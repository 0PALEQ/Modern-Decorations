package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ElectricGuitarBlackBlock extends PixelHorizontalFurnitureBlock {
	private static final VoxelShape NORTH_SHAPE = box(4.4125, 2.775, 13.414414, 11.7625, 24.775, 14.8875);
	private static final VoxelShape EAST_SHAPE = box(1.1125, 2.775, 4.4125, 2.585586, 24.775, 11.7625);
	private static final VoxelShape WEST_SHAPE = box(13.414414, 2.775, 4.2375, 14.8875, 24.775, 11.5875);
	private static final VoxelShape SOUTH_SHAPE = box(4.2375, 2.775, 1.1125, 11.5875, 24.775, 2.585586);

	public ElectricGuitarBlackBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}


	@Override
	protected VoxelShape getUnshiftedShape(BlockState state) {
		// Outer bounds of the parent model, including its rotated elements.
		return switch (state.getValue(FACING)) {
			case NORTH -> NORTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> SOUTH_SHAPE;
		};
	}
}
