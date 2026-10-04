package com.cookiecraftmods.mdm.client;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModScreens;
import com.cookiecraftmods.mdm.registry.ModRegistryEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public final class MdmModClient implements ClientModInitializer {
    private static final Set<String> TRANSLUCENT_BLOCKS = Set.of(
            "black_curtain", "black_curtain_left", "black_curtain_right",
            "blue_curtain", "blue_curtain_left", "blue_curtain_right",
            "coffee_table_04", "entryway_carpet",
            "green_curtain", "green_curtain_left", "green_curtain_right",
            "grey_curtain", "grey_curtain_left", "grey_curtain_right",
            "knife_stand", "potted_cactus",
            "red_curtain", "red_curtain_left", "red_curtain_right",
            "seasoning_rack", "shoji_screen",
            "white_curtain", "white_curtain_left", "white_curtain_right"
    );

    @Override
    public void onInitializeClient() {
        MdmModScreens.register();

        for (ModRegistryEntry<Block> entry : MdmModBlocks.entries()) {
            RenderType layer = TRANSLUCENT_BLOCKS.contains(entry.getId().getPath())
                    ? RenderType.translucent()
                    : RenderType.cutout();
            BlockRenderLayerMap.INSTANCE.putBlock(entry.get(), layer);
        }
    }
}
