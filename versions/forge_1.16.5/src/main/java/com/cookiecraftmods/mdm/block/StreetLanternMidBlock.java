package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

public class StreetLanternMidBlock extends FurnitureBlock {
	private static final VoxelShape SHAPE = box(7, 0, 7, 9, 16, 9);

	public StreetLanternMidBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

}
