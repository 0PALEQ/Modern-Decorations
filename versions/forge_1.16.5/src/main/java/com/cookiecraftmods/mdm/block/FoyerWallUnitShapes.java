package com.cookiecraftmods.mdm.block;

import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

final class FoyerWallUnitShapes {
    private static final double HOOK_FRONT = 14.5 - 1.625 / Math.sqrt(2.0);
    static final VoxelShape[] BOTTOM = horizontal(VoxelShapes.or(
            Block.box(-16, 0, 5, 5, 10, 16),
            Block.box(5, 0, 5, 16, 16, 16)));
    static final VoxelShape[] TOP = horizontal(VoxelShapes.or(
            Block.box(-16, -5, HOOK_FRONT, 16, 24, 16),
            Block.box(-16, 24, 10, 16, 25, 16)));

    private FoyerWallUnitShapes() {}

    static VoxelShape forFacing(VoxelShape[] shapes, Direction facing) {
        return switch (facing) {
            case EAST -> shapes[1];
            case SOUTH -> shapes[2];
            case WEST -> shapes[3];
            default -> shapes[0];
        };
    }

    private static VoxelShape[] horizontal(VoxelShape north) {
        VoxelShape east = HorizontalFurnitureBlock.rotateShape(north);
        VoxelShape south = HorizontalFurnitureBlock.rotateShape(east);
        VoxelShape west = HorizontalFurnitureBlock.rotateShape(south);
        return new VoxelShape[]{north, east, south, west};
    }
}
