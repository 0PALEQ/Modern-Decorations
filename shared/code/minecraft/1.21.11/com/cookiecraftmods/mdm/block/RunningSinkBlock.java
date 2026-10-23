package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class RunningSinkBlock extends HorizontalFurnitureBlock {
    public static final BooleanProperty RUNNING = BooleanProperty.create("running");
    private static final int DRIP_INTERVAL = 2;
    private final double[][] outlets;

    protected RunningSinkBlock(BlockBehaviour.Properties properties, double outletX) {
        this(properties, new double[][]{{outletX, 16.84, 11.5}});
    }

    protected RunningSinkBlock(BlockBehaviour.Properties properties, double[][] outlets) {
        super(properties);
        this.outlets = outlets;
        registerDefaultState(defaultBlockState().setValue(RUNNING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(RUNNING);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide()) {
            boolean running = !state.getValue(RUNNING);
            level.setBlock(pos, state.setValue(RUNNING, running), Block.UPDATE_ALL);
            if (running) {
                level.scheduleTick(pos, this, DRIP_INTERVAL);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(RUNNING)) {
            return;
        }
        var offset = com.cookiecraftmods.mdm.placement.PrecisePlacement.offset(level, pos);
        for (double[] source : outlets) {
            double[] outlet = WaterOutlets.rotate(source, state.getValue(FACING));
            level.sendParticles(ParticleTypes.FALLING_WATER,
                    pos.getX() + outlet[0] + offset.x, pos.getY() + outlet[1] + offset.y, pos.getZ() + outlet[2] + offset.z,
                    1, 0, 0, 0, 0);
        }
        level.scheduleTick(pos, this, DRIP_INTERVAL);
    }

    /** Identifies only drops emitted at this furniture's model outlets. */
    public boolean isWaterOutlet(BlockState state, BlockPos pos, double x, double y, double z) {
        for (double[] source : outlets) {
            double[] outlet = WaterOutlets.rotate(source, state.getValue(FACING));
            if (Math.abs(x - pos.getX() - outlet[0]) < 0.000001
                    && Math.abs(y - pos.getY() - outlet[1]) < 0.000001
                    && Math.abs(z - pos.getZ() - outlet[2]) < 0.000001) {
                return true;
            }
        }
        return false;
    }
}
