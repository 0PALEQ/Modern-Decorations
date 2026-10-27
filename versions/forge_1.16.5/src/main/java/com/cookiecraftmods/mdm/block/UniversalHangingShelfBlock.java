package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

public class UniversalHangingShelfBlock extends HorizontalFurnitureBlock {
    // Bounds of the shelf and sink bowl; the decorative tap is excluded.
    private static final VoxelShape NORTH_SHAPE = box(0, 7, 4, 16, 13, 16);
    private static final VoxelShape EAST_SHAPE = HorizontalFurnitureBlock.rotateShape(NORTH_SHAPE);
    private static final VoxelShape SOUTH_SHAPE = HorizontalFurnitureBlock.rotateShape(EAST_SHAPE);
    private static final VoxelShape WEST_SHAPE = HorizontalFurnitureBlock.rotateShape(SOUTH_SHAPE);

    public UniversalHangingShelfBlock() {
        super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}
