package com.cookiecraftmods.mdm.block;

import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

/** Model-aligned basin and plumbing bounds, rotated from the north-facing model. */
final class BathroomSinkShapes {
    private static final VoxelShape NORTH = VoxelShapes.or(
            Block.box(3.5, 13, 8, 12.5, 16, 16.25),
            Block.box(7.25, 10, 12, 8.75, 13, 16.25));
    private static final VoxelShape EAST = HorizontalFurnitureBlock.rotateShape(NORTH);
    private static final VoxelShape SOUTH = HorizontalFurnitureBlock.rotateShape(EAST);
    private static final VoxelShape WEST = HorizontalFurnitureBlock.rotateShape(SOUTH);

    private BathroomSinkShapes() {}

    static VoxelShape forFacing(Direction facing) {
        return switch (facing) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }
}
