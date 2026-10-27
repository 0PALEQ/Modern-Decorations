package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class SingleBedOrientationMixin {
    @Inject(method = "getBedOrientation", at = @At("HEAD"), cancellable = true)
    private void mdm$faceSingleBedPillow(CallbackInfoReturnable<Direction> callback) {
        LivingEntity sleeper = (LivingEntity) (Object) this;
        sleeper.getSleepingPos().ifPresent(pos -> {
            var state = sleeper.level().getBlockState(pos);
            if (state.getBlock() instanceof SingleBedBlock) {
                callback.setReturnValue(SingleBedBlock.sleepDirection(state));
            }
        });
    }
}
