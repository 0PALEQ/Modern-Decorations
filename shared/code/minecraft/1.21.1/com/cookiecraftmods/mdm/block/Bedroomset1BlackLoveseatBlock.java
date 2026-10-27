package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class Bedroomset1BlackLoveseatBlock extends HorizontalFurnitureBlock implements MulticellFurniture {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public Bedroomset1BlackLoveseatBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).pushReaction(FurnitureFootprint.IMMOVABLE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 0, 16, 16, 32);
				case NORTH -> box(0, 0, -16, 16, 16, 16);
				case EAST -> box(0, 0, 0, 32, 16, 16);
				case WEST -> box(-16, 0, 0, 16, 16, 16);
			};
		});
	}

	@Override
	public VoxelShape getFurnitureShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
	}

}
