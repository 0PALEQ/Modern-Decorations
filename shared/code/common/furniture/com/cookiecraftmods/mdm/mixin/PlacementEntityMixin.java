package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.placement.PlacementOffsetAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.block.FurniturePartBlock;
import net.minecraft.world.level.block.Block;

@Mixin(BlockEntity.class)
public abstract class PlacementEntityMixin implements PlacementOffsetAccess {
    @Unique private Vec3 mdm$placementOffset;
    @Override public Vec3 mdm$getPlacementOffset() { return mdm$placementOffset; }
    @Override public void mdm$setPlacementOffset(Vec3 offset) { mdm$placementOffset = offset; PrecisePlacement.clientDataChanged.accept((BlockEntity)(Object)this, offset); }

    @Inject(method = "setLevel", at = @At("RETURN"))
    private void mdm$clientDataReady(CallbackInfo callback) {
        PrecisePlacement.clientDataChanged.accept((BlockEntity)(Object)this, mdm$placementOffset);
    }

    @Inject(method = "setRemoved", at = @At("HEAD"))
    private void mdm$removeFootprint(CallbackInfo callback) {
        BlockEntity entity = (BlockEntity)(Object)this;
        PrecisePlacement.clientDataRemoved.accept(entity);
        var level = entity.getLevel();
        var pos = entity.getBlockPos();
        if (mdm$placementOffset == null || level == null || level.getBlockState(pos).is(entity.getBlockState().getBlock())) return;
        var offset = mdm$placementOffset;
        var full = PrecisePlacement.rawShape(entity.getBlockState(), level, pos, false).move(offset.x, offset.y, offset.z);
        for (var delta : FurnitureFootprint.offsets(full)) {
            var cell = pos.offset(delta);
            var state = level.getBlockState(cell);
            if (state.getBlock() instanceof FurniturePartBlock && FurniturePartBlock.parentPos(state, cell).equals(pos)) {
                level.setBlock(cell, state.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
            }
        }
    }
}
