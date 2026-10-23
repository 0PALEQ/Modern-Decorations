package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Furniture that can slide along the clicked surface in one-model-pixel steps. */
public abstract class PixelHorizontalFurnitureBlock extends TransparentHorizontalFurnitureBlock implements MulticellFurniture {
    // Two coordinates on a plane avoid creating thousands of unused 3D offset states.
    public static final EnumProperty<Direction.Axis> PLACEMENT_AXIS = EnumProperty.create("placement_axis", Direction.Axis.class);
    public static final IntegerProperty PIXEL_U = IntegerProperty.create("pixel_u", 0, 16);
    public static final IntegerProperty PIXEL_V = IntegerProperty.create("pixel_v", 0, 16);

    protected PixelHorizontalFurnitureBlock(BlockBehaviour.Properties properties) {
        super(properties.dynamicShape().pushReaction(FurnitureFootprint.IMMOVABLE));
        registerDefaultState(defaultBlockState().setValue(PLACEMENT_AXIS, Direction.Axis.Y)
                .setValue(PIXEL_U, 8).setValue(PIXEL_V, 8));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLACEMENT_AXIS, PIXEL_U, PIXEL_V);
    }

    public static Vec3 modelOffset(BlockState state) {
        double u = (state.getValue(PIXEL_U) - 8) / 16.0;
        double v = (state.getValue(PIXEL_V) - 8) / 16.0;
        return switch (state.getValue(PLACEMENT_AXIS)) {
            case X -> new Vec3(0, u, v);
            case Y -> new Vec3(u, 0, v);
            case Z -> new Vec3(u, v, 0);
        };
    }

    /** Coordinates are relative to the center of the placement cell; the surface normal stays fixed. */
    public static BlockState snapToSurface(BlockState state, Direction.Axis axis, double x, double y, double z) {
        double u = axis == Direction.Axis.X ? y : x;
        double v = axis == Direction.Axis.Z ? y : z;
        return state.setValue(PLACEMENT_AXIS, axis).setValue(PIXEL_U, snap(u)).setValue(PIXEL_V, snap(v));
    }

    private static int snap(double coordinate) {
        return (int) Math.max(0, Math.min(16, Math.round(coordinate * 16) + 8));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        if (!com.cookiecraftmods.mdm.placement.PrecisePlacement.isPlanning() && context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Vec3 hit = context.getClickLocation();
            BlockPos pos = context.getClickedPos();
            state = snapToSurface(state, context.getClickedFace().getAxis(),
                    hit.x - pos.getX() - 0.5, hit.y - pos.getY() - 0.5, hit.z - pos.getZ() - 0.5);
        }
        // Validate the final offset, including any cells crossed by the model.
        return FurnitureFootprint.placement(state, context);
    }

    protected abstract VoxelShape getUnshiftedShape(BlockState state);

    @Override
    public VoxelShape getFurnitureShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = modelOffset(state);
        return getUnshiftedShape(state).move(offset.x, offset.y, offset.z);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, level, pos, context), BlockPos.ZERO);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        Vec3 offset = modelOffset(state);
        Direction.Axis axis = state.getValue(PLACEMENT_AXIS);
        if (axis != Direction.Axis.Y && (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90)) {
            axis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        }
        Vec3 rotated = switch (rotation) {
            case NONE -> offset;
            case CLOCKWISE_90 -> new Vec3(-offset.z, offset.y, offset.x);
            case CLOCKWISE_180 -> new Vec3(-offset.x, offset.y, -offset.z);
            case COUNTERCLOCKWISE_90 -> new Vec3(offset.z, offset.y, -offset.x);
        };
        return snapToSurface(super.rotate(state, rotation), axis, rotated.x, rotated.y, rotated.z);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Vec3 offset = modelOffset(state);
        return snapToSurface(state.setValue(FACING, mirror.mirror(state.getValue(FACING))), state.getValue(PLACEMENT_AXIS),
                mirror == Mirror.FRONT_BACK ? -offset.x : offset.x, offset.y,
                mirror == Mirror.LEFT_RIGHT ? -offset.z : offset.z);
    }
}
