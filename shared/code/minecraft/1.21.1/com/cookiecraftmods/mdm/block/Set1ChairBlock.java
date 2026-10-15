package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class Set1ChairBlock extends HorizontalFurnitureBlock {

	public Set1ChairBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	// Seat/body and backrest bounds leave the space above the seat open.
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 12, 13, 24, 13)).optimize();
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 3, 4, 24, 13)).optimize();
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(3, 0, 3, 13, 12, 13), box(3, 12, 3, 13, 24, 4)).optimize();
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 3, 13, 12, 13), box(12, 12, 3, 13, 24, 13)).optimize();

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> SHAPE_EAST;
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

}
