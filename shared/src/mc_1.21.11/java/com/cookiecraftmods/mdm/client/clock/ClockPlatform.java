package com.cookiecraftmods.mdm.client.clock;

import com.cookiecraftmods.mdm.block.entity.WallClockBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import java.io.IOException;

final class ClockPlatform {
    private ClockPlatform() {}

    static long time(WallClockBlockEntity clock) {
        return clock.getLevel().getDayTime();
    }

    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath("mdm", path); }

    static RenderType layer(boolean wooden) {
        return RenderTypes.entityCutout(id("textures/block/" + (wooden ? "ikea_wood" : "black_wall_clock") + ".png"));
    }

    static ClockHandMesh[] loadHands() {
        ClockHandMesh[] hands = new ClockHandMesh[4];
        String[] models = {"black_wall_clock", "wooden_wall_clock"};
        String[] names = {"pointer_hour", "pointer_minute"};
        for (int clock = 0; clock < 2; clock++) {
            for (int hand = 0; hand < 2; hand++) {
                try (var reader = Minecraft.getInstance().getResourceManager()
                        .getResourceOrThrow(id("models/custom/" + models[clock] + ".json")).openAsReader()) {
                    hands[clock * 2 + hand] = ClockHandMesh.read(reader, names[hand]);
                } catch (IOException exception) {
                    throw new IllegalStateException("Unable to load wall clock hands", exception);
                }
            }
        }
        return hands;
    }

    static void draw(ClockHandMesh hand, PoseStack.Pose pose, VertexConsumer vertices, int light) {
        for (ClockHandMesh.Vertex v : hand.vertices()) {
            vertices.addVertex(pose.pose(), v.x(), v.y(), v.z()).setColor(255, 255, 255, 255)
                    .setUv(v.u(), v.v()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light)
                    .setNormal(pose, v.nx(), v.ny(), v.nz());
        }
    }
}

