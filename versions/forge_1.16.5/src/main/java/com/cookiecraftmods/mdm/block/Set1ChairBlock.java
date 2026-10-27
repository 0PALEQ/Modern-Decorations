package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class Set1ChairBlock extends HorizontalFurnitureBlock {

	public Set1ChairBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	// Seat/body and backrest bounds leave the space above the seat open.
	private static final VoxelShape SHAPE_NORTH = VoxelShapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 12, 13, 24, 13)).optimize();
	private static final VoxelShape SHAPE_EAST = VoxelShapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 3, 4, 24, 13)).optimize();
	private static final VoxelShape SHAPE_SOUTH = VoxelShapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 3, 13, 24, 4)).optimize();
	private static final VoxelShape SHAPE_WEST = VoxelShapes.or(box(3, 0, 3, 13, 12, 13), box(12, 12, 3, 13, 24, 13)).optimize();

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
