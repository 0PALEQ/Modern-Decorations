package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class Set1CornerHangingCabinetBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1CornerHangingCabinetBlock() {
		super(BlockBehaviour.Properties.of().strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.join(box(0, 0, 0, 16, 16, 16), box(9, 0, 9, 16, 16, 16), BooleanOp.ONLY_FIRST);
				case NORTH -> Shapes.join(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 7, 16, 7), BooleanOp.ONLY_FIRST);
				case EAST -> Shapes.join(box(0, 0, 0, 16, 16, 16), box(9, 0, 0, 16, 16, 7), BooleanOp.ONLY_FIRST);
				case WEST -> Shapes.join(box(0, 0, 0, 16, 16, 16), box(0, 0, 9, 7, 16, 16), BooleanOp.ONLY_FIRST);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
