package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class PottedTreeBlock extends TransparentHorizontalFurnitureBlock {

	public PottedTreeBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private static final VoxelShape SHAPE_NORTH = box(0, 0, 1, 15.227033, 22, 15.017917);
	private static final VoxelShape SHAPE_EAST = box(0.982083, 0, 0, 15, 22, 15.227033);
	private static final VoxelShape SHAPE_SOUTH = box(0.772967, 0, 0.982083, 16, 22, 15);
	private static final VoxelShape SHAPE_WEST = box(1, 0, 0.772967, 15.017917, 22, 16);

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
