package com.cookiecraftmods.mdm.tests;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@Mod("mdm_placement_unload_test")
public final class PlacementUnloadNeoForgeTest {
    public PlacementUnloadNeoForgeTest() {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(this::started);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(this::stopped);
    }
    private void started(ServerStartedEvent event) { PlacementUnloadScenario.started(event.getServer()); }
    private void stopped(ServerStoppedEvent event) { PlacementUnloadScenario.stopped(); }
}
