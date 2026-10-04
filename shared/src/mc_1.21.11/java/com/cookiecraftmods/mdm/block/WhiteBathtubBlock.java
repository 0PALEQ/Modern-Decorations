package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class WhiteBathtubBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WhiteBathtubBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.join(box(0, 0, -16, 16, 16, 16), box(2, 2, -14, 14, 16, 14), BooleanOp.ONLY_FIRST);
				case NORTH -> Shapes.join(box(0, 0, 0, 16, 16, 32), box(2, 2, 2, 14, 16, 30), BooleanOp.ONLY_FIRST);
				case EAST -> Shapes.join(box(-16, 0, 0, 16, 16, 16), box(-14, 2, 2, 14, 16, 14), BooleanOp.ONLY_FIRST);
				case WEST -> Shapes.join(box(0, 0, 0, 32, 16, 16), box(2, 2, 2, 30, 16, 14), BooleanOp.ONLY_FIRST);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
