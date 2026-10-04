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

public class WhiteModernStairsBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WhiteModernStairsBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 8, 9, 16, 9, 16), box(0, 16, 0, 16, 17, 7), box(6.5, 0, 10, 9.5, 1, 16), box(6.5, 1, 12.5, 9.5, 8, 13.5), box(6.5, 7, 0, 9.5, 16, 1), box(6.5, 9.75, 2.5, 9.5, 17.75, 3.5), box(6.5, 6, 0, 9.5, 7, 12.5));
				case NORTH -> Shapes.or(box(0, 8, 0, 16, 9, 7), box(0, 16, 9, 16, 17, 16), box(6.5, 0, 0, 9.5, 1, 6), box(6.5, 1, 2.5, 9.5, 8, 3.5), box(6.5, 7, 15, 9.5, 16, 16), box(6.5, 9.75, 12.5, 9.5, 17.75, 13.5), box(6.5, 6, 3.5, 9.5, 7, 16));
				case EAST -> Shapes.or(box(9, 8, 0, 16, 9, 16), box(0, 16, 0, 7, 17, 16), box(10, 0, 6.5, 16, 1, 9.5), box(12.5, 1, 6.5, 13.5, 8, 9.5), box(0, 7, 6.5, 1, 16, 9.5), box(2.5, 9.75, 6.5, 3.5, 17.75, 9.5), box(0, 6, 6.5, 12.5, 7, 9.5));
				case WEST -> Shapes.or(box(0, 8, 0, 7, 9, 16), box(9, 16, 0, 16, 17, 16), box(0, 0, 6.5, 6, 1, 9.5), box(2.5, 1, 6.5, 3.5, 8, 9.5), box(15, 7, 6.5, 16, 16, 9.5), box(12.5, 9.75, 6.5, 13.5, 17.75, 9.5), box(3.5, 6, 6.5, 16, 7, 9.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
