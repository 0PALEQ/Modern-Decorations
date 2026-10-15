package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BlackWashbasinTapBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BlackWashbasinTapBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(6, 0, 0, 10, 2.5, 4);
				case NORTH -> box(6, 0, 12, 10, 2.5, 16);
				case EAST -> box(0, 0, 6, 4, 2.5, 10);
				case WEST -> box(12, 0, 6, 16, 2.5, 10);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
