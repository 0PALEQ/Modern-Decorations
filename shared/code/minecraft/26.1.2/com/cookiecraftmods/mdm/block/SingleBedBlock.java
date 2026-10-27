package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class SingleBedBlock extends BedBlock {
    protected SingleBedBlock(BlockBehaviour.Properties properties) {
        this(DyeColor.WHITE, properties);
    }

    protected SingleBedBlock(DyeColor color, BlockBehaviour.Properties properties) {
        super(color, properties.dynamicShape());
        registerDefaultState(defaultBlockState().setValue(PART, BedPart.HEAD));
    }

    public static Direction sleepDirection(BlockState state) {
        return state.getValue(FACING).getOpposite();
    }

    public static InteractionResult tryUse(BlockState state, Level level, BlockPos pos, Player player) {
        if (player.isSpectator()) return InteractionResult.PASS;
        if (state.getBlock() instanceof FurniturePartBlock && FurnitureFootprint.validPart(level, pos, state)) {
            pos = FurniturePartBlock.parentPos(state, pos);
            state = level.getBlockState(pos);
        }
        return state.getBlock() instanceof SingleBedBlock bed
                ? bed.interact(state, level, pos, player) : InteractionResult.PASS;
    }

    public InteractionResult interact(BlockState state, Level level, BlockPos pos, Player player) {
        if (player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof MenuProvider provider) {
                serverPlayer.openMenu(provider);
            }
            return InteractionResult.SUCCESS;
        }
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        return super.useWithoutItem(state, level, pos, player, hit);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    // BedBlock would place a second inventory-owning block. Keep existing stuff.
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        FurnitureFootprint.placeParts(level, pos, state);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        FurnitureFootprint.placeParts(level, pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState updateShape(BlockState state, net.minecraft.world.level.LevelReader level,
            net.minecraft.world.level.ScheduledTickAccess ticks, BlockPos pos, Direction direction,
            BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return state;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        FurnitureFootprint.removeParts(level, pos, state);
        super.affectNeighborsAfterRemoval(state, level, pos, moving);
    }
}
