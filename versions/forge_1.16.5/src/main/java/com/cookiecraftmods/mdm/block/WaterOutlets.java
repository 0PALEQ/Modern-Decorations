package com.cookiecraftmods.mdm.block;

import net.minecraft.util.Direction;

final class WaterOutlets {
    static final double[][] SHOWER = {{8, 5.14, 8}};

    private WaterOutlets() {}

    static double[] rotate(double[] outlet, Direction facing) {
        double x = outlet[0] / 16.0;
        double y = outlet[1] / 16.0;
        double z = outlet[2] / 16.0;
        return switch (facing) {
            case EAST -> new double[]{1 - z, y, x};
            case SOUTH -> new double[]{1 - x, y, 1 - z};
            case WEST -> new double[]{z, y, 1 - x};
            default -> new double[]{x, y, z};
        };
    }
}
