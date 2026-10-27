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

public class Lamp03Block extends FurnitureBlock {
	public static final net.minecraft.state.BooleanProperty LIT = LightSwitch.LIT;

	private static final VoxelShape SHAPE = box(3, 0, 3, 13, 32, 13);

	public Lamp03Block() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOL).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
