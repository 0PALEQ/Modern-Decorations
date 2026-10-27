package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;

public class HorizontalFurnitureBlock extends FurnitureBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    protected HorizontalFurnitureBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return FurnitureFootprint.placement(super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()), context);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    static VoxelShape rotateShape(VoxelShape shape) {
        VoxelShape[] rotated = {VoxelShapes.empty()};
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                rotated[0] = VoxelShapes.or(rotated[0], VoxelShapes.box(
                        1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
        return rotated[0];
    }
}
