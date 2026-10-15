package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TransparentFurnitureBlock extends FurnitureBlock {
    protected TransparentFurnitureBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) { return true; }

    @Override
    protected int getLightBlock(BlockState state) { return 0; }
}
