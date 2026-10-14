package com.cookiecraftmods.mdm;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.block.SeatBlock;
import com.cookiecraftmods.mdm.block.LightSwitch;
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
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var level : server.getAllLevels()) {
                SeatBlock.removeEmptySeats(level);
            }
        });
        LightSwitch.registerSounds();
        MdmModBlocks.init();
        MdmModItems.init();
        MdmModBlockEntities.init();
        MdmModTabs.init();
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
            var state = level.getBlockState(hit.getBlockPos());
            var result = LightSwitch.use(state, level, hit.getBlockPos(), player);
            return result.consumesAction() ? result : SeatBlock.use(state, level, hit.getBlockPos(), player);
        });
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> SeatBlock.removeSeats(level, pos));

        LOGGER.info("Modern Decorations initialized for Fabric on Minecraft 26.3");
    }
}
