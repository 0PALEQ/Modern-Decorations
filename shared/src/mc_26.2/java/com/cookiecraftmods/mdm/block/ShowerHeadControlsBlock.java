package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class ShowerHeadControlsBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ShowerHeadControlsBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> box(5, -10.5, 12.9, 11, 12.6, 16);
				case EAST -> box(0, -10.5, 5, 3.1, 12.6, 11);
				case WEST -> box(12.9, -10.5, 5, 16, 12.6, 11);
				default -> box(5, -10.5, 0, 11, 12.6, 3.1);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
