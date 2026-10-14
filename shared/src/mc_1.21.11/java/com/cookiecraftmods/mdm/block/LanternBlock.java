package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class LanternBlock extends TransparentHorizontalFurnitureBlock {
	public static final net.minecraft.world.level.block.state.properties.BooleanProperty LIT = LightSwitch.LIT;


	public LanternBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.METAL).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private static final VoxelShape SHAPE_NORTH = box(3.507364, 0, 4.5, 12.442032, 11.083453, 11.5);
	private static final VoxelShape SHAPE_EAST = box(4.5, 0, 3.507364, 11.5, 11.083453, 12.442032);
	private static final VoxelShape SHAPE_SOUTH = box(3.557968, 0, 4.5, 12.492636, 11.083453, 11.5);
	private static final VoxelShape SHAPE_WEST = box(4.5, 0, 3.557968, 11.5, 11.083453, 12.492636);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> SHAPE_EAST;
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
