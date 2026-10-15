package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class MugBlock extends TransparentHorizontalFurnitureBlock {
	private static final VoxelShape NORTH_SHAPE = box(5.5, 0, 6.5, 9.5, 3.75, 9.5);
	private static final VoxelShape EAST_SHAPE = box(6.5, 0, 5.5, 9.5, 3.75, 9.5);
	private static final VoxelShape SOUTH_SHAPE = box(6.5, 0, 6.5, 10.5, 3.75, 9.5);
	private static final VoxelShape WEST_SHAPE = box(6.5, 0, 6.5, 9.5, 3.75, 10.5);

	public MugBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case NORTH -> NORTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> SOUTH_SHAPE;
		};
	}

}
