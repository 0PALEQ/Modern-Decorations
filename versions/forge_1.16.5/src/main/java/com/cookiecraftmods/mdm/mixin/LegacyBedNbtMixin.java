package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.migration.LegacyBedMigration;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompressedStreamTools.class)
public abstract class LegacyBedNbtMixin {
    @Inject(method = "read(Ljava/io/DataInput;Lnet/minecraft/nbt/NBTSizeTracker;)Lnet/minecraft/nbt/CompoundNBT;",
            at = @At("RETURN"))
    private static void mdm$migrateLegacyBeds(CallbackInfoReturnable<CompoundNBT> callback) {
        LegacyBedMigration.migrate(callback.getReturnValue());
    }
}
