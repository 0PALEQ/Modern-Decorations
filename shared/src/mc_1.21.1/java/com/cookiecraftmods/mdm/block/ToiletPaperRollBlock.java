package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class ToiletPaperRollBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ToiletPaperRollBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.CROP).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(4.5, 10, 0, 11.5, 15, 4.025);
				case NORTH -> box(4.5, 10, 11.975, 11.5, 15, 16);
				case EAST -> box(0, 10, 4.5, 4.025, 15, 11.5);
				case WEST -> box(11.975, 10, 4.5, 16, 15, 11.5);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
