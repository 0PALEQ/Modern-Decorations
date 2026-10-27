package com.cookiecraftmods.mdm.block;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.VoxelShape;
import java.util.function.Function;
public final class LegacyShapes {
    private LegacyShapes() {}
    public static ImmutableMap<BlockState, VoxelShape> forStates(Block block, Function<BlockState, VoxelShape> shape) {
        ImmutableMap.Builder<BlockState, VoxelShape> result = ImmutableMap.builder();
        for (BlockState state : block.getStateDefinition().getPossibleStates()) result.put(state, shape.apply(state));
        return result.build();
    }
}
