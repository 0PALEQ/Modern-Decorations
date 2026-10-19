package com.cookiecraftmods.mdm.block;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import net.minecraft.world.level.block.Block;

/** Supported storage-entity blocks, including every dyed single bed. */
public final class BedVariants {
    private BedVariants() {}

    public static Block[] validBlocks(String id, Block original) {
        return switch (id) {
            case "bedroom_set_1grey_single_bed" -> new Block[] {
                original,
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_ORANGE.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_MAGENTA.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_LIGHT_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_YELLOW.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_LIME.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_PINK.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_LIGHT_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_CYAN.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_PURPLE.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_BROWN.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_GREEN.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_RED.get(),
                MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED_BLACK.get()
            };
            case "bedroom_set_1wenge_single_bed" -> new Block[] {
                original,
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_ORANGE.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_MAGENTA.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_LIGHT_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_YELLOW.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_LIME.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_PINK.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_LIGHT_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_CYAN.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_PURPLE.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_BROWN.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_GREEN.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_RED.get(),
                MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED_BLACK.get()
            };
            case "bedroom_set_1white_singlebed" -> new Block[] {
                original,
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_ORANGE.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_MAGENTA.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_LIGHT_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_YELLOW.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_LIME.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_PINK.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_LIGHT_GRAY.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_CYAN.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_PURPLE.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_BLUE.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_BROWN.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_GREEN.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_RED.get(),
                MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED_BLACK.get()
            };
            default -> new Block[] {original};
        };
    }
}
