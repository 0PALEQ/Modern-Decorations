package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;
import java.util.Random;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockRayTraceResult;

public abstract class RunningSinkBlock extends HorizontalFurnitureBlock {
    public static final BooleanProperty RUNNING = BooleanProperty.create("running");
    private static final int DRIP_INTERVAL = 2;
    private final double[][] outlets;

    protected RunningSinkBlock(AbstractBlock.Properties properties, double outletX) {
        this(properties, new double[][]{{outletX, 16.84, 11.5}});
    }

    protected RunningSinkBlock(AbstractBlock.Properties properties, double[][] outlets) {
        super(properties);
        this.outlets = outlets;
        registerDefaultState(defaultBlockState().setValue(RUNNING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(RUNNING);
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!level.isClientSide) {
            boolean running = !state.getValue(RUNNING);
            level.setBlock(pos, state.setValue(RUNNING, running), 3);
            if (running) {
                level.getBlockTicks().scheduleTick(pos, this, DRIP_INTERVAL);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (!state.getValue(RUNNING)) {
            return;
        }
        var offset = com.cookiecraftmods.eqlib.placement.PrecisePlacement.offset(level, pos);
        for (double[] source : outlets) {
            double[] outlet = WaterOutlets.rotate(source, state.getValue(FACING));
            level.sendParticles(ParticleTypes.FALLING_WATER,
                    pos.getX() + outlet[0] + offset.x, pos.getY() + outlet[1] + offset.y, pos.getZ() + outlet[2] + offset.z,
                    1, 0, 0, 0, 0);
        }
        level.getBlockTicks().scheduleTick(pos, this, DRIP_INTERVAL);
    }

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
