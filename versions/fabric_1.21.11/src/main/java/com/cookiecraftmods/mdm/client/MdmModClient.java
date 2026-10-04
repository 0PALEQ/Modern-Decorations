package com.cookiecraftmods.mdm.client;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModScreens;
import com.cookiecraftmods.mdm.registry.ModRegistryEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public final class MdmModClient implements ClientModInitializer {
    private static final Set<String> TRANSLUCENT_BLOCKS = Set.of(
            "black_curtain", "black_curtain_left", "black_curtain_right",
            "black_bench", "black_office_desk",
            "blue_curtain", "blue_curtain_left", "blue_curtain_right", "bowl",
            "coffee_table_04", "curtain_rack", "entryway_carpet",
            "green_curtain", "green_curtain_left", "green_curtain_right",
            "grey_curtain", "grey_curtain_left", "grey_curtain_right",
            "knife_stand", "potted_cactus",
            "leafless_bamboo_decoration_1", "leafless_bamboo_decoration_2", "leafless_bamboo_decoration_3",
            "mug", "plate",
            "red_curtain", "red_curtain_left", "red_curtain_right",
            "seasoning_rack", "shoji_screen",
            "toilet_paper_roll",
            "white_bench", "white_curtain", "white_curtain_left", "white_curtain_right"
    );

    @Override
    public void onInitializeClient() {
        MdmModScreens.register();

        for (ModRegistryEntry<Block> entry : MdmModBlocks.entries()) {
            ChunkSectionLayer layer = TRANSLUCENT_BLOCKS.contains(entry.getId().getPath())
                    ? ChunkSectionLayer.TRANSLUCENT
                    : ChunkSectionLayer.CUTOUT;
            BlockRenderLayerMap.putBlock(entry.get(), layer);
        }
    }
}
