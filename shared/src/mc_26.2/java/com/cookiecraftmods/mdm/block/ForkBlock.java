package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class ForkBlock extends TransparentHorizontalFurnitureBlock {
	private static final VoxelShape NORTH_SHAPE = box(7.5, -0.006042, 4.76306, 8.5, 0.947384, 10.589469);
	private static final VoxelShape EAST_SHAPE = box(5.410531, -0.006042, 7.5, 11.23694, 0.947384, 8.5);
	private static final VoxelShape SOUTH_SHAPE = box(7.5, -0.006042, 5.410531, 8.5, 0.947384, 11.23694);
	private static final VoxelShape WEST_SHAPE = box(4.76306, -0.006042, 7.5, 10.589469, 0.947384, 8.5);

	public ForkBlock() {
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
