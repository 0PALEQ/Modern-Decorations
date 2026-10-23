package com.cookiecraftmods.mdm.mixin;

import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class DecorationPlacementShapeMixin {
    @Shadow protected abstract BlockState asState();

    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void mdm$selection(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callback) {
        if (PrecisePlacement.supported(asState().getBlock()) && PrecisePlacement.active(level, pos)) {
            callback.setReturnValue(FurnitureFootprint.localShape(PrecisePlacement.fullShape(asState(), level, pos, false), BlockPos.ZERO));
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void mdm$collision(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callback) {
        if (PrecisePlacement.supported(asState().getBlock()) && PrecisePlacement.active(level, pos)) {
            callback.setReturnValue(FurnitureFootprint.localShape(PrecisePlacement.fullShape(asState(), level, pos, true), BlockPos.ZERO));
        }
    }
}
