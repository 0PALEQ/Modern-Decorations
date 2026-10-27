package com.cookiecraftmods.mdm.block;
import net.minecraft.state.BooleanProperty;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class WallLamp2Block extends HorizontalFurnitureBlock {
	public static final net.minecraft.state.BooleanProperty LIT = LightSwitch.LIT;

	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WallLamp2Block() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(7, 6, 0, 9, 10, 5);
				case NORTH -> box(7, 6, 11, 9, 10, 16);
				case EAST -> box(0, 6, 7, 5, 10, 9);
				case WEST -> box(11, 6, 7, 16, 10, 9);
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
