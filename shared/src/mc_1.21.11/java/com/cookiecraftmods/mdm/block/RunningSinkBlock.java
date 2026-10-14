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
    private final double outletX;

    protected RunningSinkBlock(BlockBehaviour.Properties properties, double outletX) {
        super(properties);
        this.outletX = outletX / 16.0;
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
        // Centre of the small outlet under the tap, just below its bottom face.
        double x = outletX;
        double z = 11.5 / 16.0;
        double rotatedX;
        double rotatedZ;
        switch (state.getValue(FACING)) {
            case EAST -> { rotatedX = 1 - z; rotatedZ = x; }
            case SOUTH -> { rotatedX = 1 - x; rotatedZ = 1 - z; }
            case WEST -> { rotatedX = z; rotatedZ = 1 - x; }
            default -> { rotatedX = x; rotatedZ = z; }
        }
        // FALLING_WATER starts falling immediately and accelerates downward under gravity.
        level.sendParticles(ParticleTypes.FALLING_WATER,
                pos.getX() + rotatedX, pos.getY() + 16.84 / 16.0, pos.getZ() + rotatedZ,
                1, 0, 0, 0, 0);
        level.scheduleTick(pos, this, DRIP_INTERVAL);
    }
}
