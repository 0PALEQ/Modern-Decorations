package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.SoundType;

public class PottedCactusBlock extends TransparentFurnitureBlock {
	public PottedCactusBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private static final VoxelShape SHAPE_NORTH = box(6, 0, 6, 10, 5, 10);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE_NORTH;
	}

}
