package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class ElectricGuitarBlackBlock extends TransparentHorizontalFurnitureBlock {
	// Outer bounds of the black parent model, including its rotated elements.
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ElectricGuitarBlackBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(4.2375, 2.775, 1.1125, 11.5875, 24.775, 2.585586);
				case NORTH -> box(4.4125, 2.775, 13.414414, 11.7625, 24.775, 14.8875);
				case EAST -> box(1.1125, 2.775, 4.4125, 2.585586, 24.775, 11.7625);
				case WEST -> box(13.414414, 2.775, 4.2375, 14.8875, 24.775, 11.5875);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
