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

public class ToiletPaperRollBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ToiletPaperRollBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.CROP).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(10.5, 13, 0, 11.5, 14, 3), box(4.5, 13, 0, 5.5, 14, 3), box(6, 12, 1, 10, 15, 4), box(6, 10, 4, 10, 15, 4.025), box(5.5, 13, 2, 10.5, 14, 3));
				case NORTH -> Shapes.or(box(4.5, 13, 13, 5.5, 14, 16), box(10.5, 13, 13, 11.5, 14, 16), box(6, 12, 12, 10, 15, 15), box(6, 10, 11.975, 10, 15, 12), box(5.5, 13, 13, 10.5, 14, 14));
				case EAST -> Shapes.or(box(0, 13, 4.5, 3, 14, 5.5), box(0, 13, 10.5, 3, 14, 11.5), box(1, 12, 6, 4, 15, 10), box(4, 10, 6, 4.025, 15, 10), box(2, 13, 5.5, 3, 14, 10.5));
				case WEST -> Shapes.or(box(13, 13, 10.5, 16, 14, 11.5), box(13, 13, 4.5, 16, 14, 5.5), box(12, 12, 6, 15, 15, 10), box(11.975, 10, 6, 12, 15, 10), box(13, 13, 5.5, 14, 14, 10.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
