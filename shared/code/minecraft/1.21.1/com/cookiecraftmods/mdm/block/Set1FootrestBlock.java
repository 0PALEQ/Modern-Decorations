package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class Set1FootrestBlock extends TransparentHorizontalFurnitureBlock {

	public Set1FootrestBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private static final VoxelShape SHAPE_NORTH = box(0, 0, 4, 16, 8, 16);
	private static final VoxelShape SHAPE_EAST = box(0, 0, 0, 12, 8, 16);
	private static final VoxelShape SHAPE_SOUTH = box(0, 0, 0, 16, 8, 12);
	private static final VoxelShape SHAPE_WEST = box(4, 0, 0, 16, 8, 16);

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
