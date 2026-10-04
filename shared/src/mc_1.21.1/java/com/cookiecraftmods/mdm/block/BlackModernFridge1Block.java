package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BlackModernFridge1Block extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BlackModernFridge1Block() {
		super(BlockBehaviour.Properties.of().strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 0, 1, 16, 32, 13), box(9.5, 1, 13, 15, 31, 13.5), box(1, 1, 13, 9, 31, 13.5), box(9.475, 0.975, 13, 15.025, 31.025, 13.025), box(0.975, 0.975, 13, 9.025, 31.025, 13.025), box(10, 6, 13.5, 10.25, 23, 13.75),
						box(10, 6, 13.75, 10.5, 23, 14), box(8.25, 6, 13.5, 8.5, 23, 13.75), box(8, 6, 13.75, 8.5, 23, 14), box(1, 1, 0.5, 15, 31, 1), box(0.25, 0.15, 0.975, 2.75, 0.825, 1));
				case NORTH -> Shapes.or(box(0, 0, 3, 16, 32, 15), box(1, 1, 2.5, 6.5, 31, 3), box(7, 1, 2.5, 15, 31, 3), box(0.975, 0.975, 2.975, 6.525, 31.025, 3), box(6.975, 0.975, 2.975, 15.025, 31.025, 3), box(5.75, 6, 2.25, 6, 23, 2.5),
						box(5.5, 6, 2, 6, 23, 2.25), box(7.5, 6, 2.25, 7.75, 23, 2.5), box(7.5, 6, 2, 8, 23, 2.25), box(1, 1, 15, 15, 31, 15.5), box(13.25, 0.15, 15, 15.75, 0.825, 15.025));
				case EAST -> Shapes.or(box(1, 0, 0, 13, 32, 16), box(13, 1, 1, 13.5, 31, 6.5), box(13, 1, 7, 13.5, 31, 15), box(13, 0.975, 0.975, 13.025, 31.025, 6.525), box(13, 0.975, 6.975, 13.025, 31.025, 15.025), box(13.5, 6, 5.75, 13.75, 23, 6),
						box(13.75, 6, 5.5, 14, 23, 6), box(13.5, 6, 7.5, 13.75, 23, 7.75), box(13.75, 6, 7.5, 14, 23, 8), box(0.5, 1, 1, 1, 31, 15), box(0.975, 0.15, 13.25, 1, 0.825, 15.75));
				case WEST -> Shapes.or(box(3, 0, 0, 15, 32, 16), box(2.5, 1, 9.5, 3, 31, 15), box(2.5, 1, 1, 3, 31, 9), box(2.975, 0.975, 9.475, 3, 31.025, 15.025), box(2.975, 0.975, 0.975, 3, 31.025, 9.025), box(2.25, 6, 10, 2.5, 23, 10.25),
						box(2, 6, 10, 2.25, 23, 10.5), box(2.25, 6, 8.25, 2.5, 23, 8.5), box(2, 6, 8, 2.25, 23, 8.5), box(15, 1, 1, 15.5, 31, 15), box(15, 0.15, 0.25, 15.025, 0.825, 2.75));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
