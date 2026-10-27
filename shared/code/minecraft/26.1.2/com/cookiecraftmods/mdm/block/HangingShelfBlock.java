package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class HangingShelfBlock extends HorizontalFurnitureBlock implements MulticellFurniture {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public HangingShelfBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().pushReaction(FurnitureFootprint.IMMOVABLE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 0, 32, 16, 7);
				case NORTH -> box(-16, 0, 9, 16, 16, 16);
				case EAST -> box(0, 0, -16, 7, 16, 16);
				case WEST -> box(9, 0, 0, 16, 16, 32);
			};
		});
	}

	@Override
	public VoxelShape getFurnitureShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
    }

}
