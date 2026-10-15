package com.cookiecraftmods.mdm.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class ModernStairsShapes {
    private static final VoxelShape NORTH = Shapes.or(
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
