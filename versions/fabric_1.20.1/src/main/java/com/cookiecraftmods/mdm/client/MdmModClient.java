package com.cookiecraftmods.mdm.client;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModScreens;
import com.cookiecraftmods.mdm.registry.RegistryEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public final class MdmModClient implements ClientModInitializer {
    private static final Set<String> TRANSLUCENT_BLOCKS = Set.of(
            "black_bench", "black_curtain", "black_curtain_left", "black_curtain_right",
            "black_office_desk", "blue_curtain", "blue_curtain_left", "blue_curtain_right",
            "bowl", "coffee_table_04", "curtain_rack", "green_curtain", "green_curtain_left",
            "green_curtain_right", "grey_curtain", "grey_curtain_left", "grey_curtain_right",
            "leafless_bamboo_decoration_1", "leafless_bamboo_decoration_2",
            "leafless_bamboo_decoration_3", "mug", "plate", "potted_cactus", "red_curtain",
            "red_curtain_left", "red_curtain_right", "seasoning_rack", "toilet_paper_roll",
            "shoji_screen", "white_bench", "white_curtain", "white_curtain_left", "white_curtain_right"
    );

    @Override
    public void onInitializeClient() {
        MdmModScreens.initialize();

        for (RegistryEntry<? extends Block> entry : MdmModBlocks.REGISTRY.entries()) {
            RenderType layer = TRANSLUCENT_BLOCKS.contains(entry.getId().getPath())
                    ? RenderType.translucent()
                    : RenderType.cutout();
            BlockRenderLayerMap.INSTANCE.putBlock(entry.get(), layer);
        }
    }
}
