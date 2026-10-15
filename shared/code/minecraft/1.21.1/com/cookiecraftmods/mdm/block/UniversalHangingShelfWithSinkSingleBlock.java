package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UniversalHangingShelfWithSinkSingleBlock extends RunningSinkBlock {
    private static final VoxelShape NORTH_SHAPE = box(0, 7, 4, 16, 13, 16);
    private static final VoxelShape EAST_SHAPE = HorizontalFurnitureBlock.rotateShape(NORTH_SHAPE);
    private static final VoxelShape SOUTH_SHAPE = HorizontalFurnitureBlock.rotateShape(EAST_SHAPE);
    private static final VoxelShape WEST_SHAPE = HorizontalFurnitureBlock.rotateShape(SOUTH_SHAPE);

    public UniversalHangingShelfWithSinkSingleBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false), 8);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SinkShelfCollisionShapes.forFacing(SinkShelfCollisionShapes.SINGLE, state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}
