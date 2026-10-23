package com.cookiecraftmods.mdm.mixin;
import com.cookiecraftmods.mdm.client.placement.PlacementPreview;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.RenderType;
@Mixin(LevelRenderer.class)
public abstract class PlacementPreviewMixin {
    @Inject(method = "renderHitOutline", at = @At("RETURN"))
    private void mdm$preview(PoseStack pose, VertexConsumer lines, Entity entity, double cameraX, double cameraY,
                             double cameraZ, BlockPos target, BlockState targetState, CallbackInfo callback) {
        var plan = PlacementPreview.current();
        if (plan == null) return;
        Minecraft mc = Minecraft.getInstance();
        var buffers = mc.renderBuffers().bufferSource();
        pose.pushPose();
        pose.translate(plan.pos().getX() - cameraX, plan.pos().getY() - cameraY, plan.pos().getZ() - cameraZ);
        var vertices = buffers.getBuffer(RenderType.lines());
        plan.shape().forAllBoxes((x0, y0, z0, x1, y1, z1) -> LevelRenderer.renderLineBox(pose, vertices,
                x0, y0, z0, x1, y1, z1, plan.valid() ? 0.2f : 1f, plan.valid() ? 1f : 0.2f, 0.2f, 0.8f));
        pose.translate(plan.offset().x, plan.offset().y, plan.offset().z);
        mc.getBlockRenderer().renderSingleBlock(plan.state(), pose, buffers, 15728880, OverlayTexture.NO_OVERLAY);
        pose.popPose();
    }
}
