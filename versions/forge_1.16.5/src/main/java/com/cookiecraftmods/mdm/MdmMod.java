package com.cookiecraftmods.mdm;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;




import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.eqlib.block.SeatBlock;
import com.cookiecraftmods.mdm.block.LightSwitch;
import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod("mdm")
public class MdmMod {
	public static final Logger LOGGER = LogManager.getLogger(MdmMod.class);
	public static final String MODID = "mdm";

	public MdmMod() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        var sounds = net.minecraftforge.registries.DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.SOUND_EVENTS, MODID);
        sounds.register("light_on", () -> LightSwitch.ON);
        sounds.register("light_off", () -> LightSwitch.OFF);
        sounds.register(bus);
		MdmModBlocks.REGISTRY.register(bus);
		MdmModBlockEntities.REGISTRY.register(bus);
		MdmModItems.REGISTRY.register(bus);
		MdmModTabs.init();
		// Start of user code block mod init
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private static final Queue<java.util.Map.Entry<Integer, Runnable>> workToBeScheduled = new ConcurrentLinkedQueue<>();
	private static final PriorityQueue<TickDelayedTask> workQueue = new PriorityQueue<>(Comparator.comparingInt(TickDelayedTask::getTick));

	public static void queueServerWork(int delay, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workToBeScheduled.add(new java.util.AbstractMap.SimpleImmutableEntry<>(delay, action));
	}

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			int currentTick = net.minecraftforge.fml.server.ServerLifecycleHooks.getCurrentServer().getTickCount();
			java.util.Map.Entry<Integer, Runnable> work;
			while ((work = workToBeScheduled.poll()) != null) {
				workQueue.add(new TickDelayedTask(currentTick + work.getKey(), work.getValue()));
			}
			while (!workQueue.isEmpty() && currentTick >= workQueue.peek().getTick()) {
				workQueue.poll().run();
			}
		}
	}

	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getPlayer().isShiftKeyDown() && event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof net.minecraft.item.BlockItem held && com.cookiecraftmods.eqlib.placement.PrecisePlacement.supported(held.getBlock())) return;
		var state = event.getWorld().getBlockState(event.getPos());
        var bedResult = SingleBedBlock.tryUse(state, event.getWorld(), event.getPos(), event.getPlayer());
        if (bedResult.consumesAction()) {
            event.setCancellationResult(bedResult);
            event.setCanceled(true);
            return;
        }
        var result = LightSwitch.use(state, event.getWorld(), event.getPos(), event.getPlayer());
        if (result.consumesAction()) {
            event.setCancellationResult(result);
            event.setCanceled(true);
            return;
        }
		if (SeatBlock.use(state, event.getWorld(), event.getPos(), event.getPlayer()).consumesAction()) {
            // Mounting an armor stand does not align the rider with its rotation.
            if (event.getPlayer() instanceof net.minecraft.entity.player.ServerPlayerEntity && event.getPlayer().isPassenger() && state.hasProperty(net.minecraft.block.HorizontalBlock.FACING)) {
                net.minecraft.entity.player.ServerPlayerEntity rider = (net.minecraft.entity.player.ServerPlayerEntity) event.getPlayer();
                float yaw = state.getValue(net.minecraft.block.HorizontalBlock.FACING).toYRot();
                rider.connection.teleport(rider.getX(), rider.getY(), rider.getZ(), yaw, rider.xRot);
                rider.setYHeadRot(yaw);
                rider.setYBodyRot(yaw);
            }
			event.setCanceled(true);
		}
	}

}
