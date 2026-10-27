package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(LivingEntity.class)
public abstract class PlacedBedPositionMixin {
    @Inject(method = "setPosToBed", at = @At("RETURN"))
    private void mdm$placedBedPosition(BlockPos pos, CallbackInfo callback) {
        LivingEntity sleeper = (LivingEntity)(Object)this;
        var offset = com.cookiecraftmods.eqlib.placement.PrecisePlacement.offset(sleeper.level, pos);
        sleeper.setPos(sleeper.getX() + offset.x, sleeper.getY() + offset.y, sleeper.getZ() + offset.z);
    }
}
