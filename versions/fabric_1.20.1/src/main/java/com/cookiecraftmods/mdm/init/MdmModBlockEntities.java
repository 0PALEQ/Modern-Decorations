package com.cookiecraftmods.mdm.init;

import com.cookiecraftmods.mdm.registry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import com.cookiecraftmods.mdm.registry.SimpleRegistry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;

import com.cookiecraftmods.mdm.block.entity.*;
import com.cookiecraftmods.mdm.MdmMod;

public class MdmModBlockEntities {
	public static final SimpleRegistry<BlockEntityType<?>> REGISTRY = SimpleRegistry.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MdmMod.MODID);
	public static final RegistryEntry<BlockEntityType<Set1CabinetBlockEntity>> SET_1_CABINET = register("set_1_cabinet", MdmModBlocks.SET_1_CABINET, Set1CabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1CabinetWithDrawersBlockEntity>> SET_1_CABINET_WITH_DRAWERS = register("set_1_cabinet_with_drawers", MdmModBlocks.SET_1_CABINET_WITH_DRAWERS, Set1CabinetWithDrawersBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1CornerCabinet1BlockEntity>> SET_1_CORNER_CABINET_1 = register("set_1_corner_cabinet_1", MdmModBlocks.SET_1_CORNER_CABINET_1, Set1CornerCabinet1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1CornerCabinet2BlockEntity>> SET_1_CORNER_CABINET_2 = register("set_1_corner_cabinet_2", MdmModBlocks.SET_1_CORNER_CABINET_2, Set1CornerCabinet2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1Island1BlockEntity>> SET_1_ISLAND_1 = register("set_1_island_1", MdmModBlocks.SET_1_ISLAND_1, Set1Island1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1Island2BlockEntity>> SET_1_ISLAND_2 = register("set_1_island_2", MdmModBlocks.SET_1_ISLAND_2, Set1Island2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1FridgeBlockEntity>> SET_1_FRIDGE = register("set_1_fridge", MdmModBlocks.SET_1_FRIDGE, Set1FridgeBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1Cabinet2BlockEntity>> SET_1_CABINET_2 = register("set_1_cabinet_2", MdmModBlocks.SET_1_CABINET_2, Set1Cabinet2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2Cabinet1BlockEntity>> SET_2_CABINET_1 = register("set_2_cabinet_1", MdmModBlocks.SET_2_CABINET_1, Set2Cabinet1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2Cabinet2BlockEntity>> SET_2_CABINET_2 = register("set_2_cabinet_2", MdmModBlocks.SET_2_CABINET_2, Set2Cabinet2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2Cabinet3BlockEntity>> SET_2_CABINET_3 = register("set_2_cabinet_3", MdmModBlocks.SET_2_CABINET_3, Set2Cabinet3BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2CabinetWithDrawersBlockEntity>> SET_2_CABINET_WITH_DRAWERS = register("set_2_cabinet_with_drawers", MdmModBlocks.SET_2_CABINET_WITH_DRAWERS, Set2CabinetWithDrawersBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2HangingShelfBlockEntity>> SET_2_HANGING_SHELF = register("set_2_hanging_shelf", MdmModBlocks.SET_2_HANGING_SHELF, Set2HangingShelfBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set2CornerCounterBlockEntity>> SET_2_CORNER_COUNTER = register("set_2_corner_counter", MdmModBlocks.SET_2_CORNER_COUNTER, Set2CornerCounterBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set3CoffeTableBlockEntity>> SET_3_COFFE_TABLE = register("set_3_coffe_table", MdmModBlocks.SET_3_COFFE_TABLE, Set3CoffeTableBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set3CoffeTableDecoratedBlockEntity>> SET_3_COFFE_TABLE_DECORATED = register("set_3_coffe_table_decorated", MdmModBlocks.SET_3_COFFE_TABLE_DECORATED, Set3CoffeTableDecoratedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set3tvStandBlockEntity>> SET_3TV_STAND = register("set_3tv_stand", MdmModBlocks.SET_3TV_STAND, Set3tvStandBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1TVStandBlockEntity>> SET_1_TV_STAND = register("set_1_tv_stand", MdmModBlocks.SET_1_TV_STAND, Set1TVStandBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1TVStandMiddleBlockEntity>> SET_1_TV_STAND_MIDDLE = register("set_1_tv_stand_middle", MdmModBlocks.SET_1_TV_STAND_MIDDLE, Set1TVStandMiddleBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1LivingroomCabinetBlockEntity>> SET_1_LIVINGROOM_CABINET = register("set_1_livingroom_cabinet", MdmModBlocks.SET_1_LIVINGROOM_CABINET, Set1LivingroomCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1LivingroomCabinet2BlockEntity>> SET_1_LIVINGROOM_CABINET_2 = register("set_1_livingroom_cabinet_2", MdmModBlocks.SET_1_LIVINGROOM_CABINET_2, Set1LivingroomCabinet2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<Set1LivingroomHangingCabinetBlockEntity>> SET_1_LIVINGROOM_HANGING_CABINET = register("set_1_livingroom_hanging_cabinet", MdmModBlocks.SET_1_LIVINGROOM_HANGING_CABINET,
			Set1LivingroomHangingCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3CornerCounterBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER = register("kitchen_set_3_corner_counter", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER, KitchenSet3CornerCounterBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3CornerCounterWithSinkBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK = register("kitchen_set_3_corner_counter_with_sink", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK,
			KitchenSet3CornerCounterWithSinkBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3CounterBlockEntity>> KITCHEN_SET_3_COUNTER = register("kitchen_set_3_counter", MdmModBlocks.KITCHEN_SET_3_COUNTER, KitchenSet3CounterBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3Counter2BlockEntity>> KITCHEN_SET_3_COUNTER_2 = register("kitchen_set_3_counter_2", MdmModBlocks.KITCHEN_SET_3_COUNTER_2, KitchenSet3Counter2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3Counter3BlockEntity>> KITCHEN_SET_3_COUNTER_3 = register("kitchen_set_3_counter_3", MdmModBlocks.KITCHEN_SET_3_COUNTER_3, KitchenSet3Counter3BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3FridgeBottomBlockEntity>> KITCHEN_SET_3_FRIDGE_BOTTOM = register("kitchen_set_3_fridge_bottom", MdmModBlocks.KITCHEN_SET_3_FRIDGE_BOTTOM, KitchenSet3FridgeBottomBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3FridgeTopBlockEntity>> KITCHEN_SET_3_FRIDGE_TOP = register("kitchen_set_3_fridge_top", MdmModBlocks.KITCHEN_SET_3_FRIDGE_TOP, KitchenSet3FridgeTopBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3HangingCabinetBlockEntity>> KITCHEN_SET_3_HANGING_CABINET = register("kitchen_set_3_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CABINET,
			KitchenSet3HangingCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3SmallCornerHangingCabinetBlockEntity>> KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET = register("kitchen_set_3_small_corner_hanging_cabinet",
			MdmModBlocks.KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET, KitchenSet3SmallCornerHangingCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3SmallHangingCabinetBlockEntity>> KITCHEN_SET_3_SMALL_HANGING_CABINET = register("kitchen_set_3_small_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_SMALL_HANGING_CABINET,
			KitchenSet3SmallHangingCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<KitchenSet3HangingCornerCabinetBlockEntity>> KITCHEN_SET_3_HANGING_CORNER_CABINET = register("kitchen_set_3_hanging_corner_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CORNER_CABINET,
			KitchenSet3HangingCornerCabinetBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greydresserleftBlockEntity>> BEDROOM_SET_1GREYDRESSERLEFT = register("bedroom_set_1greydresserleft", MdmModBlocks.BEDROOM_SET_1GREYDRESSERLEFT,
			BedroomSet1greydresserleftBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greydressermidBlockEntity>> BEDROOM_SET_1GREYDRESSERMID = register("bedroom_set_1greydressermid", MdmModBlocks.BEDROOM_SET_1GREYDRESSERMID, BedroomSet1greydressermidBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greydresserrightBlockEntity>> BEDROOM_SET_1GREYDRESSERRIGHT = register("bedroom_set_1greydresserright", MdmModBlocks.BEDROOM_SET_1GREYDRESSERRIGHT,
			BedroomSet1greydresserrightBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greySingleBedBlockEntity>> BEDROOM_SET_1GREY_SINGLE_BED = register("bedroom_set_1grey_single_bed", MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED, BedroomSet1greySingleBedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greyDoubleBedBlockEntity>> BEDROOM_SET_1GREY_DOUBLE_BED = register("bedroom_set_1grey_double_bed", MdmModBlocks.BEDROOM_SET_1GREY_DOUBLE_BED, BedroomSet1greyDoubleBedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greyBunkBedBlockEntity>> BEDROOM_SET_1GREY_BUNK_BED = register("bedroom_set_1grey_bunk_bed", MdmModBlocks.BEDROOM_SET_1GREY_BUNK_BED, BedroomSet1greyBunkBedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greywardrobeBlockEntity>> BEDROOM_SET_1GREYWARDROBE = register("bedroom_set_1greywardrobe", MdmModBlocks.BEDROOM_SET_1GREYWARDROBE, BedroomSet1greywardrobeBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greyNightstand1BlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_1 = register("bedroom_set_1grey_nightstand_1", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_1,
			BedroomSet1greyNightstand1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1greyNightstand2BlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_2 = register("bedroom_set_1grey_nightstand_2", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_2,
			BedroomSet1greyNightstand2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengebunkbedBlockEntity>> BEDROOM_SET_1WENGEBUNKBED = register("bedroom_set_1wengebunkbed", MdmModBlocks.BEDROOM_SET_1WENGEBUNKBED, BedroomSet1wengebunkbedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengeDoubleBedBlockEntity>> BEDROOM_SET_1WENGE_DOUBLE_BED = register("bedroom_set_1wenge_double_bed", MdmModBlocks.BEDROOM_SET_1WENGE_DOUBLE_BED,
			BedroomSet1wengeDoubleBedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengeSingleBedBlockEntity>> BEDROOM_SET_1WENGE_SINGLE_BED = register("bedroom_set_1wenge_single_bed", MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED,
			BedroomSet1wengeSingleBedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengedressermidBlockEntity>> BEDROOM_SET_1WENGEDRESSERMID = register("bedroom_set_1wengedressermid", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERMID,
			BedroomSet1wengedressermidBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengedresserleftBlockEntity>> BEDROOM_SET_1WENGEDRESSERLEFT = register("bedroom_set_1wengedresserleft", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERLEFT,
			BedroomSet1wengedresserleftBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengedresserrightBlockEntity>> BEDROOM_SET_1WENGEDRESSERRIGHT = register("bedroom_set_1wengedresserright", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERRIGHT,
			BedroomSet1wengedresserrightBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengewardrobeBlockEntity>> BEDROOM_SET_1WENGEWARDROBE = register("bedroom_set_1wengewardrobe", MdmModBlocks.BEDROOM_SET_1WENGEWARDROBE, BedroomSet1wengewardrobeBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengenightstand1BlockEntity>> BEDROOM_SET_1WENGENIGHTSTAND_1 = register("bedroom_set_1wengenightstand_1", MdmModBlocks.BEDROOM_SET_1WENGENIGHTSTAND_1,
			BedroomSet1wengenightstand1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1wengenighstand2BlockEntity>> BEDROOM_SET_1WENGENIGHSTAND_2 = register("bedroom_set_1wengenighstand_2", MdmModBlocks.BEDROOM_SET_1WENGENIGHSTAND_2,
			BedroomSet1wengenighstand2BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitebunkbedBlockEntity>> BEDROOM_SET_1WHITEBUNKBED = register("bedroom_set_1whitebunkbed", MdmModBlocks.BEDROOM_SET_1WHITEBUNKBED, BedroomSet1whitebunkbedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whiteDoublebedBlockEntity>> BEDROOM_SET_1WHITE_DOUBLEBED = register("bedroom_set_1white_doublebed", MdmModBlocks.BEDROOM_SET_1WHITE_DOUBLEBED,
			BedroomSet1whiteDoublebedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whiteSinglebedBlockEntity>> BEDROOM_SET_1WHITE_SINGLEBED = register("bedroom_set_1white_singlebed", MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED,
			BedroomSet1whiteSinglebedBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitedresserleftBlockEntity>> BEDROOM_SET_1WHITEDRESSERLEFT = register("bedroom_set_1whitedresserleft", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERLEFT,
			BedroomSet1whitedresserleftBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitedressermidBlockEntity>> BEDROOM_SET_1WHITEDRESSERMID = register("bedroom_set_1whitedressermid", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERMID,
			BedroomSet1whitedressermidBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitedresserrightBlockEntity>> BEDROOM_SET_1WHITEDRESSERRIGHT = register("bedroom_set_1whitedresserright", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERRIGHT,
			BedroomSet1whitedresserrightBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitewardrobeBlockEntity>> BEDROOM_SET_1WHITEWARDROBE = register("bedroom_set_1whitewardrobe", MdmModBlocks.BEDROOM_SET_1WHITEWARDROBE, BedroomSet1whitewardrobeBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whiteloveseatBlockEntity>> BEDROOM_SET_1WHITELOVESEAT = register("bedroom_set_1whiteloveseat", MdmModBlocks.BEDROOM_SET_1WHITELOVESEAT, BedroomSet1whiteloveseatBlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitenightstand1BlockEntity>> BEDROOM_SET_1WHITENIGHTSTAND_1 = register("bedroom_set_1whitenightstand_1", MdmModBlocks.BEDROOM_SET_1WHITENIGHTSTAND_1,
			BedroomSet1whitenightstand1BlockEntity::new);
	public static final RegistryEntry<BlockEntityType<BedroomSet1whitenighstand2BlockEntity>> BEDROOM_SET_1WHITENIGHSTAND_2 = register("bedroom_set_1whitenighstand_2", MdmModBlocks.BEDROOM_SET_1WHITENIGHSTAND_2,
			BedroomSet1whitenighstand2BlockEntity::new);
	private static <T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String registryname, RegistryEntry<Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}