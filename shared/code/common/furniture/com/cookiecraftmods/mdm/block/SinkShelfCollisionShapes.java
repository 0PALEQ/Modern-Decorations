package com.cookiecraftmods.mdm.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class SinkShelfCollisionShapes {
    static final VoxelShape[] SINGLE = create(0, 16, 8);
    static final VoxelShape[] DOUBLE = create(-16, 16, 0);
    static final VoxelShape[] DOUBLE_ODD = create(-8, 24, 8);

    private SinkShelfCollisionShapes() {}

    private static VoxelShape[] create(double minX, double maxX, double sinkX) {
        VoxelShape basin = Shapes.join(
                Block.box(minX, 7, 4, maxX, 15, 16),
                Block.box(sinkX - 5, 13.25, 7, sinkX + 5, 15, 13),
                BooleanOp.ONLY_FIRST);
        VoxelShape north = Shapes.or(basin,
                Block.box(sinkX - 0.5, 13.25, 10.5, sinkX + 0.5, 13.4, 11.5));
        VoxelShape east = HorizontalFurnitureBlock.rotateShape(north);
        VoxelShape south = HorizontalFurnitureBlock.rotateShape(east);
        VoxelShape west = HorizontalFurnitureBlock.rotateShape(south);
        return new VoxelShape[]{north, east, south, west};
    }

    static VoxelShape forFacing(VoxelShape[] shapes, Direction facing) {
        return switch (facing) {
            case EAST -> shapes[1];
            case SOUTH -> shapes[2];
            case WEST -> shapes[3];
            default -> shapes[0];
        };
    }
}
