package com.cookiecraftmods.mdm.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Model-aligned bounds, defined once facing north and rotated for placement. */
final class OfficeDeskShapes {
    static final VoxelShape[] DESK_2_SIDE = horizontal(Block.box(0, 0, 0, 16, 16, 16));
    static final VoxelShape[] DESK_1_RIGHT = horizontal(Shapes.or(Block.box(0, 0, 0, 1, 16, 16),
            Block.box(1, 12, 0, 16, 16, 16)));
    static final VoxelShape[] DESK_1_MIDDLE = horizontal(Shapes.or(Block.box(0, 15, 0, 16, 16, 16),
            Block.box(0, 12, 2, 16, 15, 15)));
    static final VoxelShape[] DESK_1_LEFT = horizontal(Shapes.or(Block.box(15, 0, 0, 16, 16, 16),
            Block.box(0, 12, 0, 15, 16, 16)));
    static final VoxelShape[] DESK_2_MID = horizontal(Shapes.or(Block.box(0, 15, 0, 16, 16, 16),
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
