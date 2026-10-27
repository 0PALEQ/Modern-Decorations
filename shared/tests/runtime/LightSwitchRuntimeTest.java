import com.cookiecraftmods.mdm.block.LightSwitch;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class LightSwitchRuntimeTest implements ModInitializer {
    @Override public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            try {
                var level = server.overworld();
                var player = new ServerPlayer(server, level,
                        new GameProfile(UUID.randomUUID(), "LightTest"), ClientInformation.createDefault());
                var pos = new BlockPos(20, 200, 20);
                level.getChunkAt(pos);
                player.setPos(20, 200, 22);
                var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
                int count = 0;
                for (var block : BuiltInRegistries.BLOCK.stream().filter(b ->
                        BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals("mdm")
                                && b.defaultBlockState().hasProperty(LightSwitch.LIT)).toList()) {
                    level.setBlock(pos, block.defaultBlockState(), 3);
                    for (var held : new net.minecraft.world.item.Item[]{Items.AIR, Items.STONE, Items.DIAMOND}) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, held == Items.AIR ? ItemStack.EMPTY : new ItemStack(held, 16));
                        for (int i = 0; i < 1000; i++) {
                            var before = level.getBlockState(pos);
                            var result = UseBlockCallback.EVENT.invoker().interact(player, level, InteractionHand.MAIN_HAND, hit);
                            require(result.consumesAction(), "Light interaction was not consumed: " + block);
                            require(level.getBlockState(pos).getValue(LightSwitch.LIT) != before.getValue(LightSwitch.LIT), "Light did not toggle");
                        }
                        require(player.getMainHandItem().getCount() == (held == Items.AIR ? 0 : 16), "Held stack changed");
                        require(level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(8)).isEmpty(), "Switching spawned items: " + block);
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            if (i != player.getInventory().getSelectedSlot()) require(player.getInventory().getItem(i).isEmpty(), "Switching gave inventory items");
                        }
                    }
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    count++;
                }
                require(count > 0, "No lights tested");
                System.out.println("LIGHT_SWITCH_RUNTIME_PASSED: " + count + " lights, 3000 rapid interactions each; no spawned items or inventory changes");
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
