package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class DoorMatBlock extends TransparentHorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public DoorMatBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.GRASS).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(1, 0, 1, 15, 0.5, 9);
				case NORTH -> box(1, 0, 7, 15, 0.5, 15);
				case EAST -> box(1, 0, 1, 9, 0.5, 15);
				case WEST -> box(7, 0, 1, 15, 0.5, 15);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
