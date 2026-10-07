package com.cookiecraftmods.mdm.client;

import com.cookiecraftmods.mdm.init.MdmModScreens;
import net.fabricmc.api.ClientModInitializer;

public final class MdmModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MdmModScreens.register();
    }
}
