package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class DocumentFoldersBlock extends HorizontalFurnitureBlock {

	public DocumentFoldersBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.WOOD).sound(SoundType.SCAFFOLDING).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	// Model outer bounds, cached for each supported orientation.
	private static final VoxelShape SHAPE_NORTH = box(5.35, 0, 9.9, 11.35, 4.1, 15.4);
	private static final VoxelShape SHAPE_EAST = box(0.6, 0, 5.35, 6.1, 4.1, 11.35);
	private static final VoxelShape SHAPE_SOUTH = box(4.65, 0, 0.6, 10.65, 4.1, 6.1);
	private static final VoxelShape SHAPE_WEST = box(9.9, 0, 4.65, 15.4, 4.1, 10.65);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> SHAPE_EAST;
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

}
