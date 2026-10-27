import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.block.SingleBedBlock;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class SingleBedSleepRuntimeTest implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            try {
                var level = server.overworld();
                level.setDayTime(18000);
                level.updateSkyBrightness();
                var profile = new GameProfile(UUID.randomUUID(), "BedTest");
                ServerPlayer player = new ServerPlayer(server, level, profile, ClientInformation.createDefault()) {
                    @Override
                    public void displayClientMessage(net.minecraft.network.chat.Component message, boolean overlay) {
                        System.out.println("BED_TEST_MESSAGE: " + message.getString());
                    }
                };
                player.connection = new ServerGamePacketListenerImpl(server, new Connection(PacketFlow.SERVERBOUND),
                        player, CommonListenerCookie.createInitial(profile, false));
                var pos = new BlockPos(0, 200, 0);
                for (var floor : BlockPos.betweenClosed(pos.offset(-3, -1, -3), pos.offset(3, -1, 3))) {
                    level.setBlock(floor, Blocks.STONE.defaultBlockState(), 3);
                }
                var beds = BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof SingleBedBlock).toList();
                require(beds.size() == 48, "Expected 48 registered beds");
                int sleeps = 0;
                for (var bed : beds) {
                    for (var facing : Direction.Plane.HORIZONTAL) {
                        var state = bed.defaultBlockState().setValue(BedBlock.FACING, facing);
                        level.setBlock(pos, state, 3);
                        FurnitureFootprint.placeParts(level, pos, state);
                        var storage = (Container) level.getBlockEntity(pos);
                        storage.setItem(0, new ItemStack(Items.DIAMOND, 3));
                        for (var clicked : new BlockPos[]{pos, pos.relative(facing)}) {
                            player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                            var hit = new BlockHitResult(Vec3.atCenterOf(clicked), Direction.UP, clicked, false);
                            var result = UseBlockCallback.EVENT.invoker().interact(player, level, InteractionHand.MAIN_HAND, hit);
                            require(result.consumesAction(), "Click not handled: " + bed);
                            require(player.isSleeping(), "Player did not sleep: " + bed + " / " + facing + " / " + clicked);
                            require(player.getSleepingPos().orElseThrow().equals(pos), "Sleeping at helper instead of parent");
                            require(level.getBlockState(pos).getValue(BedBlock.OCCUPIED), "Occupancy missing");
                            player.stopSleepInBed(true, true);
                            require(!level.getBlockState(pos).getValue(BedBlock.OCCUPIED), "Occupancy not cleared");
                            require(storage.getItem(0).getCount() == 3, "Sleeping lost stored items");
                            player.setShiftKeyDown(true);
                            UseBlockCallback.EVENT.invoker().interact(player, level, InteractionHand.MAIN_HAND, hit);
                            require(player.containerMenu != player.inventoryMenu, "Shift-click did not open storage");
                            player.closeContainer();
                            player.setShiftKeyDown(false);
                            sleeps++;
                        }
                        level.removeBlock(pos, false);
                    }
                }
                System.out.println("SINGLE_BED_SLEEP_RUNTIME_PASSED: " + sleeps + " sleep/wake/storage interactions (48 beds, four directions, both cells).");
                Runtime.getRuntime().halt(0);
            } catch (Throwable failure) {
                failure.printStackTrace();
                Runtime.getRuntime().halt(1);
            }
        });
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
