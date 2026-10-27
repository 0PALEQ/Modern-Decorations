package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/** 26.3 changed setPosToBed(BlockPos) to return success and added an overload. */
@Mixin(LivingEntity.class)
public abstract class PlacedBedPositionModernMixin {
    @Inject(method = "setPosToBed(Lnet/minecraft/core/BlockPos;)Z", at = @At("RETURN"), remap = false)
    private void mdm$placedBedPosition(BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        if (!callback.getReturnValue()) return;
        LivingEntity sleeper = (LivingEntity)(Object)this;
        var offset = com.cookiecraftmods.eqlib.placement.PrecisePlacement.offset(sleeper.level(), pos);
        sleeper.setPos(sleeper.getX() + offset.x, sleeper.getY() + offset.y, sleeper.getZ() + offset.z);
    }
}
