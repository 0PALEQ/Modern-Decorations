package com.cookiecraftmods.mdm.placement;

import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.block.FurniturePartBlock;
import com.cookiecraftmods.mdm.block.MulticellFurniture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.function.Supplier;

public final class PrecisePlacement {
    private PrecisePlacement() {}
    private static final ThreadLocal<Integer> RAW = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Plan> PENDING = new ThreadLocal<>();
    public static java.util.function.Function<BlockPos, Vec3> clientOffset = pos -> Vec3.ZERO;
    public static java.util.function.BiConsumer<BlockEntity, Vec3> clientDataChanged = (entity, offset) -> {};
    public static java.util.function.Consumer<BlockEntity> clientDataRemoved = entity -> {};

    public record Plan(BlockState state, BlockPos pos, Vec3 offset, VoxelShape shape, boolean valid) {}
    public static boolean supported(Block block) {
        return block.getClass().getName().startsWith("com.cookiecraftmods.mdm.block.")
                && !(block instanceof FurniturePartBlock);
    }

    public static boolean isPlanning() { return RAW.get() > 0; }

    public static <T> T raw(Supplier<T> action) {
        RAW.set(RAW.get() + 1);
        try { return action.get(); }
        finally { RAW.set(RAW.get() - 1); }
    }

    public static boolean active(BlockGetter level, BlockPos pos) {
        if (level == null || isPlanning()) return false;
        Plan plan = PENDING.get();
        if (plan != null && plan.pos.equals(pos)) return true;
        return level.getBlockEntity(pos) instanceof PlacementOffsetAccess data && data.mdm$getPlacementOffset() != null;
    }

    public static Vec3 offset(BlockGetter level, BlockPos pos) {
        if (level == null || isPlanning()) return Vec3.ZERO;
        Plan plan = PENDING.get();
        if (plan != null && plan.pos.equals(pos)) return plan.offset;
        if (level.getBlockEntity(pos) instanceof PlacementOffsetAccess data && data.mdm$getPlacementOffset() != null) {
            return data.mdm$getPlacementOffset();
        }
        return Vec3.ZERO;
    }

    public static VoxelShape rawShape(BlockState state, BlockGetter level, BlockPos pos, boolean collision) {
        return raw(() -> {
            if (state.getBlock() instanceof MulticellFurniture furniture) {
                return collision ? furniture.getFurnitureCollisionShape(state, level, pos, CollisionContext.empty())
                        : furniture.getFurnitureShape(state, level, pos, CollisionContext.empty());
            }
            return collision ? state.getCollisionShape(level, pos) : state.getShape(level, pos);
        });
    }

    public static VoxelShape fullShape(BlockState state, BlockGetter level, BlockPos pos, boolean collision) {
        Vec3 offset = offset(level, pos);
        return rawShape(state, level, pos, collision).move(offset.x, offset.y, offset.z);
    }
    public static boolean requested(BlockPlaceContext context) {
        return !isPlanning() && context.getPlayer() != null && context.getPlayer().isShiftKeyDown()
                && context.getItemInHand().getItem() instanceof BlockItem item && supported(item.getBlock());
    }
    public static Plan plan(BlockPlaceContext context) {
        Block block = ((BlockItem) context.getItemInHand().getItem()).getBlock();
        BlockPos pos = context.getClickedPos().immutable();
        BlockState candidate = raw(() -> block.getStateForPlacement(context));
        if (candidate == null) candidate = block.defaultBlockState();
        Direction face = context.getClickedFace();
        if (candidate.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && face.getAxis() != Direction.Axis.Y) {
            candidate = candidate.setValue(BlockStateProperties.HORIZONTAL_FACING, face);
        }
        VoxelShape shape = rawShape(candidate, context.getLevel(), pos, false);
        if (shape.isEmpty()) return new Plan(candidate, pos, Vec3.ZERO, shape, false);
        Vec3 offset = align(shape.bounds(), face, context.getClickLocation().subtract(pos.getX(), pos.getY(), pos.getZ()));
        VoxelShape shifted = shape.move(offset.x, offset.y, offset.z);
        VoxelShape collision = rawShape(candidate, context.getLevel(), pos, true)
                .move(pos.getX() + offset.x, pos.getY() + offset.y, pos.getZ() + offset.z);
        boolean valid = fits(context.getLevel(), pos, shifted) && candidate.canSurvive(context.getLevel(), pos)
                && context.getLevel().isUnobstructed(null, collision);
        return new Plan(candidate, pos, offset, shifted, valid);
    }

    public static Vec3 align(AABB bounds, Direction face, Vec3 hit) {
        double x = Math.rint(hit.x * 16) / 16 - (bounds.minX + bounds.maxX) / 2;
        double y = Math.rint(hit.y * 16) / 16 - (bounds.minY + bounds.maxY) / 2;
        double z = Math.rint(hit.z * 16) / 16 - (bounds.minZ + bounds.maxZ) / 2;
        return switch (face) {
            case EAST -> new Vec3(hit.x - bounds.minX, y, z);
            case WEST -> new Vec3(hit.x - bounds.maxX, y, z);
            case UP -> new Vec3(x, hit.y - bounds.minY, z);
            case DOWN -> new Vec3(x, hit.y - bounds.maxY, z);
            case SOUTH -> new Vec3(x, y, hit.z - bounds.minZ);
            case NORTH -> new Vec3(x, y, hit.z - bounds.maxZ);
        };
    }

    public static boolean fits(Level level, BlockPos pos, VoxelShape shape) {
        if (shape.isEmpty()) return false;
        for (BlockPos offset : FurnitureFootprint.offsets(shape)) {
            if (!FurniturePartBlock.X.getPossibleValues().contains(offset.getX() + 4)
                    || !FurniturePartBlock.Y.getPossibleValues().contains(offset.getY() + 4)
                    || !FurniturePartBlock.Z.getPossibleValues().contains(offset.getZ() + 4)) return false;
            BlockPos cell = pos.offset(offset);
            if (level.isOutsideBuildHeight(cell) || !level.getWorldBorder().isWithinBounds(cell)
                    || !level.getBlockState(cell).canBeReplaced()) return false;
        }
        return !level.isOutsideBuildHeight(pos) && level.getWorldBorder().isWithinBounds(pos)
                && level.getBlockState(pos).canBeReplaced();
    }

    public static void begin(Plan plan) { PENDING.set(plan); }
    public static void end() { PENDING.remove(); }
    public static void commit(Level level, BlockPos pos) {
        Plan plan = PENDING.get();
        if (plan == null || !plan.pos.equals(pos)) return;
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof PlacementOffsetAccess data)) {
            throw new IllegalStateException("Decoration is missing placement storage: " + plan.state);
        }
        data.mdm$setPlacementOffset(plan.offset);
        entity.setChanged();
        FurnitureFootprint.placeParts(level, pos, plan.state);
        level.sendBlockUpdated(pos, plan.state, plan.state, Block.UPDATE_ALL);
    }

    public static void refresh(BlockEntity entity) {
        Level level = entity.getLevel();
        if (level != null) {
            BlockState state = entity.getBlockState();
            level.sendBlockUpdated(entity.getBlockPos(), state, state, Block.UPDATE_ALL);
        }
    }
}