package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

/** Furniture that can slide along the clicked surface in one-model-pixel steps. */
public abstract class PixelHorizontalFurnitureBlock extends TransparentHorizontalFurnitureBlock implements MulticellFurniture {
    // Two coordinates on a plane avoid creating thousands of unused 3D offset states.
    public static final EnumProperty<Direction.Axis> PLACEMENT_AXIS = EnumProperty.create("placement_axis", Direction.Axis.class);
    public static final IntegerProperty PIXEL_U = IntegerProperty.create("pixel_u", 0, 16);
    public static final IntegerProperty PIXEL_V = IntegerProperty.create("pixel_v", 0, 16);

    protected PixelHorizontalFurnitureBlock(AbstractBlock.Properties properties) {
        super(properties.dynamicShape());
        registerDefaultState(defaultBlockState().setValue(PLACEMENT_AXIS, Direction.Axis.Y)
                .setValue(PIXEL_U, 8).setValue(PIXEL_V, 8));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLACEMENT_AXIS, PIXEL_U, PIXEL_V);
    }

    public static Vector3d modelOffset(BlockState state) {
        double u = (state.getValue(PIXEL_U) - 8) / 16.0;
        double v = (state.getValue(PIXEL_V) - 8) / 16.0;
        return switch (state.getValue(PLACEMENT_AXIS)) {
            case X -> new Vector3d(0, u, v);
            case Y -> new Vector3d(u, 0, v);
            case Z -> new Vector3d(u, v, 0);
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        if (!com.cookiecraftmods.eqlib.placement.PrecisePlacement.isPlanning() && context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Vector3d hit = context.getClickLocation();
            BlockPos pos = context.getClickedPos();
            state = snapToSurface(state, context.getClickedFace().getAxis(),
                    hit.x - pos.getX() - 0.5, hit.y - pos.getY() - 0.5, hit.z - pos.getZ() - 0.5);
        }
        // Validate the final offset, including any cells crossed by the model.
        return FurnitureFootprint.placement(state, context);
    }

    protected abstract VoxelShape getUnshiftedShape(BlockState state);

    @Override
    public VoxelShape getFurnitureShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        Vector3d offset = modelOffset(state);
        return getUnshiftedShape(state).move(offset.x, offset.y, offset.z);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, level, pos, context), BlockPos.ZERO);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        Vector3d offset = modelOffset(state);
        Direction.Axis axis = state.getValue(PLACEMENT_AXIS);
        if (axis != Direction.Axis.Y && (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90)) {
            axis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        }
        Vector3d rotated = switch (rotation) {
            case NONE -> offset;
            case CLOCKWISE_90 -> new Vector3d(-offset.z, offset.y, offset.x);
            case CLOCKWISE_180 -> new Vector3d(-offset.x, offset.y, -offset.z);
            case COUNTERCLOCKWISE_90 -> new Vector3d(offset.z, offset.y, -offset.x);
        };
        return snapToSurface(super.rotate(state, rotation), axis, rotated.x, rotated.y, rotated.z);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Vector3d offset = modelOffset(state);
        return snapToSurface(state.setValue(FACING, mirror.mirror(state.getValue(FACING))), state.getValue(PLACEMENT_AXIS),
                mirror == Mirror.FRONT_BACK ? -offset.x : offset.x, offset.y,
                mirror == Mirror.LEFT_RIGHT ? -offset.z : offset.z);
    }
}
