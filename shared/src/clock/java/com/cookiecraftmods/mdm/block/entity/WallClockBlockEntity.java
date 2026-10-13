package com.cookiecraftmods.mdm.block.entity;

import com.cookiecraftmods.mdm.block.WoodenWallClockBlock;
import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class WallClockBlockEntity extends BlockEntity {
    public WallClockBlockEntity(BlockPos pos, BlockState state) {
        super(state.getBlock() instanceof WoodenWallClockBlock
                ? MdmModBlockEntities.WOODEN_WALL_CLOCK.get()
                : MdmModBlockEntities.BLACK_WALL_CLOCK.get(), pos, state);
    }
}
