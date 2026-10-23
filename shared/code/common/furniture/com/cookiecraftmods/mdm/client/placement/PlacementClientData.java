package com.cookiecraftmods.mdm.client.placement;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import java.util.concurrent.ConcurrentHashMap;

/** Immutable offsets are safe to query from chunk meshing threads, without reading live block entity maps. */
public final class PlacementClientData {
    private static final ConcurrentHashMap<BlockPos, Vec3> OFFSETS = new ConcurrentHashMap<>();
    private static volatile BlockGetter world;
    private PlacementClientData() {}
    public static void tick() {
        if (world != Minecraft.getInstance().level) {
            OFFSETS.clear();
            world = Minecraft.getInstance().level;
        }
    }
    public static void install() {
        PrecisePlacement.clientOffset = pos -> world == Minecraft.getInstance().level ? OFFSETS.getOrDefault(pos, Vec3.ZERO) : Vec3.ZERO;
        PrecisePlacement.clientDataChanged = (entity, offset) -> {
            if (entity.getLevel() == null || entity.getLevel() != Minecraft.getInstance().level) return;
            tick();
            if (offset == null) OFFSETS.remove(entity.getBlockPos());
            else OFFSETS.put(entity.getBlockPos(), offset);
        };
        PrecisePlacement.clientDataRemoved = entity -> {
            if (entity.getLevel() == world) OFFSETS.remove(entity.getBlockPos());
        };
    }
}
