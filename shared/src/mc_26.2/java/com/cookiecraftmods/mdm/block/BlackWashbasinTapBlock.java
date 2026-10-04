package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class BlackWashbasinTapBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public BlackWashbasinTapBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(7.5, 2, 0, 8.5, 2.5, 4), box(9, 0, 0.5, 10, 0.75, 1.5), box(6, 0, 0.5, 7, 0.75, 1.5), box(7.75, 1.75, 3.25, 8.25, 2, 3.75));
				case NORTH -> Shapes.or(box(7.5, 2, 12, 8.5, 2.5, 16), box(6, 0, 14.5, 7, 0.75, 15.5), box(9, 0, 14.5, 10, 0.75, 15.5), box(7.75, 1.75, 12.25, 8.25, 2, 12.75));
				case EAST -> Shapes.or(box(0, 2, 7.5, 4, 2.5, 8.5), box(0.5, 0, 6, 1.5, 0.75, 7), box(0.5, 0, 9, 1.5, 0.75, 10), box(3.25, 1.75, 7.75, 3.75, 2, 8.25));
				case WEST -> Shapes.or(box(12, 2, 7.5, 16, 2.5, 8.5), box(14.5, 0, 9, 15.5, 0.75, 10), box(14.5, 0, 6, 15.5, 0.75, 7), box(12.25, 1.75, 7.75, 12.75, 2, 8.25));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
