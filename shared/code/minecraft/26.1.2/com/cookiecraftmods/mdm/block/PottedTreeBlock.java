package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.SoundType;

public class PottedTreeBlock extends TransparentHorizontalFurnitureBlock {

	public PottedTreeBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private static final VoxelShape SHAPE_NORTH = box(0, 0, 1, 15.227033, 22, 15.017917);
	private static final VoxelShape SHAPE_EAST = box(0.982083, 0, 0, 15, 22, 15.227033);
	private static final VoxelShape SHAPE_SOUTH = box(0.772967, 0, 0.982083, 16, 22, 15);
	private static final VoxelShape SHAPE_WEST = box(1, 0, 0.772967, 15.017917, 22, 16);

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
