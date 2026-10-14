import com.cookiecraftmods.mdm.block.RunningSinkBlock;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            System.out.println("FURNITURE_WATER_RUNTIME_PASSED");
            System.exit(0);
        } catch (Throwable failure) {
            failure.printStackTrace();
            System.exit(1);
        }
    }
}
