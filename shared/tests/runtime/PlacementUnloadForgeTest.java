package com.cookiecraftmods.mdm.tests;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;

@Mod("mdm_placement_unload_test")
public final class PlacementUnloadForgeTest {
    public PlacementUnloadForgeTest() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.addListener(this::started);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.addListener(this::stopped);
    }
    private void started(ServerStartedEvent event) { PlacementUnloadScenario.started(event.getServer()); }
    private void stopped(ServerStoppedEvent event) { PlacementUnloadScenario.stopped(); }
}
