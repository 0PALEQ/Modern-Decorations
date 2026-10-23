package com.cookiecraftmods.mdm.block.entity;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Stores placement for decorations that do not already have a container or clock entity. */
public final class DecorationPlacementBlockEntity extends BlockEntity {
    public DecorationPlacementBlockEntity(BlockPos pos, BlockState state) {
        super(MdmModBlockEntities.DECORATION_PLACEMENT.get(), pos, state);
    }
}
