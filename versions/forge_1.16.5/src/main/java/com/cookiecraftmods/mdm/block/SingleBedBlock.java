package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import java.util.Random;
import net.minecraft.util.ActionResultType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.World;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public abstract class SingleBedBlock extends BedBlock {
    protected SingleBedBlock(AbstractBlock.Properties properties) {
        this(DyeColor.WHITE, properties);
    }

    protected SingleBedBlock(DyeColor color, AbstractBlock.Properties properties) {
        super(color, properties.dynamicShape());
        registerDefaultState(defaultBlockState().setValue(PART, BedPart.HEAD));
    }

    public static Direction sleepDirection(BlockState state) {
        return state.getValue(FACING).getOpposite();
    }

    public static ActionResultType tryUse(BlockState state, World level, BlockPos pos, PlayerEntity player) {
        if (player.isSpectator()) return ActionResultType.PASS;
        if (state.getBlock() instanceof FurniturePartBlock && FurnitureFootprint.validPart(level, pos, state)) {
            pos = FurniturePartBlock.parentPos(state, pos);
            state = level.getBlockState(pos);
        }
        return state.getBlock() instanceof SingleBedBlock bed
                ? bed.interact(state, level, pos, player) : ActionResultType.PASS;
    }

    public ActionResultType interact(BlockState state, World level, BlockPos pos, PlayerEntity player) {
        if (player.isShiftKeyDown()) {
            if (player instanceof ServerPlayerEntity serverPlayer && level.getBlockEntity(pos) instanceof INamedContainerProvider provider) {
                serverPlayer.openMenu(provider);
            }
            return ActionResultType.SUCCESS;
        }
        BlockRayTraceResult hit = new BlockRayTraceResult(Vector3d.atCenterOf(pos), Direction.UP, pos, false);
        return super.use(state, level, pos, player, net.minecraft.util.Hand.MAIN_HAND, hit);
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState();
    }

    // BedBlock would place a second inventory-owning block. Keep existing stuff.
    @Override
    public void setPlacedBy(World level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        FurnitureFootprint.placeParts(level, pos, state);
    }

    @Override
    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        FurnitureFootprint.placeParts(level, pos, state);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            net.minecraft.world.IWorld level, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    @Override
    public void onRemove(BlockState state, World level, BlockPos pos, BlockState replacement, boolean moving) {
        if (!state.is(replacement.getBlock())) FurnitureFootprint.removeParts(level, pos, state);
        super.onRemove(state, level, pos, replacement, moving);
    }
}
