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

public class KitchenSet3BarChairBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public KitchenSet3BarChairBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 12, 5, 11, 13, 11), box(3, 13, 3, 13, 14, 13), box(7.25, 1, 7.25, 8.75, 12, 8.75), box(3.25, 14, 3.25, 12.75, 15, 12.75), box(3.5, 15, 3.5, 12.5, 15.25, 12.5));
				case NORTH -> Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 12, 5, 11, 13, 11), box(3, 13, 3, 13, 14, 13), box(7.25, 1, 7.25, 8.75, 12, 8.75), box(3.25, 14, 3.25, 12.75, 15, 12.75), box(3.5, 15, 3.5, 12.5, 15.25, 12.5));
				case EAST -> Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 12, 5, 11, 13, 11), box(3, 13, 3, 13, 14, 13), box(7.25, 1, 7.25, 8.75, 12, 8.75), box(3.25, 14, 3.25, 12.75, 15, 12.75), box(3.5, 15, 3.5, 12.5, 15.25, 12.5));
				case WEST -> Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 12, 5, 11, 13, 11), box(3, 13, 3, 13, 14, 13), box(7.25, 1, 7.25, 8.75, 12, 8.75), box(3.25, 14, 3.25, 12.75, 15, 12.75), box(3.5, 15, 3.5, 12.5, 15.25, 12.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
