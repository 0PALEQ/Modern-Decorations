package com.cookiecraftmods.mdm.block.entity;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockState;

/** Stores placement for decorations that do not already have a container or clock entity. */
public final class DecorationPlacementBlockEntity extends TileEntity {
    public DecorationPlacementBlockEntity() {
        super(MdmModBlockEntities.DECORATION_PLACEMENT.get());
    }
}
