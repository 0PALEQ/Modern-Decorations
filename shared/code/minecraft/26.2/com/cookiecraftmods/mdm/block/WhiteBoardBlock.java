package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class WhiteBoardBlock extends HorizontalFurnitureBlock implements MulticellFurniture {

	public WhiteBoardBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).pushReaction(FurnitureFootprint.IMMOVABLE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

    @Override
    public VoxelShape getFurnitureShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return AdditionalFurnitureShapes.forFacing(AdditionalFurnitureShapes.WHITE_BOARD, state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
    }

}
