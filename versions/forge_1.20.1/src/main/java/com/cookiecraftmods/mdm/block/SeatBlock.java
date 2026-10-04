package com.cookiecraftmods.mdm.block;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;

public final class SeatBlock {
	private static final String SEAT_TAG = "mdm:seat";
	private static final double DEFAULT_SEAT_HEIGHT = 7.0D / 16.0D;
	private static final double RIDER_HEIGHT_CORRECTION = 2.0D;
	private static final Map<ResourceLocation, Double> SEAT_HEIGHTS = new HashMap<>();
	private static final TagKey<Block> SITTABLE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MdmMod.MODID, "sittable"));

	private SeatBlock() {
	}

	public static void setSeatHeight(RegistryObject<Block> block, double seatHeight) {
		SEAT_HEIGHTS.put(block.getId(), seatHeight);
	}

	public static void setSeatHeightPixels(RegistryObject<Block> block, double seatHeightPixels) {
		setSeatHeight(block, seatHeightPixels / 16.0D);
	}

	public static InteractionResult use(BlockState state, Level level, BlockPos pos, Player player) {
		if (!state.is(SITTABLE_BLOCKS) || player.isShiftKeyDown() || player.isPassenger()) {
			return InteractionResult.PASS;
		}
		if (!level.isClientSide) {
			List<ArmorStand> seats = level.getEntitiesOfClass(ArmorStand.class, new AABB(pos), SeatBlock::isSeat);
			ArmorStand seat = seats.stream().filter(candidate -> candidate.getPassengers().isEmpty()).findFirst().orElse(null);
			if (seat == null && seats.isEmpty()) {
				seat = new ArmorStand(level, pos.getX() + 0.5D, getSeatY(state, pos), pos.getZ() + 0.5D);
				seat.setNoGravity(true);
				seat.setInvulnerable(true);
				seat.setInvisible(true);
				seat.addTag(SEAT_TAG);
				level.addFreshEntity(seat);
			}
			if (seat != null) {
				seat.setPos(pos.getX() + 0.5D, getSeatY(state, pos), pos.getZ() + 0.5D);
				if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
					seat.setYRot(state.getValue(HorizontalDirectionalBlock.FACING).toYRot());
				}
				player.startRiding(seat, true);
			}
		}
		return InteractionResult.SUCCESS;
	}

	private static double getSeatY(BlockState state, BlockPos pos) {
		return pos.getY() + SEAT_HEIGHTS.getOrDefault(BuiltInRegistries.BLOCK.getKey(state.getBlock()), DEFAULT_SEAT_HEIGHT) - RIDER_HEIGHT_CORRECTION;
	}

	private static boolean isSeat(ArmorStand entity) {
		return entity.getTags().contains(SEAT_TAG);
	}

	public static void removeSeats(Level level, BlockPos pos) {
		for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(pos), candidate -> candidate.getTags().contains(SEAT_TAG))) {
			entity.ejectPassengers();
			entity.discard();
		}
	}
}
