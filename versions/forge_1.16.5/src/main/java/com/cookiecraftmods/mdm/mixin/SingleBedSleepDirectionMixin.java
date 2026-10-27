package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayerEntity.class)
public abstract class SingleBedSleepDirectionMixin {
    // Target the checks themselves: NeoForge wraps their callers in a lambda.
    @ModifyVariable(method = {"bedInRange", "bedBlocked"}, at = @At("HEAD"), argsOnly = true)
    private Direction mdm$orientMattressCheck(Direction direction, BlockPos pos, Direction originalDirection) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        var state = player.level.getBlockState(pos);
        return state.getBlock() instanceof SingleBedBlock ? SingleBedBlock.sleepDirection(state) : direction;
    }
}
