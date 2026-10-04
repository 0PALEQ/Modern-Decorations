package com.cookiecraftmods.mdm.client;

import com.cookiecraftmods.mdm.MdmMod;
import com.cookiecraftmods.mdm.init.MdmModScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MdmMod.MODID, value = Dist.CLIENT)
public final class MdmModClient {
    private MdmModClient() {
    }

    @SubscribeEvent
    static void registerMenuScreens(RegisterMenuScreensEvent event) {
        MdmModScreens.register(event);
    }
}
