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

public class ModernDesk1Block extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ModernDesk1Block() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(-16, 15, 0, 32, 16, 16), box(24.25, 0, 0, 26.25, 1, 16), box(-10.25, 0, 0, -8.25, 1, 16));
				case NORTH -> Shapes.or(box(-16, 15, 0, 32, 16, 16), box(-10.25, 0, 0, -8.25, 1, 16), box(24.25, 0, 0, 26.25, 1, 16));
				case EAST -> Shapes.or(box(0, 15, -16, 16, 16, 32), box(0, 0, -10.25, 16, 1, -8.25), box(0, 0, 24.25, 16, 1, 26.25));
				case WEST -> Shapes.or(box(0, 15, -16, 16, 16, 32), box(0, 0, 24.25, 16, 1, 26.25), box(0, 0, -10.25, 16, 1, -8.25));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
