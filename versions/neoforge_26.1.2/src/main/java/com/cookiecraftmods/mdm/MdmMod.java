package com.cookiecraftmods.mdm;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModMenus;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.block.SeatBlock;
import com.cookiecraftmods.mdm.registry.ModRegistryEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(MdmMod.MODID)
public final class MdmMod {
    public static final String MODID = "mdm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public MdmMod(IEventBus modEventBus) {
		NeoForge.EVENT_BUS.register(this);
        MdmModBlocks.init();
        MdmModItems.init();
        MdmModBlockEntities.init();
        MdmModMenus.init();
        MdmModTabs.init();
        ModRegistryEntry.registerAll(modEventBus);
        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Modern Decorations {} initialized for NeoForge", "26.9");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            int repairedStates = 0;

            for (ModRegistryEntry<Block> entry : MdmModBlocks.entries()) {
                for (BlockState state : entry.get().getStateDefinition().getPossibleStates()) {
                    if (Block.BLOCK_STATE_REGISTRY.getId(state) == -1) {
                        Block.BLOCK_STATE_REGISTRY.add(state);
                        repairedStates++;
                    }
                }
            }

            if (repairedStates > 0) {
                LOGGER.warn("Added {} missing MDM block states to the global state registry", repairedStates);
            }
        });
    }

	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (SeatBlock.use(event.getLevel().getBlockState(event.getPos()), event.getLevel(), event.getPos(), event.getEntity()).consumesAction()) {
			event.setCanceled(true);
		}
	}

}
