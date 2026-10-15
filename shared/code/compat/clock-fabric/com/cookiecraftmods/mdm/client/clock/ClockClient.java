package com.cookiecraftmods.mdm.client.clock;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public final class ClockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MdmModBlockEntities.BLACK_WALL_CLOCK.get(), WallClockRenderer::new);
        BlockEntityRendererRegistry.register(MdmModBlockEntities.WOODEN_WALL_CLOCK.get(), WallClockRenderer::new);
    }
}
