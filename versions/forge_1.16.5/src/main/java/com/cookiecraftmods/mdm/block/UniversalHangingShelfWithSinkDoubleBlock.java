package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

public class UniversalHangingShelfWithSinkDoubleBlock extends RunningSinkBlock implements MulticellFurniture {

    public UniversalHangingShelfWithSinkDoubleBlock() {
        super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false), 0);
    }

    @Override
    public VoxelShape getFurnitureCollisionShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return SinkShelfCollisionShapes.forFacing(SinkShelfCollisionShapes.DOUBLE, state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return FurnitureFootprint.localShape(getFurnitureCollisionShape(state, level, pos, context), BlockPos.ZERO);
    }

    @Override
    public void tick(BlockState state, net.minecraft.world.server.ServerWorld level, BlockPos pos, java.util.Random random) {
        FurnitureFootprint.placeParts(level, pos, state);
        super.tick(state, level, pos, random);
    }

    @Override
    public VoxelShape getFurnitureShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return AdditionalFurnitureShapes.forFacing(AdditionalFurnitureShapes.SINK_DOUBLE, state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
    }
}
