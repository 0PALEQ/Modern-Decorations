package com.cookiecraftmods.mdm;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.server.TickTask;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

import it.unimi.dsi.fastutil.ints.IntObjectPair;
import it.unimi.dsi.fastutil.ints.IntObjectImmutablePair;

import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.eqlib.block.SeatBlock;
import com.cookiecraftmods.mdm.block.LightSwitch;
import com.cookiecraftmods.mdm.block.SingleBedBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod("mdm")
public class MdmMod {
	public static final Logger LOGGER = LogManager.getLogger(MdmMod.class);
	public static final String MODID = "mdm";

	public MdmMod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
        var sounds = net.neoforged.neoforge.registries.DeferredRegister.create(net.minecraft.core.registries.Registries.SOUND_EVENT, MODID);
        sounds.register("light_on", () -> LightSwitch.ON);
        sounds.register("light_off", () -> LightSwitch.OFF);
        sounds.register(modEventBus);
		modEventBus.addListener(this::registerNetworking);
		MdmModBlocks.REGISTRY.register(modEventBus);
		MdmModBlockEntities.REGISTRY.register(modEventBus);
		MdmModItems.REGISTRY.register(modEventBus);
		MdmModTabs.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

	private static final Queue<IntObjectPair<Runnable>> workToBeScheduled = new ConcurrentLinkedQueue<>();
	private static final PriorityQueue<TickTask> workQueue = new PriorityQueue<>(Comparator.comparingInt(TickTask::getTick));

	public static void queueServerWork(int delay, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workToBeScheduled.add(new IntObjectImmutablePair<>(delay, action));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		int currentTick = event.getServer().getTickCount();
		IntObjectPair<Runnable> work;
		while ((work = workToBeScheduled.poll()) != null) {
			workQueue.add(new TickTask(currentTick + work.leftInt(), work.right()));
		}
		while (!workQueue.isEmpty() && currentTick >= workQueue.peek().getTick()) {
			workQueue.poll().run();
		}
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
