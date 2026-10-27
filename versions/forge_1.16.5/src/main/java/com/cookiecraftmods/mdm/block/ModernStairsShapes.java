package com.cookiecraftmods.mdm.block;

import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

final class ModernStairsShapes {
    private static final VoxelShape NORTH = VoxelShapes.or(
            Block.box(0, 0, 0, 16, 9, 8),
            Block.box(0, 0, 8, 16, 17, 16));
    private static final VoxelShape EAST = HorizontalFurnitureBlock.rotateShape(NORTH);
    private static final VoxelShape SOUTH = HorizontalFurnitureBlock.rotateShape(EAST);
    private static final VoxelShape WEST = HorizontalFurnitureBlock.rotateShape(SOUTH);

    private ModernStairsShapes() {}

    static VoxelShape forFacing(Direction facing) {
        return switch (facing) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }
}
