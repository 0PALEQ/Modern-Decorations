package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class DecorationPlacementItemMixin {
    @Shadow protected abstract boolean canPlace(BlockPlaceContext context, BlockState state);
    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void mdm$preciseState(BlockPlaceContext context, CallbackInfoReturnable<BlockState> callback) {
        if (!PrecisePlacement.requested(context)) return;
        var plan = PrecisePlacement.plan(context);
        if (plan.valid()) {
            PrecisePlacement.begin(plan);
            if (canPlace(context, plan.state())) {
                callback.setReturnValue(plan.state());
                return;
            }
        }
        PrecisePlacement.end();
        callback.setReturnValue(null);
    }

    @Inject(method = "placeBlock", at = @At("RETURN"))
    private void mdm$storeOffset(BlockPlaceContext context, BlockState state, CallbackInfoReturnable<Boolean> callback) {
        if (callback.getReturnValue()) PrecisePlacement.commit(context.getLevel(), context.getClickedPos());
    }

    @Inject(method = "place", at = @At("RETURN"))
    private void mdm$finish(CallbackInfoReturnable<net.minecraft.world.InteractionResult> callback) {
        PrecisePlacement.end();
    }
}
