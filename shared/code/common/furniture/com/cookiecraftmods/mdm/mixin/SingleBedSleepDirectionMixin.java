package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayer.class)
public abstract class SingleBedSleepDirectionMixin {
    // Target the checks themselves: NeoForge wraps their callers in a lambda.
    @ModifyVariable(method = {"bedInRange", "bedBlocked"}, at = @At("HEAD"), argsOnly = true)
    private Direction mdm$orientMattressCheck(Direction direction, BlockPos pos, Direction originalDirection) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        var state = player.level().getBlockState(pos);
        return state.getBlock() instanceof SingleBedBlock ? SingleBedBlock.sleepDirection(state) : direction;
    }
}
