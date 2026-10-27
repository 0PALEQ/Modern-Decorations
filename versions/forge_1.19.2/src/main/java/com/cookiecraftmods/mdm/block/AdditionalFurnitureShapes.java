package com.cookiecraftmods.mdm.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class AdditionalFurnitureShapes {
    static final VoxelShape[] TALL_MIRROR = horizontal(
            Block.box(0, -16, 15, 16, 32, 16));

    static final VoxelShape[] SINK_DOUBLE = horizontal(
            Block.box(-16, 7, 4, 16, 13, 16));

    static final VoxelShape[] SINK_DOUBLE_ODD = horizontal(
            Block.box(-8, 7, 4, 24, 13, 16));

    static final VoxelShape[] STANDING_BOARD = horizontal(
            Block.box(-7, 13.5, 5.5, 23, 30, 9.5),
            Block.box(-3, 0, 3, -2, 13.5, 15),
            Block.box(17, 0, 3, 18, 13.5, 15));

    static final VoxelShape[] WHITE_BOARD = horizontal(
            Block.box(-12, 0, 13, 28, 16, 16));

    static final VoxelShape[] CORK_BOARD = horizontal(
            Block.box(-12, 0, 15, 28, 16, 16));

    static final VoxelShape[] BENCH = horizontal(
            Block.box(-13, 0, 0, -10, 15, 16),
            Block.box(26, 0, 0, 29, 15, 16),
            Block.box(-10, 11, 1, 26, 13, 15));

    static final VoxelShape[] BUNK_BED = horizontal(
            Block.box(-16, 0, 0, 16, 8, 16),
            Block.box(-16, 18, 0, 16, 27, 16),
            Block.box(-16, 8, 0, -14, 18, 16),
            Block.box(14, 8, 0, 16, 18, 16),
            Block.box(2, 8, 0, 14, 18, 1));

    static final VoxelShape[] OFFICE_DESK = horizontal(
            Block.box(-16, 15, 0, 32, 16, 16),
            Block.box(-11, 0, 5, -5, 15, 11),
            Block.box(17, 0, 0.5, 32, 15, 16));

    static final VoxelShape[] BATHTUB = horizontal(Shapes.join(
            Block.box(0, 0, 0, 16, 16, 32),
            Block.box(2, 2, 2, 14, 16, 29), BooleanOp.ONLY_FIRST));

    private AdditionalFurnitureShapes() {}

    static VoxelShape forFacing(VoxelShape[] shapes, Direction facing) {
        return switch (facing) {
            case EAST -> shapes[1];
            case SOUTH -> shapes[2];
            case WEST -> shapes[3];
            default -> shapes[0];
        };
    }

    private static VoxelShape[] horizontal(VoxelShape... boxes) {
        VoxelShape north = Shapes.or(Shapes.empty(), boxes).optimize();
        VoxelShape east = HorizontalFurnitureBlock.rotateShape(north).optimize();
        VoxelShape south = HorizontalFurnitureBlock.rotateShape(east).optimize();
        VoxelShape west = HorizontalFurnitureBlock.rotateShape(south).optimize();
        return new VoxelShape[]{north, east, south, west};
    }
}
