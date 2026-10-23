package com.cookiecraftmods.mdm.placement;

import net.minecraft.world.phys.Vec3;

public interface PlacementOffsetAccess {
    Vec3 mdm$getPlacementOffset();
    void mdm$setPlacementOffset(Vec3 offset);
}
