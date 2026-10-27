package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public final class LightSwitch {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final SoundEvent ON = new SoundEvent(id("light_on"));
    public static final SoundEvent OFF = new SoundEvent(id("light_off"));

    private LightSwitch() {}

    private static ResourceLocation id(String path) {
        return new ResourceLocation("mdm", path);
    }

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, id("light_on"), ON);
        Registry.register(Registry.SOUND_EVENT, id("light_off"), OFF);
    }

    public static InteractionResult use(BlockState state, Level level, BlockPos pos, Player player) {
        if (!state.hasProperty(LIT) || !Registry.BLOCK.getKey(state.getBlock()).getNamespace().equals("mdm")) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            boolean lit = !state.getValue(LIT);
            level.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL);
            level.playSound(null, pos, lit ? ON : OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, net.minecraft.world.level.gameevent.GameEvent.BLOCK_CHANGE, pos);
        }
        return InteractionResult.SUCCESS;
    }
}
