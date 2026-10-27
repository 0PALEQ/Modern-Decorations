package com.cookiecraftmods.mdm.test;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.renderer.model.ModelResourceLocation;
@Mod.EventBusSubscriber(modid="mdm", value=Dist.CLIENT)
public final class BackportClientSmokeTest {
    private static boolean done;
    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (done || event.phase != TickEvent.Phase.END || !(mc.screen instanceof MainMenuScreen) || mc.getOverlay() != null) return;
        done = true;
        try {
            int count = 0;
            for (Block block : Registry.BLOCK) {
                if (!Registry.BLOCK.getKey(block).getNamespace().equals("mdm") || block instanceof com.cookiecraftmods.eqlib.block.FurniturePartBlock) continue;
                if (mc.getModelManager().getModel(new ModelResourceLocation(Registry.BLOCK.getKey(block), "inventory")) == mc.getModelManager().getMissingModel()) throw new AssertionError("Missing item model: " + block);
                count++;
            }
            Class<?> platform = Class.forName("com.cookiecraftmods.mdm.client.clock.ClockPlatform");
            java.lang.reflect.Method hands = platform.getDeclaredMethod("loadHands");
            hands.setAccessible(true);
            if (((Object[])hands.invoke(null)).length != 4) throw new AssertionError("Clock hands missing");
            String result = "BACKPORT_CLIENT_PASSED models=" + count;
            System.out.println(result);
            java.nio.file.Files.write(java.nio.file.Paths.get("client-smoke-result.txt"), result.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Throwable error) { error.printStackTrace(); }
        finally { mc.stop(); }
    }
}
