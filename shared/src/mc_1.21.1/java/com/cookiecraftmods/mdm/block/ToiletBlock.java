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

public class ToiletBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ToiletBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_TILES).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(6, 3, 0, 10, 5, 2), box(6, 22, 0, 8, 24, 0.05), box(5, 21, 0, 11, 25, 0.025), box(9, 22, 0, 10, 24, 0.05), box(5, 5, 0, 11, 6, 9), box(3, 6, 0, 13, 9, 13), box(3, 9.25, 1, 13, 10.25, 13),
						box(4, 9, 1.5, 12, 9.25, 12));
				case NORTH -> Shapes.or(box(6, 3, 14, 10, 5, 16), box(8, 22, 15.95, 10, 24, 16), box(5, 21, 15.975, 11, 25, 16), box(6, 22, 15.95, 7, 24, 16), box(5, 5, 7, 11, 6, 16), box(3, 6, 3, 13, 9, 16), box(3, 9.25, 3, 13, 10.25, 15),
						box(4, 9, 4, 12, 9.25, 14.5));
				case EAST -> Shapes.or(box(0, 3, 6, 2, 5, 10), box(0, 22, 8, 0.05, 24, 10), box(0, 21, 5, 0.025, 25, 11), box(0, 22, 6, 0.05, 24, 7), box(0, 5, 5, 9, 6, 11), box(0, 6, 3, 13, 9, 13), box(1, 9.25, 3, 13, 10.25, 13),
						box(1.5, 9, 4, 12, 9.25, 12));
				case WEST -> Shapes.or(box(14, 3, 6, 16, 5, 10), box(15.95, 22, 6, 16, 24, 8), box(15.975, 21, 5, 16, 25, 11), box(15.95, 22, 9, 16, 24, 10), box(7, 5, 5, 16, 6, 11), box(3, 6, 3, 16, 9, 13), box(3, 9.25, 3, 15, 10.25, 13),
						box(4, 9, 4, 14.5, 9.25, 12));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
