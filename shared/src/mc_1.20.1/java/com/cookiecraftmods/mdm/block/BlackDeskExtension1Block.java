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

public class BlackDeskExtension1Block extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BlackDeskExtension1Block() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 0, 0, 6, 15, 15), box(7, 1, 1, 15, 15, 16), box(0, 15, 0, 16, 16, 16));
				case NORTH -> Shapes.or(box(10, 0, 1, 16, 15, 16), box(1, 1, 0, 9, 15, 15), box(0, 15, 0, 16, 16, 16));
				case EAST -> Shapes.or(box(0, 0, 10, 15, 15, 16), box(1, 1, 1, 16, 15, 9), box(0, 15, 0, 16, 16, 16));
				case WEST -> Shapes.or(box(1, 0, 0, 16, 15, 6), box(0, 1, 7, 15, 15, 15), box(0, 15, 0, 16, 16, 16));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
