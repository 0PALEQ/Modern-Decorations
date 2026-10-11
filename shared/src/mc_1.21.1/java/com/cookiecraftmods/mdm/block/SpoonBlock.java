package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class SpoonBlock extends TransparentHorizontalFurnitureBlock {
	private static final VoxelShape NORTH_SHAPE = box(7.5, -0.021894, 4.76306, 8.5, 0.832579, 10.312305);
	private static final VoxelShape EAST_SHAPE = box(5.687695, -0.021894, 7.5, 11.23694, 0.832579, 8.5);
	private static final VoxelShape SOUTH_SHAPE = box(7.5, -0.021894, 5.687695, 8.5, 0.832579, 11.23694);
	private static final VoxelShape WEST_SHAPE = box(4.76306, -0.021894, 7.5, 10.312305, 0.832579, 8.5);

	public SpoonBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
