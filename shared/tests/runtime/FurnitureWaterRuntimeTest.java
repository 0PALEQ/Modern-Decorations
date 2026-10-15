import com.cookiecraftmods.mdm.block.RunningSinkBlock;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.List;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class FurnitureWaterRuntimeTest implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            Class<?> particle = Class.forName("net.minecraft.client.particle.DripParticle$FallAndLandParticle");
            particle.getDeclaredField("mdm$furnitureWater");
            RunningSinkBlock shower = (RunningSinkBlock) MdmModBlocks.SHOWER_HEAD.get();
            var state = shower.defaultBlockState();
            if (state.getValue(RunningSinkBlock.RUNNING)) {
                throw new AssertionError("Shower should initially be off");
            }
            var model = JsonParser.parseReader(new InputStreamReader(
                    getClass().getResourceAsStream("/assets/mdm/models/custom/shower_head.json"),
                    StandardCharsets.UTF_8)).getAsJsonObject();
            var elements = model.getAsJsonArray("elements");
            if (elements.size() != 59) {
                throw new AssertionError("Shower model changed; review its particle-spawner groups");
            }
            double x = 0;
            double y = 0;
            double z = 0;
            for (int index = 11; index < elements.size(); index++) {
                var element = elements.get(index).getAsJsonObject();
                var from = element.getAsJsonArray("from");
                var to = element.getAsJsonArray("to");
                x += (from.get(0).getAsDouble() + to.get(0).getAsDouble()) / 32;
                y += (from.get(1).getAsDouble() - 0.01) / 16;
                z += (from.get(2).getAsDouble() + to.get(2).getAsDouble()) / 32;
            }
            int outletParts = elements.size() - 11;
            x /= outletParts;
            y /= outletParts;
            z /= outletParts;
            for (Direction facing : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
                double rotatedX = switch (facing) {
                    case EAST -> 1 - z;
                    case SOUTH -> 1 - x;
                    case WEST -> z;
                    default -> x;
                };
                double rotatedZ = switch (facing) {
                    case EAST -> x;
                    case SOUTH -> 1 - z;
                    case WEST -> 1 - x;
                    default -> z;
                };
                var placed = state.setValue(RunningSinkBlock.FACING, facing);
                BlockPos pos = new BlockPos(-20, 70, 30);
                if (!shower.isWaterOutlet(placed, pos, pos.getX() + rotatedX,
                        pos.getY() + y, pos.getZ() + rotatedZ)) {
                    throw new AssertionError("Average outlet failed to rotate: " + facing);
                }
                if (shower.isWaterOutlet(placed, pos, pos.getX() + rotatedX,
                        pos.getY() + y + 0.1, pos.getZ() + rotatedZ)) {
                    throw new AssertionError("Unrelated drops should not be treated as furniture water");
                }
            }
            checkSinkCollision((RunningSinkBlock) MdmModBlocks.UNIVERSAL_HANGING_SHELF_WITH_SINK_SINGLE_LIGHT_OAK.get(), 8);
            checkSinkCollision((RunningSinkBlock) MdmModBlocks.UNIVERSAL_HANGING_SHELF_WITH_SINK_DOUBLE_LIGHT_OAK.get(), 0);
            checkSinkCollision((RunningSinkBlock) MdmModBlocks.UNIVERSAL_HANGING_SHELF_WITH_SINK_DOUBLE_ODD_LIGHT_OAK.get(), 8);
            System.out.println("FURNITURE_WATER_RUNTIME_PASSED");
            System.exit(0);
        } catch (Throwable failure) {
            failure.printStackTrace();
            System.exit(1);
        }
    }

    private static void checkSinkCollision(RunningSinkBlock sink, double outletX) {
        double x = outletX / 16;
        double z = 11.5 / 16;
        for (Direction facing : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
            double rotatedX = switch (facing) {
                case EAST -> 1 - z;
                case SOUTH -> 1 - x;
                case WEST -> z;
                default -> x;
            };
            double rotatedZ = switch (facing) {
                case EAST -> x;
                case SOUTH -> 1 - z;
                case WEST -> 1 - x;
                default -> z;
            };
            for (boolean running : new boolean[]{false, true}) {
                var state = sink.defaultBlockState().setValue(RunningSinkBlock.FACING, facing)
                        .setValue(RunningSinkBlock.RUNNING, running);
                var collision = state.getCollisionShape(null, BlockPos.ZERO, CollisionContext.empty());
                AABB drop = new AABB(rotatedX - 0.01, 16.84 / 16, rotatedZ - 0.01,
                        rotatedX + 0.01, 16.84 / 16 + 0.01, rotatedZ + 0.01);
                double travel = Shapes.collide(Direction.Axis.Y, drop, List.of(collision), -0.18);
                if (Math.abs(travel + 0.18) > 0.000001) {
                    throw new AssertionError("Water hits the basin rim: " + sink + " " + facing);
                }
                double landing = Shapes.collide(Direction.Axis.Y, drop, List.of(collision), -1);
                if (Math.abs(drop.minY + landing - 13.4 / 16) > 0.000001) {
                    throw new AssertionError("Water should land on the drain: " + sink + " " + facing);
                }
            }
        }
    }
}
