package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

public class FurnitureBlock extends Block implements net.minecraft.block.ITileEntityProvider {
    protected FurnitureBlock(AbstractBlock.Properties properties) {
        super(properties.dynamicShape());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, net.minecraft.world.IBlockReader level, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, net.minecraft.world.IBlockReader level, BlockPos pos) {
        return 0;
    }

    @Override
    public net.minecraft.block.material.PushReaction getPistonPushReaction(BlockState state) {
        return net.minecraft.block.material.PushReaction.BLOCK;
    }

    @Override
    public net.minecraft.tileentity.TileEntity newBlockEntity(net.minecraft.world.IBlockReader level) {
        return new com.cookiecraftmods.mdm.block.entity.DecorationPlacementBlockEntity();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }
    @Override
    public void setPlacedBy(net.minecraft.world.World level, BlockPos pos, BlockState state,
            net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        FurnitureFootprint.placeParts(level, pos, state);
    }

    @Override
    public void tick(BlockState state, net.minecraft.world.server.ServerWorld level, BlockPos pos,
            java.util.Random random) {
        if (this instanceof MulticellFurniture) FurnitureFootprint.placeParts(level, pos, state);
        else super.tick(state, level, pos, random);
    }


    @Override
    public void onRemove(BlockState state, net.minecraft.world.World level, BlockPos pos,
            BlockState replacement, boolean moving) {
        if (!state.is(replacement.getBlock())) FurnitureFootprint.removeParts(level, pos, state);
        super.onRemove(state, level, pos, replacement, moving);
    }

}

