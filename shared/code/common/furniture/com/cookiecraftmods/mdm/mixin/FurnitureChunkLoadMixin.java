package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.MulticellFurniture;
import com.cookiecraftmods.mdm.block.FurniturePartBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public abstract class FurnitureChunkLoadMixin {
    @Inject(method = "registerTickContainerInLevel", at = @At("RETURN"))
    private void mdm$repairFurniture(CallbackInfo callback) {
        LevelChunk chunk = (LevelChunk) (Object) this;
        if (!(chunk.getLevel() instanceof ServerLevel level)) return;
        var sections = chunk.getSections();
        for (int index = 0; index < sections.length; index++) {
            var section = sections[index];
            if (!section.getStates().maybeHas(state -> state.getBlock() instanceof MulticellFurniture
                    || state.getBlock() instanceof FurniturePartBlock)) continue;
            int baseY = chunk.getSectionYFromSectionIndex(index) << 4;
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        var state = section.getBlockState(x, y, z);
                        if (state.getBlock() instanceof MulticellFurniture || state.getBlock() instanceof FurniturePartBlock) {
                            BlockPos pos = new BlockPos(chunk.getPos().getMinBlockX() + x, baseY + y, chunk.getPos().getMinBlockZ() + z);
                            level.scheduleTick(pos, state.getBlock(), 1);
                        }
                    }
                }
            }
        }
    }
}
