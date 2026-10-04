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

public class BathroomMirrorBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public BathroomMirrorBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().postProcess((bs, br, bp) -> bp).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(1, 1, 0, 15, 3, 2), box(14, 1.5, 1, 15, 2.5, 3), box(1, 1.5, 2, 2, 2.5, 4), box(13.5, 0.7, 2.7, 15.5, 2.7, 3.7), box(0.5, 0.7, 2.7, 2.5, 2.7, 3.7), box(3, -12, 0.25, 13, -1, 0.5), box(4, -11, 0, 12, -2, 0.25),
						box(3, -12, 0.5, 13, -11.75, 3));
				case NORTH -> Shapes.or(box(1, 1, 14, 15, 3, 16), box(1, 1.5, 13, 2, 2.5, 15), box(14, 1.5, 12, 15, 2.5, 14), box(0.5, 0.7, 12.3, 2.5, 2.7, 13.3), box(13.5, 0.7, 12.3, 15.5, 2.7, 13.3), box(3, -12, 15.5, 13, -1, 15.75),
						box(4, -11, 15.75, 12, -2, 16), box(3, -12, 13, 13, -11.75, 15.5));
				case EAST -> Shapes.or(box(0, 1, 1, 2, 3, 15), box(1, 1.5, 1, 3, 2.5, 2), box(2, 1.5, 14, 4, 2.5, 15), box(2.7, 0.7, 0.5, 3.7, 2.7, 2.5), box(2.7, 0.7, 13.5, 3.7, 2.7, 15.5), box(0.25, -12, 3, 0.5, -1, 13),
						box(0, -11, 4, 0.25, -2, 12), box(0.5, -12, 3, 3, -11.75, 13));
				case WEST -> Shapes.or(box(14, 1, 1, 16, 3, 15), box(13, 1.5, 14, 15, 2.5, 15), box(12, 1.5, 1, 14, 2.5, 2), box(12.3, 0.7, 13.5, 13.3, 2.7, 15.5), box(12.3, 0.7, 0.5, 13.3, 2.7, 2.5), box(15.5, -12, 3, 15.75, -1, 13),
						box(15.75, -11, 4, 16, -2, 12), box(13, -12, 3, 15.5, -11.75, 13));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
