package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ActionResultType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.BooleanProperty;

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

    public static ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player) {
        if (!state.hasProperty(LIT) || !Registry.BLOCK.getKey(state.getBlock()).getNamespace().equals("mdm")) {
            return ActionResultType.PASS;
        }
        if (!level.isClientSide) {
            boolean lit = !state.getValue(LIT);
            level.setBlock(pos, state.setValue(LIT, lit), 3);
            level.playSound(null, pos, lit ? ON : OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return ActionResultType.SUCCESS;
    }
}

