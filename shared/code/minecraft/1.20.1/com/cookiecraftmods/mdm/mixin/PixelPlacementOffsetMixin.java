package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.mdm.block.PixelHorizontalFurnitureBlock;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class PixelPlacementOffsetMixin {
    @Shadow protected abstract BlockState asState();
    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    private void mdm$offset(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Vec3> callback) {
        BlockState state = asState();
        if (!PrecisePlacement.supported(state.getBlock())) return;
        Vec3 offset = PrecisePlacement.offset(level, pos);
        if (state.getBlock() instanceof PixelHorizontalFurnitureBlock) offset = offset.add(PixelHorizontalFurnitureBlock.modelOffset(state));
        callback.setReturnValue(offset);
    }
    @Inject(method = "hasOffsetFunction", at = @At("HEAD"), cancellable = true)
    private void mdm$hasOffset(CallbackInfoReturnable<Boolean> callback) {
        if (PrecisePlacement.supported(asState().getBlock())) callback.setReturnValue(true);
    }
}
