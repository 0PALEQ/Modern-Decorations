package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.migration.LegacyBedMigration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NbtIo.class)
public abstract class LegacyBedNbtMixin {
    @Inject(method = "read(Ljava/io/DataInput;Lnet/minecraft/nbt/NbtAccounter;)Lnet/minecraft/nbt/CompoundTag;",
            at = @At("RETURN"))
    private static void mdm$migrateLegacyBeds(CallbackInfoReturnable<CompoundTag> callback) {
        LegacyBedMigration.migrate(callback.getReturnValue());
    }
}
