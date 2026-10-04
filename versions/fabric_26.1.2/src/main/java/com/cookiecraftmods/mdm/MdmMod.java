package com.cookiecraftmods.mdm;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModMenus;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.block.SeatBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MdmMod implements ModInitializer {
    public static final String MODID = "mdm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        MdmModBlocks.init();
        MdmModItems.init();
        MdmModBlockEntities.init();
        MdmModMenus.init();
        MdmModTabs.init();
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> SeatBlock.use(level.getBlockState(hit.getBlockPos()), level, hit.getBlockPos(), player));
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> SeatBlock.removeSeats(level, pos));

        LOGGER.info("Modern Decorations {} initialized for Fabric", "26.9");
    }
}
