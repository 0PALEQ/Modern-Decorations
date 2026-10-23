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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.DisplayRenderer;
@Mixin(LevelRenderer.class)
public abstract class PlacementPreviewMixin {
    @Inject(method = "submitBlockEntities", at = @At("RETURN"))
    private void mdm$preview(PoseStack pose, LevelRenderState state, SubmitNodeCollector nodes, CallbackInfo callback) {
        var plan = PlacementPreview.current();
        if (plan == null) return;
        var camera = state.cameraRenderState.pos;
        pose.pushPose();
        pose.translate(plan.pos().getX() - camera.x, plan.pos().getY() - camera.y, plan.pos().getZ() - camera.z);
        nodes.submitShapeOutline(pose, plan.shape(), RenderTypes.lines(), plan.valid() ? 0xff33ff33 : 0xffff3333, 2f, false);
        pose.translate(plan.offset().x, plan.offset().y, plan.offset().z);
        BlockModelRenderState model = new BlockModelRenderState();
        Minecraft.getInstance().getBlockModelResolver().update(model, plan.state(), DisplayRenderer.BLOCK_DISPLAY_CONTEXT);
        model.submit(pose, nodes, 15728880, OverlayTexture.NO_OVERLAY, 0);
        pose.popPose();
    }
}
