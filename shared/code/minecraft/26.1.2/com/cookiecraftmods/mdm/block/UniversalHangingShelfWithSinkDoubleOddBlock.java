package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UniversalHangingShelfWithSinkDoubleOddBlock extends RunningSinkBlock implements MulticellFurniture {

    public UniversalHangingShelfWithSinkDoubleOddBlock() {
        super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).pushReaction(FurnitureFootprint.IMMOVABLE).strength(1f, 10f)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false), 8);
    }

    @Override
    public VoxelShape getFurnitureCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SinkShelfCollisionShapes.forFacing(SinkShelfCollisionShapes.DOUBLE_ODD, state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureCollisionShape(state, level, pos, context), BlockPos.ZERO);
    }

    @Override
    public void tick(BlockState state, net.minecraft.server.level.ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        FurnitureFootprint.placeParts(level, pos, state);
        super.tick(state, level, pos, random);
    }

    @Override
    public VoxelShape getFurnitureShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return AdditionalFurnitureShapes.forFacing(AdditionalFurnitureShapes.SINK_DOUBLE_ODD, state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
    }
}
