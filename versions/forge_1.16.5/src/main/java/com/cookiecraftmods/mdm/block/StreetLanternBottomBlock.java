package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

public class StreetLanternBottomBlock extends FurnitureBlock {
	private static final VoxelShape SHAPE = VoxelShapes.or(box(5, 0, 5, 11, 1, 11), box(6, 1, 6, 10, 2, 10), box(7, 2, 7, 9, 16, 9));

	public StreetLanternBottomBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

}
