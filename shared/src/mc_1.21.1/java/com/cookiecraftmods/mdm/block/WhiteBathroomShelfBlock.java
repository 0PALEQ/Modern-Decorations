package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class WhiteBathroomShelfBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WhiteBathroomShelfBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 0, 0, 16, 16, 7), box(9, 1, 7, 15, 15, 7.5), box(1, 1, 7, 7, 15, 7.5), box(9.5, 6, 7.5, 10, 13, 7.75), box(6, 6, 7.5, 6.5, 13, 7.75));
				case NORTH -> Shapes.or(box(0, 0, 9, 16, 16, 16), box(1, 1, 8.5, 7, 15, 9), box(9, 1, 8.5, 15, 15, 9), box(6, 6, 8.25, 6.5, 13, 8.5), box(9.5, 6, 8.25, 10, 13, 8.5));
				case EAST -> Shapes.or(box(0, 0, 0, 7, 16, 16), box(7, 1, 1, 7.5, 15, 7), box(7, 1, 9, 7.5, 15, 15), box(7.5, 6, 6, 7.75, 13, 6.5), box(7.5, 6, 9.5, 7.75, 13, 10));
				case WEST -> Shapes.or(box(9, 0, 0, 16, 16, 16), box(8.5, 1, 9, 9, 15, 15), box(8.5, 1, 1, 9, 15, 7), box(8.25, 6, 9.5, 8.5, 13, 10), box(8.25, 6, 6, 8.5, 13, 6.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
