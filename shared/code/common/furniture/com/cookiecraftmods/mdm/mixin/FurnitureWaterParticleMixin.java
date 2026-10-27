package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import com.cookiecraftmods.mdm.block.RunningSinkBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.particle.DripParticle$FallAndLandParticle")
public abstract class FurnitureWaterParticleMixin extends Particle {
    @Shadow @Final protected ParticleOptions landParticle;
    @Unique private boolean mdm$furnitureWater;

    protected FurnitureWaterParticleMixin(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void mdm$identifyOutlet(CallbackInfo callback) {
        if (landParticle != ParticleTypes.SPLASH) {
            return;
        }
        BlockPos pos = BlockPos.containing(x, y, z);
        for (int dx = -1; dx <= 1 && !mdm$furnitureWater; dx++) {
            for (int dz = -1; dz <= 1 && !mdm$furnitureWater; dz++) {
                BlockPos candidate = pos.offset(dx, 0, dz);
                mdm$furnitureWater = mdm$isOutlet(candidate) || mdm$isOutlet(candidate.below());
            }
        }
        if (mdm$furnitureWater) {
            xd = 0;
            yd = -0.08;
            zd = 0;
        }
    }

    @Unique
    private boolean mdm$isOutlet(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        var offset = com.cookiecraftmods.eqlib.placement.PrecisePlacement.offset(level, pos);
        return state.getBlock() instanceof RunningSinkBlock sink
                && sink.isWaterOutlet(state, pos, x - offset.x, y - offset.y, z - offset.z);
    }

    @Inject(method = "postMoveUpdate", at = @At("HEAD"), cancellable = true)
    private void mdm$stopSplash(CallbackInfo callback) {
        if (mdm$furnitureWater && onGround) {
            remove();
            callback.cancel();
        }
    }
}
