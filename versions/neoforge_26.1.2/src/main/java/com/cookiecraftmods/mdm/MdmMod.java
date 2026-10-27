package com.cookiecraftmods.mdm;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.eqlib.block.SeatBlock;
import com.cookiecraftmods.mdm.block.LightSwitch;
import com.cookiecraftmods.mdm.block.SingleBedBlock;
import com.cookiecraftmods.mdm.registry.ModRegistryEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(MdmMod.MODID)
public final class MdmMod {
    public static final String MODID = "mdm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public MdmMod(IEventBus modEventBus) {
		NeoForge.EVENT_BUS.register(this);
        var sounds = net.neoforged.neoforge.registries.DeferredRegister.create(net.minecraft.core.registries.Registries.SOUND_EVENT, MODID);
        sounds.register("light_on", () -> LightSwitch.ON);
        sounds.register("light_off", () -> LightSwitch.OFF);
        sounds.register(modEventBus);
        MdmModBlocks.init();
        MdmModItems.init();
        MdmModBlockEntities.init();
        MdmModTabs.init();
        ModRegistryEntry.registerAll(modEventBus);
        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Modern Decorations {} initialized for NeoForge", "3.1");
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
        if (event.getEntity().isShiftKeyDown() && event.getEntity().getItemInHand(event.getHand()).getItem() instanceof net.minecraft.world.item.BlockItem held && com.cookiecraftmods.eqlib.placement.PrecisePlacement.supported(held.getBlock())) return;
		var state = event.getLevel().getBlockState(event.getPos());
        var bedResult = SingleBedBlock.tryUse(state, event.getLevel(), event.getPos(), event.getEntity());
        if (bedResult.consumesAction()) {
            event.setCancellationResult(bedResult);
            event.setCanceled(true);
            return;
        }
        var result = LightSwitch.use(state, event.getLevel(), event.getPos(), event.getEntity());
        if (result.consumesAction()) {
            event.setCancellationResult(result);
            event.setCanceled(true);
            return;
        }
		if (SeatBlock.use(state, event.getLevel(), event.getPos(), event.getEntity()).consumesAction()) {
			event.setCanceled(true);
		}
	}


}
