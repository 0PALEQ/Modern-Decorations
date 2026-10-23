package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Minecraft.class)
public abstract class PlacementClientMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void mdm$clientWorld(CallbackInfo callback) {
        com.cookiecraftmods.mdm.client.placement.PlacementClientData.install();
    }
    @Inject(method = "tick", at = @At("HEAD"))
    private void mdm$worldChanged(CallbackInfo callback) {
        com.cookiecraftmods.mdm.client.placement.PlacementClientData.tick();
    }
}

