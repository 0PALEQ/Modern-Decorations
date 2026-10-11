package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class KnifeBlock extends TransparentHorizontalFurnitureBlock {
	private static final VoxelShape NORTH_SHAPE = box(7.5, 0.1, 5, 8.5, 0.4, 11);
	private static final VoxelShape EAST_SHAPE = box(5, 0.1, 7.5, 11, 0.4, 8.5);
	private static final VoxelShape SOUTH_SHAPE = box(7.5, 0.1, 5, 8.5, 0.4, 11);
	private static final VoxelShape WEST_SHAPE = box(5, 0.1, 7.5, 11, 0.4, 8.5);

	public KnifeBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
