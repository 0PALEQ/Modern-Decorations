package com.cookiecraftmods.mdm.tests;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class PlacementUnloadRuntimeTest implements ModInitializer {
    @Override public void onInitialize() {
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> PlacementUnloadScenario.stopped());
        ServerLifecycleEvents.SERVER_STARTED.register(PlacementUnloadScenario::started);
    }
}
