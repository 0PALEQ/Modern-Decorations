package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class CeillingFanBlock extends HorizontalFurnitureBlock {
	public static final net.minecraft.world.level.block.state.properties.BooleanProperty LIT = LightSwitch.LIT;

	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public CeillingFanBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(6, 11, 6, 10, 16, 10);
				case NORTH -> box(6, 11, 6, 10, 16, 10);
				case EAST -> box(6, 11, 6, 10, 16, 10);
				case WEST -> box(6, 11, 6, 10, 16, 10);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
