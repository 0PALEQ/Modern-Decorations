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

public class Set1BedBlock extends HorizontalFurnitureBlock {
	public static final net.minecraft.world.level.block.state.properties.BooleanProperty LIT = LightSwitch.LIT;

	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1BedBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 1 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, -16, 16, 8, 16);
				case NORTH -> box(0, 0, 0, 16, 8, 32);
				case EAST -> box(-16, 0, 0, 16, 8, 16);
				case WEST -> box(0, 0, 0, 32, 8, 16);
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
