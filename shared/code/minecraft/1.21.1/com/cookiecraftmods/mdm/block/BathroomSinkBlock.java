package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;


public class BathroomSinkBlock extends HorizontalFurnitureBlock {

	public BathroomSinkBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_TILES).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}


	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return BathroomSinkShapes.forFacing(state.getValue(FACING));
	}

}
