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

public class ModernDeskLamp1Block extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ModernDeskLamp1Block() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> 15).noOcclusion().postProcess((bs, br, bp) -> bp).emissiveRendering(state -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(4, 0, 4, 12, 16, 12);
				case NORTH -> box(4, 0, 4, 12, 16, 12);
				case EAST -> box(4, 0, 4, 12, 16, 12);
				case WEST -> box(4, 0, 4, 12, 16, 12);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
