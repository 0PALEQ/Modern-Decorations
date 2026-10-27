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

public class ToothbrushesBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ToothbrushesBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.BAMBOO_SAPLING).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(11, 0, 1, 16, 3, 3);
				case NORTH -> box(0, 0, 13, 5, 3, 15);
				case EAST -> box(1, 0, 0, 3, 3, 5);
				case WEST -> box(13, 0, 11, 15, 3, 16);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
