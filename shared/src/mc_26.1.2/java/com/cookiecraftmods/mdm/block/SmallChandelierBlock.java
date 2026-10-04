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

public class SmallChandelierBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public SmallChandelierBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(0, 14, 7, 16, 16, 9), box(1.3, 10, 7.5, 2.3, 14, 8.5), box(13.55, 10, 7.5, 14.55, 14, 8.5), box(9.55, 10, 7.5, 10.55, 14, 8.5), box(5.3, 10, 7.5, 6.3, 14, 8.5), box(0.8, 8, 7, 2.8, 10, 9),
						box(13.05, 8, 7, 15.05, 10, 9), box(9.05, 8, 7, 11.05, 10, 9), box(4.8, 8, 7, 6.8, 10, 9));
				case NORTH -> Shapes.or(box(0, 14, 7, 16, 16, 9), box(13.7, 10, 7.5, 14.7, 14, 8.5), box(1.45, 10, 7.5, 2.45, 14, 8.5), box(5.45, 10, 7.5, 6.45, 14, 8.5), box(9.7, 10, 7.5, 10.7, 14, 8.5), box(13.2, 8, 7, 15.2, 10, 9),
						box(0.95, 8, 7, 2.95, 10, 9), box(4.95, 8, 7, 6.95, 10, 9), box(9.2, 8, 7, 11.2, 10, 9));
				case EAST -> Shapes.or(box(7, 14, 0, 9, 16, 16), box(7.5, 10, 13.7, 8.5, 14, 14.7), box(7.5, 10, 1.45, 8.5, 14, 2.45), box(7.5, 10, 5.45, 8.5, 14, 6.45), box(7.5, 10, 9.7, 8.5, 14, 10.7), box(7, 8, 13.2, 9, 10, 15.2),
						box(7, 8, 0.95, 9, 10, 2.95), box(7, 8, 4.95, 9, 10, 6.95), box(7, 8, 9.2, 9, 10, 11.2));
				case WEST -> Shapes.or(box(7, 14, 0, 9, 16, 16), box(7.5, 10, 1.3, 8.5, 14, 2.3), box(7.5, 10, 13.55, 8.5, 14, 14.55), box(7.5, 10, 9.55, 8.5, 14, 10.55), box(7.5, 10, 5.3, 8.5, 14, 6.3), box(7, 8, 0.8, 9, 10, 2.8),
						box(7, 8, 13.05, 9, 10, 15.05), box(7, 8, 9.05, 9, 10, 11.05), box(7, 8, 4.8, 9, 10, 6.8));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
