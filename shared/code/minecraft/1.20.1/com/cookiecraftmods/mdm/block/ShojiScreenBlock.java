package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class ShojiScreenBlock extends TransparentHorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ShojiScreenBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(-10, 0, 3, 26, 32, 18);
				case NORTH -> box(-10, 0, 3, 26, 32, 18);
				case EAST -> box(3, 0, -10, 18, 32, 26);
				case WEST -> box(3, 0, -10, 18, 32, 26);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}
}

