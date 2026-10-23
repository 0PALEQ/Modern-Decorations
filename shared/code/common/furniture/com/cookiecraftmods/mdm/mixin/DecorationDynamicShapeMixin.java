package com.cookiecraftmods.mdm.mixin;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Entity-backed placement cannot use the shape cache shared by every occurrence of a block state.
@Mixin(BlockBehaviour.class)
public abstract class DecorationDynamicShapeMixin {
    @Shadow @Final @Mutable protected boolean dynamicShape;
    @Inject(method = "<init>", at = @At("RETURN"))
    private void mdm$dynamicPlacement(CallbackInfo callback) {
        if (getClass().getName().startsWith("com.cookiecraftmods.mdm.block.")) dynamicShape = true;
    }
}
