import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;
import com.cookiecraftmods.eqlib.placement.PlacementOffsetAccess;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/** Full server path: the same plan consumed by the preview must match the committed geometry and saved data. */
public final class DecorationPlacementRuntimeTest implements ModInitializer {
    @Override public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            try {
                var level = server.overworld();
                var profile = new GameProfile(UUID.randomUUID(), "DecorationTest");
                var player = new ServerPlayer(server, level, profile, ClientInformation.createDefault());
                player.connection = new ServerGamePacketListenerImpl(server, new Connection(PacketFlow.SERVERBOUND), player,
                        CommonListenerCookie.createInitial(profile, false));
                player.setPos(20, 200, 20);
                player.setShiftKeyDown(true);
                var pos = new BlockPos(-10, 200, -10);
                var blocks = BuiltInRegistries.BLOCK.stream().filter(PrecisePlacement::supported).toList();
                require(blocks.size() > 300, "Test did not include the full decoration registry");
                int count = 0;
                for (var block : blocks) {
                    for (Direction face : Direction.values()) {
                        clear(level, pos);
                        var support = pos.relative(face.getOpposite());
                        level.setBlockAndUpdate(support, Blocks.DIRT.defaultBlockState());
                        var stack = new ItemStack(block.asItem(), 2);
                        require(stack.getItem() instanceof BlockItem, "Decoration has no block item: " + block);
                        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                        Vec3 hit = new Vec3(pos.getX() + 0.70, pos.getY() + 0.60, pos.getZ() + 0.20);
                        hit = switch (face.getAxis()) {
                            case X -> new Vec3(pos.getX() + (face == Direction.WEST ? 1 : 0), hit.y, hit.z);
                            case Y -> new Vec3(hit.x, pos.getY() + (face == Direction.DOWN ? 1 : 0), hit.z);
                            case Z -> new Vec3(hit.x, hit.y, pos.getZ() + (face == Direction.NORTH ? 1 : 0));
                        };
                        var blockHit = new BlockHitResult(hit, face, support, false);
                        var context = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, stack, blockHit);
                        var preview = PrecisePlacement.plan(context);
                        require(preview.valid(), "Preview unexpectedly invalid: " + block + "/" + face + "/" + preview.shape().bounds());
                        require(((BlockItem)stack.getItem()).place(context).consumesAction(), "Placement failed: " + block + "/" + face);
                        var state = level.getBlockState(pos);
                        require(state == preview.state(), "Preview and placement states differ");
                        require(stack.getCount() == 1, "Incorrect item consumption");
                        var entity = level.getBlockEntity(pos);
                        require(entity instanceof PlacementOffsetAccess, "Missing placement data: " + block);
                        var savedOffset = ((PlacementOffsetAccess)entity).mdm$getPlacementOffset();
                        require(savedOffset.equals(preview.offset()), "Saved offset differs from preview");
                        require(state.getOffset(level, pos).equals(preview.offset()), "Rendered model offset differs from preview");
                        var actual = FurnitureFootprint.fullShape(state, level, pos);
                        require(actual.toAabbs().equals(preview.shape().toAabbs()), "Actual bounds differ from preview");
                        var b = actual.bounds();
                        double contact = switch(face) {
                            case EAST -> b.minX + pos.getX(); case WEST -> b.maxX + pos.getX();
                            case UP -> b.minY + pos.getY(); case DOWN -> b.maxY + pos.getY();
                            case SOUTH -> b.minZ + pos.getZ(); case NORTH -> b.maxZ + pos.getZ();
                        };
                        double plane = face.getAxis() == Direction.Axis.X ? hit.x : face.getAxis() == Direction.Axis.Y ? hit.y : hit.z;
                        require(close(contact, plane), "Model does not touch the targeted face exactly: " + block + "/" + face);
                        double[] centers = {(b.minX+b.maxX)/2+pos.getX(), (b.minY+b.maxY)/2+pos.getY(), (b.minZ+b.maxZ)/2+pos.getZ()};
                        for (Direction.Axis axis : Direction.Axis.values()) {
                            if (axis != face.getAxis()) require(close(centers[axis.ordinal()]*16, Math.rint(centers[axis.ordinal()]*16)), "Tangential center is off the pixel grid");
                        }
                        if (entity instanceof Container inventory) inventory.setItem(0, new ItemStack(Items.DIAMOND, 3));
                        var tag = entity.saveWithFullMetadata(level.registryAccess());
                        var restored = BlockEntity.loadStatic(pos, state, tag, level.registryAccess());
                        require(restored instanceof PlacementOffsetAccess && ((PlacementOffsetAccess)restored).mdm$getPlacementOffset().equals(savedOffset), "Save/load lost offset");
                        if (restored instanceof Container inventory) require(inventory.getItem(0).getCount() == 3, "Save/load lost inventory");
                        require(entity.getUpdateTag(level.registryAccess()).getBoolean("mdm_precise"), "Chunk sync lost placement data");
                        require(entity.getUpdatePacket() != null, "No placement update packet");
                        var parts = FurnitureFootprint.offsets(actual);
                        for (var delta : parts) {
                            var cell = pos.offset(delta);
                            var part = level.getBlockState(cell);
                            require(part.getBlock() instanceof FurniturePartBlock && FurnitureFootprint.validPart(level, cell, part), "Missing collision/helper cell");
                        }
                        require(!UseBlockCallback.EVENT.invoker().interact(player, level, InteractionHand.MAIN_HAND,
                                new BlockHitResult(Vec3.atCenterOf(pos), face, pos, false)).consumesAction(), "Shift placement was intercepted by furniture interaction");
                        level.removeBlock(pos, false);
                        for (var delta : parts) require(level.getBlockState(pos.offset(delta)).isAir(), "Removed decoration left helper blocks: " + block);
                        require(level.getBlockState(support).is(Blocks.DIRT), "Placement or removal modified the support");
                        if (!parts.isEmpty()) {
                            level.setBlockAndUpdate(pos.offset(parts.get(0)), Blocks.STONE.defaultBlockState());
                            var blocked = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, stack, blockHit);
                            require(!PrecisePlacement.plan(blocked).valid(), "Obstructed preview stayed valid");
                            require(!((BlockItem)stack.getItem()).place(blocked).consumesAction() && stack.getCount() == 1, "Obstructed placement consumed an item");
                        }
                        count++;
                    }
                }
                System.out.println("DECORATION_PLACEMENT_RUNTIME_PASSED: " + blocks.size() + " pieces, " + count + " placements on all six faces; preview parity, exact contact, pixel snapping, persistence, inventory, packets, cleanup, obstruction.");
                Runtime.getRuntime().halt(0);
            } catch (Throwable failure) {
                failure.printStackTrace();
                Runtime.getRuntime().halt(1);
            }
        });
    }
    private static void clear(ServerLevel level, BlockPos pos) {
        for (var cell : BlockPos.betweenClosed(pos.offset(-5,-5,-5),pos.offset(5,5,5))) level.setBlockAndUpdate(cell, Blocks.AIR.defaultBlockState());
    }
    private static boolean close(double a, double b) { return Math.abs(a-b) < 0.000001; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
