package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface MulticellFurniture {
    VoxelShape getFurnitureShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);

    default VoxelShape getFurnitureCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getFurnitureShape(state, level, pos, context);
    }
}
