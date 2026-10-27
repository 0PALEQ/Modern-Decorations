package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class ChoppingBoardBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ChoppingBoardBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(3, 0, 5, 13, 0.5, 11);
				case NORTH -> box(3, 0, 5, 13, 0.5, 11);
				case EAST -> box(5, 0, 3, 11, 0.5, 13);
				case WEST -> box(5, 0, 3, 11, 0.5, 13);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
