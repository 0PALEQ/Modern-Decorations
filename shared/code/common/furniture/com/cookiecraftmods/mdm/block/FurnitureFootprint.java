package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public final class FurnitureFootprint {
    public static final PushReaction IMMOVABLE = immovableReaction();

    private FurnitureFootprint() {}

    private static PushReaction immovableReaction() {
        try {
            return PushReaction.valueOf("BLOCK");
        } catch (IllegalArgumentException renamed) {
            return PushReaction.valueOf("IMMOVEABLE");
        }
    }

    public static VoxelShape fullShape(BlockState state, BlockGetter level, BlockPos pos) {
        return ((MulticellFurniture) state.getBlock()).getFurnitureShape(state, level, pos, CollisionContext.empty());
    }

    public static VoxelShape localShape(VoxelShape full, BlockPos offset) {
        return Shapes.join(full.move(-offset.getX(), -offset.getY(), -offset.getZ()), Shapes.block(), BooleanOp.AND);
    }

    public static List<BlockPos> offsets(VoxelShape full) {
        List<BlockPos> result = new ArrayList<>();
        if (full.isEmpty()) return result;
        var bounds = full.bounds();
        for (int x = (int) Math.floor(bounds.minX); x < Math.ceil(bounds.maxX); x++) {
            for (int y = (int) Math.floor(bounds.minY); y < Math.ceil(bounds.maxY); y++) {
                for (int z = (int) Math.floor(bounds.minZ); z < Math.ceil(bounds.maxZ); z++) {
                    BlockPos offset = new BlockPos(x, y, z);
                    if (!offset.equals(BlockPos.ZERO) && !localShape(full, offset).isEmpty()) result.add(offset);
                }
            }
        }
        return result;
    }

    public static BlockState placement(BlockState state, BlockPlaceContext context) {
        if (!(state.getBlock() instanceof MulticellFurniture)) return state;
        Level level = context.getLevel();
        BlockPos parent = context.getClickedPos();
        for (BlockPos offset : offsets(fullShape(state, level, parent))) {
            BlockPos cell = parent.offset(offset);
            if (level.isOutsideBuildHeight(cell) || !level.getWorldBorder().isWithinBounds(cell)) return null;
            BlockState existing = level.getBlockState(cell);
            if (existing.getBlock() instanceof FurniturePartBlock) {
                BlockPos owner = FurniturePartBlock.parentPos(existing, cell);
                if (compatible(state, parent, level.getBlockState(owner), owner)) continue;
            } else if (compatible(state, parent, existing, cell)) continue;
            if (!existing.canBeReplaced()) return null;
        }
        return state;
    }

    public static boolean compatible(BlockState a, BlockPos aPos, BlockState b, BlockPos bPos) {
        String aName = a.getBlock().getClass().getSimpleName();
        String bName = b.getBlock().getClass().getSimpleName();
        if (!aName.contains("FoyerWallUnit") || !bName.contains("FoyerWallUnit")) return false;
        boolean aTop = aName.contains("Top");
        boolean bTop = bName.contains("Top");
        if (aTop == bTop || !aPos.equals(aTop ? bPos.above() : bPos.below())) return false;
        if (!aName.replace("Top", "").replace("Bottom", "").equals(bName.replace("Top", "").replace("Bottom", ""))) return false;
        return a.getValue(BlockStateProperties.HORIZONTAL_FACING) == b.getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public static void placeParts(Level level, BlockPos parent, BlockState state) {
        if (!(state.getBlock() instanceof MulticellFurniture)) return;
        for (BlockPos offset : offsets(fullShape(state, level, parent))) {
            BlockPos cell = parent.offset(offset);
            BlockState existing = level.getBlockState(cell);
            if (existing.canBeReplaced()) {
                level.setBlock(cell, FurniturePartBlock.forOffset(offset, existing), Block.UPDATE_ALL);
            }
        }
    }

    public static void removeParts(Level level, BlockPos parent, BlockState state) {
        if (!(state.getBlock() instanceof MulticellFurniture)) return;
        SeatBlock.removeSeats(level, parent);
        for (BlockPos offset : offsets(fullShape(state, level, parent))) {
            BlockPos cell = parent.offset(offset);
            BlockState existing = level.getBlockState(cell);
            if (existing.getBlock() instanceof FurniturePartBlock && FurniturePartBlock.parentPos(existing, cell).equals(parent)) {
                level.setBlock(cell, existing.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
            }
        }
        for (BlockPos other : new BlockPos[]{parent.above(), parent.below()}) {
            BlockState neighbor = level.getBlockState(other);
            if (compatible(state, parent, neighbor, other)) level.scheduleTick(other, neighbor.getBlock(), 1);
        }
    }

    public static boolean validPart(BlockGetter level, BlockPos cell, BlockState part) {
        BlockPos parent = FurniturePartBlock.parentPos(part, cell);
        BlockState state = level.getBlockState(parent);
        return state.getBlock() instanceof MulticellFurniture
                && !localShape(fullShape(state, level, parent), cell.subtract(parent)).isEmpty();
    }

    public static VoxelShape partShape(BlockGetter level, BlockPos cell, BlockState part) {
        return partShape(level, cell, part, false);
    }

    public static VoxelShape partCollisionShape(BlockGetter level, BlockPos cell, BlockState part) {
        return partShape(level, cell, part, true);
    }

    private static VoxelShape partShape(BlockGetter level, BlockPos cell, BlockState part, boolean collision) {
        if (!validPart(level, cell, part)) return Shapes.empty();
        BlockPos parent = FurniturePartBlock.parentPos(part, cell);
        BlockState owner = level.getBlockState(parent);
        VoxelShape shape = localShape(partSourceShape(owner, level, parent, collision), cell.subtract(parent));
        for (BlockPos other : new BlockPos[]{parent.above(), parent.below()}) {
            BlockState neighbor = level.getBlockState(other);
            if (compatible(owner, parent, neighbor, other)) {
                shape = Shapes.or(shape, localShape(partSourceShape(neighbor, level, other, collision), cell.subtract(other)));
            }
        }
        return shape;
    }

    private static VoxelShape partSourceShape(BlockState state, BlockGetter level, BlockPos pos, boolean collision) {
        MulticellFurniture furniture = (MulticellFurniture) state.getBlock();
        return collision ? furniture.getFurnitureCollisionShape(state, level, pos, CollisionContext.empty())
                : furniture.getFurnitureShape(state, level, pos, CollisionContext.empty());
    }

    public static void partRemoved(Level level, BlockPos cell, BlockState part, BlockState replacement) {
        BlockPos parent = FurniturePartBlock.parentPos(part, cell);
        BlockState owner = level.getBlockState(parent);
        if (validPart(level, cell, part) && !compatible(owner, parent, replacement, cell)) {
            destroyParents(level, cell, part, true);
        }
    }

    public static void destroyParents(Level level, BlockPos cell, BlockState part, boolean drops) {
        BlockPos parent = FurniturePartBlock.parentPos(part, cell);
        BlockState owner = level.getBlockState(parent);
        if (validPart(level, cell, part)) {
            List<BlockPos> shared = new ArrayList<>();
            for (BlockPos other : new BlockPos[]{parent.above(), parent.below()}) {
                BlockState neighbor = level.getBlockState(other);
                if (compatible(owner, parent, neighbor, other)
                        && !localShape(fullShape(neighbor, level, other), cell.subtract(other)).isEmpty()) shared.add(other);
            }
            level.destroyBlock(parent, drops);
            for (BlockPos other : shared) level.destroyBlock(other, drops);
        }
    }
}
