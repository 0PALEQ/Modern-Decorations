package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ServerPlayer.class)
public abstract class SingleBedSleepDirectionMixin {
    @ModifyArgs(method = "startSleepInBed", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;bedInRange(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"))
    private void mdm$checkSingleBedRange(Args args) {
        mdm$orientMattressCheck(args);
    }

    @ModifyArgs(method = "startSleepInBed", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;bedBlocked(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"))
    private void mdm$checkSingleBedClearance(Args args) {
        mdm$orientMattressCheck(args);
    }

    @Unique
    private void mdm$orientMattressCheck(Args args) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        var state = player.level().getBlockState(args.get(0));
        if (state.getBlock() instanceof SingleBedBlock) {
            args.set(1, SingleBedBlock.sleepDirection(state));
        }
    }
}
