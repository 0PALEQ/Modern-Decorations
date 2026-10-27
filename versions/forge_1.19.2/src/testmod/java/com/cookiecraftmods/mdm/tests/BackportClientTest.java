package com.cookiecraftmods.mdm.tests;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.client.clock.WallClockRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.nio.file.*;

@Mod.EventBusSubscriber(modid="mdm_backport_test", value=Dist.CLIENT)
public final class BackportClientTest {
    private static boolean done;
    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
        var mc=Minecraft.getInstance();
        if (done || !(mc.screen instanceof TitleScreen) || event.phase!=TickEvent.Phase.END) return;
        done=true;
        try {
            Class.forName("net.minecraft.client.particle.DripParticle$FallAndLandParticle");
            new WallClockRenderer(null); // Load and parse all four clock hands.
            var models=mc.getModelManager();
            int count=0;
            for(var entry:MdmModBlocks.REGISTRY.getEntries()) {
                for(var state:entry.get().getStateDefinition().getPossibleStates()) {
                    if(mc.getBlockRenderer().getBlockModel(state)==models.getMissingModel())
                        throw new AssertionError("Missing block model: "+entry.getId()+" "+state);
                    count++;
                }
            }
            var items=net.minecraft.core.NonNullList.<net.minecraft.world.item.ItemStack>create();
            MdmModTabs.OFFICE.fillItemList(items);
            for(var stack:items) {
                if(mc.getItemRenderer().getModel(stack,null,null,0)==models.getMissingModel())
                    throw new AssertionError("Missing item model: "+stack);
            }
            String result="BACKPORT_CLIENT_PASSED: states="+count+", items="+items.size()+", clock hands loaded";
            Files.writeString(Path.of("backport-client-result.txt"),result);
            System.out.println(result);
            mc.stop();
        } catch(Throwable failure) { failure.printStackTrace(); Runtime.getRuntime().halt(1); }
    }
}
