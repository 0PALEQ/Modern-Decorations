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

public class ChlothesbinBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ChlothesbinBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SCAFFOLDING).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(5, 0, 5, 11, 10, 11), box(4.75, 10, 4.75, 11.25, 11, 11.25), box(9.75, 8, 11.1, 10.75, 10, 11.15));
				case NORTH -> Shapes.or(box(5, 0, 5, 11, 10, 11), box(4.75, 10, 4.75, 11.25, 11, 11.25), box(5.25, 8, 4.85, 6.25, 10, 4.9));
				case EAST -> Shapes.or(box(5, 0, 5, 11, 10, 11), box(4.75, 10, 4.75, 11.25, 11, 11.25), box(11.1, 8, 5.25, 11.15, 10, 6.25));
				case WEST -> Shapes.or(box(5, 0, 5, 11, 10, 11), box(4.75, 10, 4.75, 11.25, 11, 11.25), box(4.85, 8, 9.75, 4.9, 10, 10.75));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
