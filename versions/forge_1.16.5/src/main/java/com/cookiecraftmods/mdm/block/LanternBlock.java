package com.cookiecraftmods.mdm.block;
import net.minecraft.state.BooleanProperty;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

public class LanternBlock extends TransparentHorizontalFurnitureBlock {
	public static final net.minecraft.state.BooleanProperty LIT = LightSwitch.LIT;


	public LanternBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.METAL).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private static final VoxelShape SHAPE_NORTH = box(3.507364, 0, 4.5, 12.442032, 11.083453, 11.5);
	private static final VoxelShape SHAPE_EAST = box(4.5, 0, 3.507364, 11.5, 11.083453, 12.442032);
	private static final VoxelShape SHAPE_SOUTH = box(3.557968, 0, 4.5, 12.492636, 11.083453, 11.5);
	private static final VoxelShape SHAPE_WEST = box(4.5, 0, 3.557968, 11.5, 11.083453, 12.492636);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> SHAPE_EAST;
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
