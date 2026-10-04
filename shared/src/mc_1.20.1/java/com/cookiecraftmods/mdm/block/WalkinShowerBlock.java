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

public class WalkinShowerBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WalkinShowerBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 0, -16, 16, 0.25, 16), box(0, 0, 15, 16, 32, 16), box(15, 0, -16, 16, 32, 0));
				case NORTH -> Shapes.or(box(0, 0, 0, 16, 0.25, 32), box(0, 0, 0, 16, 32, 1), box(0, 0, 16, 1, 32, 32));
				case EAST -> Shapes.or(box(-16, 0, 0, 16, 0.25, 16), box(15, 0, 0, 16, 32, 16), box(-16, 0, 0, 0, 32, 1));
				case WEST -> Shapes.or(box(0, 0, 0, 32, 0.25, 16), box(0, 0, 0, 1, 32, 16), box(16, 0, 15, 32, 32, 16));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
