package com.cookiecraftmods.mdm.block;
import net.minecraft.state.BooleanProperty;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BigCeilingLightBlock extends HorizontalFurnitureBlock {
	public static final net.minecraft.state.BooleanProperty LIT = LightSwitch.LIT;

	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BigCeilingLightBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(0, 13, 0, 16, 16, 16);
				case NORTH -> box(0, 13, 0, 16, 16, 16);
				case EAST -> box(0, 13, 0, 16, 16, 16);
				case WEST -> box(0, 13, 0, 16, 16, 16);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
