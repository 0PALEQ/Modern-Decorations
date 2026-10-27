package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class ShowerHeadControlsBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ShowerHeadControlsBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)
				);
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> box(5, -10.5, 12.9, 11, 12.6, 16);
				case EAST -> box(0, -10.5, 5, 3.1, 12.6, 11);
				case WEST -> box(12.9, -10.5, 5, 16, 12.6, 11);
				default -> box(5, -10.5, 0, 11, 12.6, 3.1);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
