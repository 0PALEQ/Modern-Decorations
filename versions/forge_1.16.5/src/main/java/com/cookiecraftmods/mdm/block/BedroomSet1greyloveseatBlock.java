package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BedroomSet1greyloveseatBlock extends HorizontalFurnitureBlock implements MulticellFurniture {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BedroomSet1greyloveseatBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 0, 16, 16, 32);
				case NORTH -> box(0, 0, -16, 16, 16, 16);
				case EAST -> box(0, 0, 0, 32, 16, 16);
				case WEST -> box(-16, 0, 0, 16, 16, 16);
			};
		});
	}

	@Override
	public VoxelShape getFurnitureShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
	}

}
