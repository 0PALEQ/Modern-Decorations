package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;

public class TransparentHorizontalFurnitureBlock extends HorizontalFurnitureBlock {
    protected TransparentHorizontalFurnitureBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader level, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, IBlockReader level, BlockPos pos) {
        return 0;
    }
}
