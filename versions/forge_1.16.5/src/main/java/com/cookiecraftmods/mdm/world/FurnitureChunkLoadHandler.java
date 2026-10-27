package com.cookiecraftmods.mdm.world;

import com.cookiecraftmods.eqlib.block.MulticellFurniture;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = "mdm")
public final class FurnitureChunkLoadHandler {
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void repairFurniture(net.minecraftforge.event.world.ChunkEvent.Load event) {
        if (!(event.getChunk() instanceof Chunk)) return;
        Chunk chunk = (Chunk) event.getChunk();
        if (!(chunk.getLevel() instanceof ServerWorld level)) return;
        var sections = chunk.getSections();
        for (int index = 0; index < sections.length; index++) {
            var section = sections[index];
            if (section == null || !section.getStates().maybeHas(state -> state.getBlock() instanceof MulticellFurniture
                    || state.getBlock() instanceof FurniturePartBlock)) continue;
            int baseY = index << 4;
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        var state = section.getBlockState(x, y, z);
                        if (state.getBlock() instanceof MulticellFurniture || state.getBlock() instanceof FurniturePartBlock) {
                            BlockPos pos = new BlockPos(chunk.getPos().getMinBlockX() + x, baseY + y, chunk.getPos().getMinBlockZ() + z);
                            level.getBlockTicks().scheduleTick(pos, state.getBlock(), 1);
                        }
                    }
                }
            }
        }
    }
}

