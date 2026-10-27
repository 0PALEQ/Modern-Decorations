package com.cookiecraftmods.mdm.tests;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.placement.PlacementOffsetAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.MinecraftServer;

/** Shared fixture: a precisely placed entity spanning chunk edges must save and unload without reloading chunks. */
public final class PlacementUnloadScenario {
    private static final java.util.concurrent.atomic.AtomicInteger removals = new java.util.concurrent.atomic.AtomicInteger();
    public static void stopped() {
        if (removals.get() == 0) throw new AssertionError("Shutdown did not unload a precisely placed entity");
        String result = "PLACEMENT_UNLOAD_SHUTDOWN_PASSED: " + removals.get() + " precisely placed entities unloaded";
        try { java.nio.file.Files.writeString(java.nio.file.Path.of("placement-unload-result.txt"), result); }
        catch (java.io.IOException failure) { throw new java.io.UncheckedIOException(failure); }
        System.out.println(result);
    }
    public static void started(MinecraftServer server) {
            try {
                com.cookiecraftmods.eqlib.placement.PrecisePlacement.clientDataRemoved = entity -> {
                    if (entity instanceof PlacementOffsetAccess data && data.mdm$getPlacementOffset() != null) removals.incrementAndGet();
                };
                var level = server.overworld();
                var pos = new BlockPos(175, 200, 175);
                var block = com.cookiecraftmods.mdm.init.MdmModBlocks.ELECTRIC_GUITAR_BLACK.get();
                var state = block.defaultBlockState();
                level.getChunkAt(pos);
                level.setBlock(pos, state, 3);
                var entity = level.getBlockEntity(pos);
                ((PlacementOffsetAccess)entity).mdm$setPlacementOffset(new Vec3(0.5, 0.5, 0.5));
                entity.setChanged();
                FurnitureFootprint.placeParts(level, pos, state);
                System.out.println("PLACEMENT_UNLOAD_SHUTDOWN_BEGIN");
                server.halt(false);
            } catch (Throwable failure) {
                failure.printStackTrace();
                Runtime.getRuntime().halt(1);
            }
    }
}
