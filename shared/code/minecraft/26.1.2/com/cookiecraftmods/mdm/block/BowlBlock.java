package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.SoundType;

public class BowlBlock extends TransparentHorizontalFurnitureBlock {
	private static final VoxelShape SHAPE = box(5.1875, 0, 5.1875, 10.8125, 2.1875, 10.8125);

	public BowlBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
