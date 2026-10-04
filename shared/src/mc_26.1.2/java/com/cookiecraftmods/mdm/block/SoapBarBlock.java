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

public class SoapBarBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public SoapBarBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.HONEY_BLOCK).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(5, 0, 1, 11, 0.25, 5), box(5, 0.25, 1, 11, 0.5, 1.5), box(5, 0.25, 4.5, 11, 0.5, 5), box(10.5, 0.25, 1.5, 11, 0.5, 4.5), box(5, 0.25, 1.5, 5.5, 0.5, 4.5), box(6, 0.25, 2, 10, 1, 4));
				case NORTH -> Shapes.or(box(5, 0, 11, 11, 0.25, 15), box(5, 0.25, 14.5, 11, 0.5, 15), box(5, 0.25, 11, 11, 0.5, 11.5), box(5, 0.25, 11.5, 5.5, 0.5, 14.5), box(10.5, 0.25, 11.5, 11, 0.5, 14.5), box(6, 0.25, 12, 10, 1, 14));
				case EAST -> Shapes.or(box(1, 0, 5, 5, 0.25, 11), box(1, 0.25, 5, 1.5, 0.5, 11), box(4.5, 0.25, 5, 5, 0.5, 11), box(1.5, 0.25, 5, 4.5, 0.5, 5.5), box(1.5, 0.25, 10.5, 4.5, 0.5, 11), box(2, 0.25, 6, 4, 1, 10));
				case WEST -> Shapes.or(box(11, 0, 5, 15, 0.25, 11), box(14.5, 0.25, 5, 15, 0.5, 11), box(11, 0.25, 5, 11.5, 0.5, 11), box(11.5, 0.25, 10.5, 14.5, 0.5, 11), box(11.5, 0.25, 5, 14.5, 0.5, 5.5), box(12, 0.25, 6, 14, 1, 10));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
