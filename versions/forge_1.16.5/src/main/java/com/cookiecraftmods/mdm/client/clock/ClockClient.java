package com.cookiecraftmods.mdm.client.clock;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@EventBusSubscriber(modid = "mdm", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ClockClient {
    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(MdmModBlockEntities.BLACK_WALL_CLOCK.get(), WallClockRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MdmModBlockEntities.WOODEN_WALL_CLOCK.get(), WallClockRenderer::new);
    }
}

