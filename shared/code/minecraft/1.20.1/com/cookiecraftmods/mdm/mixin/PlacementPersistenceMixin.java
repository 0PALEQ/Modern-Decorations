package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.mdm.placement.PlacementOffsetAccess;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.nbt.CompoundTag;

@Mixin(BlockEntity.class)
public abstract class PlacementPersistenceMixin {
    @Inject(method = "load", at = @At("RETURN"))
    private void mdm$read(CompoundTag tag, CallbackInfo callback) {
        var data = (PlacementOffsetAccess) this;
        data.mdm$setPlacementOffset(tag.getBoolean("mdm_precise") ? new Vec3(tag.getDouble("mdm_offset_x"), tag.getDouble("mdm_offset_y"), tag.getDouble("mdm_offset_z")) : null);
        if (data.mdm$getPlacementOffset() != null) PrecisePlacement.refresh((BlockEntity)(Object)this);
    }
    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void mdm$write(CompoundTag tag, CallbackInfo callback) {
        Vec3 offset = ((PlacementOffsetAccess) this).mdm$getPlacementOffset();
        if (offset == null) return;
        tag.putBoolean("mdm_precise", true);
        tag.putDouble("mdm_offset_x", offset.x);
        tag.putDouble("mdm_offset_y", offset.y);
        tag.putDouble("mdm_offset_z", offset.z);
    }
    @Inject(method = "getUpdateTag", at = @At("HEAD"), cancellable = true)
    private void mdm$syncTag(CallbackInfoReturnable<CompoundTag> callback) {
        if (((PlacementOffsetAccess) this).mdm$getPlacementOffset() != null) {
            callback.setReturnValue(((BlockEntity)(Object)this).saveWithoutMetadata());
        }
    }
    @Inject(method = "getUpdatePacket", at = @At("HEAD"), cancellable = true)
    private void mdm$syncPacket(CallbackInfoReturnable<ClientboundBlockEntityDataPacket> callback) {
        if (((PlacementOffsetAccess) this).mdm$getPlacementOffset() != null) {
            callback.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity)(Object)this));
        }
    }
}
