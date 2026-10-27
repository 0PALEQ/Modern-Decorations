package com.cookiecraftmods.mdm.block;

import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

/** Model-aligned bounds, defined once facing north and rotated for placement. */
final class OfficeDeskShapes {
    static final VoxelShape[] DESK_2_SIDE = horizontal(Block.box(0, 0, 0, 16, 16, 16));
    static final VoxelShape[] DESK_1_RIGHT = horizontal(VoxelShapes.or(Block.box(0, 0, 0, 1, 16, 16),
            Block.box(1, 12, 0, 16, 16, 16)));
    static final VoxelShape[] DESK_1_MIDDLE = horizontal(VoxelShapes.or(Block.box(0, 15, 0, 16, 16, 16),
            Block.box(0, 12, 2, 16, 15, 15)));
    static final VoxelShape[] DESK_1_LEFT = horizontal(VoxelShapes.or(Block.box(15, 0, 0, 16, 16, 16),
            Block.box(0, 12, 0, 15, 16, 16)));
    static final VoxelShape[] DESK_2_MID = horizontal(VoxelShapes.or(Block.box(0, 15, 0, 16, 16, 16),
            Block.box(-1, 0, 14, 17, 15, 16)));

    private OfficeDeskShapes() {}

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
