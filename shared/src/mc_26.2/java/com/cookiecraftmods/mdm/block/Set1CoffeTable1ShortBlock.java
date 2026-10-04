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

public class Set1CoffeTable1ShortBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1CoffeTable1ShortBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 5, 1, 15, 8, 15), box(8.5, 0, 0.25, 10.5, 1, 15.75), box(8.5, 4.5, 1, 10.5, 5, 15), box(8.5, 1, 0.25, 10.5, 7, 1), box(8.5, 1, 15, 10.5, 7, 15.75));
				case NORTH -> Shapes.or(box(1, 5, 1, 16, 8, 15), box(5.5, 0, 0.25, 7.5, 1, 15.75), box(5.5, 4.5, 1, 7.5, 5, 15), box(5.5, 1, 15, 7.5, 7, 15.75), box(5.5, 1, 0.25, 7.5, 7, 1));
				case EAST -> Shapes.or(box(1, 5, 1, 15, 8, 16), box(0.25, 0, 5.5, 15.75, 1, 7.5), box(1, 4.5, 5.5, 15, 5, 7.5), box(0.25, 1, 5.5, 1, 7, 7.5), box(15, 1, 5.5, 15.75, 7, 7.5));
				case WEST -> Shapes.or(box(1, 5, 0, 15, 8, 15), box(0.25, 0, 8.5, 15.75, 1, 10.5), box(1, 4.5, 8.5, 15, 5, 10.5), box(15, 1, 8.5, 15.75, 7, 10.5), box(0.25, 1, 8.5, 1, 7, 10.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
