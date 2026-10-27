package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class PottedZamioculcasBlock extends TransparentHorizontalFurnitureBlock {

	public PottedZamioculcasBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().noCollission().isRedstoneConductor((bs, br, bp) -> false));
	}

    private static final VoxelShape SHAPE = box(2, 0, 2, 14, 22, 14);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
