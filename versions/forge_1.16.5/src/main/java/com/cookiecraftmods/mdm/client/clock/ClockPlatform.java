package com.cookiecraftmods.mdm.client.clock;

import com.cookiecraftmods.eqlib.core.clock.ClockHandMesh;
import net.minecraft.entity.Pose;

import com.cookiecraftmods.mdm.block.entity.WallClockBlockEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import java.io.IOException;

final class ClockPlatform {
    private ClockPlatform() {}

    static long time(WallClockBlockEntity clock) {
        return clock.getLevel().getDayTime();
    }

    private static ResourceLocation id(String path) { return new ResourceLocation("mdm", path); }

    static RenderType layer(boolean wooden) {
        return RenderType.entityCutout(id("textures/block/" + (wooden ? "ikea_wood" : "black_wall_clock") + ".png"));
    }

    static ClockHandMesh[] loadHands() {
        ClockHandMesh[] hands = new ClockHandMesh[4];
        String[] models = {"black_wall_clock", "wooden_wall_clock"};
        String[] names = {"pointer_hour", "pointer_minute"};
        for (int clock = 0; clock < 2; clock++) {
            for (int hand = 0; hand < 2; hand++) {
                try (var stream = Minecraft.getInstance().getResourceManager()
                        .getResource(id("models/custom/" + models[clock] + ".json")).getInputStream()) {
                    hands[clock * 2 + hand] = ClockHandMesh.read(new java.io.InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8), names[hand]);
                } catch (IOException exception) {
                    throw new IllegalStateException("Unable to load wall clock hands", exception);
                }
            }
        }
        return hands;
    }

    static void draw(ClockHandMesh hand, MatrixStack.Entry pose, IVertexBuilder vertices, int light) {
        for (ClockHandMesh.Vertex v : hand.vertices()) {
            vertices.vertex(pose.pose(), v.x(), v.y(), v.z()).color(255, 255, 255, 255)
                    .uv(v.u(), v.v()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                    .normal(pose.normal(), v.nx(), v.ny(), v.nz()).endVertex();
        }
    }
}

