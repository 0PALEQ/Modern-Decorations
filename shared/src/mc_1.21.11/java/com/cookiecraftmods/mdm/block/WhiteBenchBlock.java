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

public class WhiteBenchBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WhiteBenchBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(-13, 0, 0, -10, 15, 16), box(26, 0, 0, 29, 15, 16), box(-10, 11, 11, 26, 13, 15), box(-10, 11, 1, 26, 13, 5), box(-10, 11, 6, 26, 13, 10));
				case NORTH -> Shapes.or(box(26, 0, 0, 29, 15, 16), box(-13, 0, 0, -10, 15, 16), box(-10, 11, 1, 26, 13, 5), box(-10, 11, 11, 26, 13, 15), box(-10, 11, 6, 26, 13, 10));
				case EAST -> Shapes.or(box(0, 0, 26, 16, 15, 29), box(0, 0, -13, 16, 15, -10), box(11, 11, -10, 15, 13, 26), box(1, 11, -10, 5, 13, 26), box(6, 11, -10, 10, 13, 26));
				case WEST -> Shapes.or(box(0, 0, -13, 16, 15, -10), box(0, 0, 26, 16, 15, 29), box(1, 11, -10, 5, 13, 26), box(11, 11, -10, 15, 13, 26), box(6, 11, -10, 10, 13, 26));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
