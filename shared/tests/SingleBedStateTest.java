import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.block.MulticellFurniture;
import com.cookiecraftmods.mdm.block.SingleBedBlock;
import java.util.IdentityHashMap;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.shapes.CollisionContext;

// this checks actual block construction and the state layout used by existing beds.
public class SingleBedStateTest {
    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        // Fabric reopens registries for mod construction; reproduce that phase in this isolated JVM.
        var frozen = MappedRegistry.class.getDeclaredField("frozen");
        frozen.setAccessible(true);
        frozen.set(BuiltInRegistries.BLOCK, false);
        var holders = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
        holders.setAccessible(true);
        holders.set(BuiltInRegistries.BLOCK, new IdentityHashMap<>());
        for (String color : new String[]{"grey", "wenge", "white"}) {
            String className = "BedroomSet1" + color + (color.equals("white") ? "SinglebedBlock" : "SingleBedBlock");
            Class<?> type = Class.forName("com.cookiecraftmods.mdm.block." + className);
            Supplier<SingleBedBlock> factory = () -> {
                try {
                    return (SingleBedBlock) type.getConstructor().newInstance();
                } catch (ReflectiveOperationException exception) {
                    throw new RuntimeException(exception);
                }
            };
            SingleBedBlock bed;
            try {
                Class<?> properties = Class.forName("com.cookiecraftmods.mdm.registry.RegistryProperties");
                bed = (SingleBedBlock) properties.getMethod("withBlockKey", String.class, Supplier.class)
                        .invoke(null, "test_single_bed_" + color, factory);
            } catch (ClassNotFoundException legacy) {
                bed = factory.get();
            }
            require(bed instanceof BedBlock, "Not a native bed: " + color);
            require(bed.defaultBlockState().getValue(BedBlock.PART) == BedPart.HEAD, "Old saves must default to head");
            require(!bed.defaultBlockState().getValue(BedBlock.OCCUPIED), "New bed is occupied");
            require(bed.getRenderShape(bed.defaultBlockState()) == RenderShape.MODEL, "Custom model lost");
            require(type.getMethod("newBlockEntity", BlockPos.class, net.minecraft.world.level.block.state.BlockState.class)
                    .getDeclaringClass() == type, "Inventory entity factory lost");
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                for (boolean occupied : new boolean[]{false, true}) {
                    var state = bed.defaultBlockState().setValue(BedBlock.FACING, facing).setValue(BedBlock.OCCUPIED, occupied);
                    var shape = ((MulticellFurniture) bed).getFurnitureShape(state, null, BlockPos.ZERO, CollisionContext.empty());
                    var offsets = FurnitureFootprint.offsets(shape);
                    require(offsets.size() == 1 && offsets.get(0).equals(BlockPos.ZERO.relative(facing)),
                            "Furniture footprint moved: " + color + "/" + facing);
                    require(state.rotate(Rotation.CLOCKWISE_90).getValue(BedBlock.FACING) == facing.getClockWise(), "Rotation changed");
                    require(state.rotate(Rotation.CLOCKWISE_90).getValue(BedBlock.OCCUPIED) == occupied, "Rotation lost occupancy");
                    require(state.getValue(BedBlock.PART) == BedPart.HEAD, "Bed became a vanilla foot");
                    require(SingleBedBlock.sleepDirection(state) == facing.getOpposite(), "Sleeper is facing away from pillow");
                    for (var method : SingleBedBlock.class.getDeclaredMethods()) {
                        if (!method.getName().equals("updateShape")) continue;
                        method.setAccessible(true);
                        Object[] parameters = method.getParameterCount() == 6
                                ? new Object[]{state, facing, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), null, BlockPos.ZERO, BlockPos.ZERO.relative(facing)}
                                : new Object[]{state, null, null, BlockPos.ZERO, facing, BlockPos.ZERO.relative(facing), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), net.minecraft.util.RandomSource.create()};
                        require(method.invoke(bed, parameters) == state, "Bed requires a vanilla second half");
                    }
                }
            }
        }
        System.out.println("Single beds passed: all colors/directions, native sleep states, footprints, models and inventory factories.");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
