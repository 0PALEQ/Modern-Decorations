package com.cookiecraftmods.mdm.client.clock;

import com.cookiecraftmods.eqlib.core.clock.ClockTime;
import com.cookiecraftmods.eqlib.core.clock.ClockHandMesh;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import com.cookiecraftmods.mdm.block.HorizontalFurnitureBlock;
import com.cookiecraftmods.mdm.block.WoodenWallClockBlock;
import com.cookiecraftmods.mdm.block.entity.WallClockBlockEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public final class WallClockRenderer extends TileEntityRenderer<WallClockBlockEntity> {
    private final ClockHandMesh[] hands = ClockPlatform.loadHands();

    public WallClockRenderer(TileEntityRendererDispatcher context) { super(context); }

    @Override
    public void render(WallClockBlockEntity clock, float partialTick, MatrixStack pose,
                       IRenderTypeBuffer buffers, int light, int overlay) {
        if (clock.getLevel() == null) return;
        boolean wooden = clock.getBlockState().getBlock() instanceof WoodenWallClockBlock;
        long time = ClockPlatform.time(clock);
        pose.pushPose();
        var placementOffset = com.cookiecraftmods.eqlib.placement.PrecisePlacement.offset(clock.getLevel(), clock.getBlockPos());
        pose.translate(placementOffset.x, placementOffset.y, placementOffset.z);
        pose.translate(0.5, 0.5, 0.5);
        pose.mulPose(Vector3f.YP.rotationDegrees(-clock.getBlockState()
                .getValue(HorizontalFurnitureBlock.FACING).toYRot() + 180));
        pose.translate(-0.5, -0.5, -0.5);
        for (int i = 0; i < 2; i++) {
            ClockHandMesh hand = hands[(wooden ? 2 : 0) + i];
            pose.pushPose();
            pose.translate(0.5, 0.5, hand.pivotZ() - i * 0.001);
            pose.mulPose(Vector3f.ZP.rotationDegrees(hand.uprightRotation()
                    + (i == 0 ? ClockTime.hourAngle(time) : ClockTime.minuteAngle(time))));
            ClockPlatform.draw(hand, pose.last(), buffers.getBuffer(ClockPlatform.layer(wooden)), light);
            pose.popPose();
        }
        pose.popPose();
    }
}

