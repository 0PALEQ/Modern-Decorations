package com.cookiecraftmods.mdm.init;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;

import com.cookiecraftmods.mdm.block.entity.*;
import com.cookiecraftmods.mdm.MdmMod;

public class MdmModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MdmMod.MODID);
	public static final RegistryObject<BlockEntityType<DecorationPlacementBlockEntity>> DECORATION_PLACEMENT = REGISTRY.register("decoration_placement", () -> BlockEntityType.Builder.of(DecorationPlacementBlockEntity::new, net.minecraft.core.registries.BuiltInRegistries.BLOCK.stream().filter(com.cookiecraftmods.eqlib.placement.PrecisePlacement::supported).toArray(Block[]::new)).build(null));
	public static final RegistryObject<BlockEntityType<WallClockBlockEntity>> BLACK_WALL_CLOCK = register("black_wall_clock", MdmModBlocks.BLACK_WALL_CLOCK, WallClockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<WallClockBlockEntity>> WOODEN_WALL_CLOCK = register("wooden_wall_clock", MdmModBlocks.WOODEN_WALL_CLOCK, WallClockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1CabinetBlockEntity>> SET_1_CABINET = register("set_1_cabinet", MdmModBlocks.SET_1_CABINET, Set1CabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1CabinetWithDrawersBlockEntity>> SET_1_CABINET_WITH_DRAWERS = register("set_1_cabinet_with_drawers", MdmModBlocks.SET_1_CABINET_WITH_DRAWERS, Set1CabinetWithDrawersBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1CornerCabinet1BlockEntity>> SET_1_CORNER_CABINET_1 = register("set_1_corner_cabinet_1", MdmModBlocks.SET_1_CORNER_CABINET_1, Set1CornerCabinet1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1CornerCabinet2BlockEntity>> SET_1_CORNER_CABINET_2 = register("set_1_corner_cabinet_2", MdmModBlocks.SET_1_CORNER_CABINET_2, Set1CornerCabinet2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1Island1BlockEntity>> SET_1_ISLAND_1 = register("set_1_island_1", MdmModBlocks.SET_1_ISLAND_1, Set1Island1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1Island2BlockEntity>> SET_1_ISLAND_2 = register("set_1_island_2", MdmModBlocks.SET_1_ISLAND_2, Set1Island2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1FridgeBlockEntity>> SET_1_FRIDGE = register("set_1_fridge", MdmModBlocks.SET_1_FRIDGE, Set1FridgeBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1Cabinet2BlockEntity>> SET_1_CABINET_2 = register("set_1_cabinet_2", MdmModBlocks.SET_1_CABINET_2, Set1Cabinet2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2Cabinet1BlockEntity>> SET_2_CABINET_1 = register("set_2_cabinet_1", MdmModBlocks.SET_2_CABINET_1, Set2Cabinet1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2Cabinet2BlockEntity>> SET_2_CABINET_2 = register("set_2_cabinet_2", MdmModBlocks.SET_2_CABINET_2, Set2Cabinet2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2Cabinet3BlockEntity>> SET_2_CABINET_3 = register("set_2_cabinet_3", MdmModBlocks.SET_2_CABINET_3, Set2Cabinet3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2CabinetWithDrawersBlockEntity>> SET_2_CABINET_WITH_DRAWERS = register("set_2_cabinet_with_drawers", MdmModBlocks.SET_2_CABINET_WITH_DRAWERS, Set2CabinetWithDrawersBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2HangingShelfBlockEntity>> SET_2_HANGING_SHELF = register("set_2_hanging_shelf", MdmModBlocks.SET_2_HANGING_SHELF, Set2HangingShelfBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set2CornerCounterBlockEntity>> SET_2_CORNER_COUNTER = register("set_2_corner_counter", MdmModBlocks.SET_2_CORNER_COUNTER, Set2CornerCounterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set3CoffeTableBlockEntity>> SET_3_COFFE_TABLE = register("set_3_coffe_table", MdmModBlocks.SET_3_COFFE_TABLE, Set3CoffeTableBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set3CoffeTableDecoratedBlockEntity>> SET_3_COFFE_TABLE_DECORATED = register("set_3_coffe_table_decorated", MdmModBlocks.SET_3_COFFE_TABLE_DECORATED, Set3CoffeTableDecoratedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set3tvStandBlockEntity>> SET_3TV_STAND = register("set_3tv_stand", MdmModBlocks.SET_3TV_STAND, Set3tvStandBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1TVStandBlockEntity>> SET_1_TV_STAND = register("set_1_tv_stand", MdmModBlocks.SET_1_TV_STAND, Set1TVStandBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1TVStandMiddleBlockEntity>> SET_1_TV_STAND_MIDDLE = register("set_1_tv_stand_middle", MdmModBlocks.SET_1_TV_STAND_MIDDLE, Set1TVStandMiddleBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1LivingroomCabinetBlockEntity>> SET_1_LIVINGROOM_CABINET = register("set_1_livingroom_cabinet", MdmModBlocks.SET_1_LIVINGROOM_CABINET, Set1LivingroomCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1LivingroomCabinet2BlockEntity>> SET_1_LIVINGROOM_CABINET_2 = register("set_1_livingroom_cabinet_2", MdmModBlocks.SET_1_LIVINGROOM_CABINET_2, Set1LivingroomCabinet2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1LivingroomHangingCabinetBlockEntity>> SET_1_LIVINGROOM_HANGING_CABINET = register("set_1_livingroom_hanging_cabinet", MdmModBlocks.SET_1_LIVINGROOM_HANGING_CABINET,
			Set1LivingroomHangingCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3CornerCounterBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER = register("kitchen_set_3_corner_counter", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER, KitchenSet3CornerCounterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3CornerCounterWithSinkBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK = register("kitchen_set_3_corner_counter_with_sink", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK,
			KitchenSet3CornerCounterWithSinkBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3CounterBlockEntity>> KITCHEN_SET_3_COUNTER = register("kitchen_set_3_counter", MdmModBlocks.KITCHEN_SET_3_COUNTER, KitchenSet3CounterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3Counter2BlockEntity>> KITCHEN_SET_3_COUNTER_2 = register("kitchen_set_3_counter_2", MdmModBlocks.KITCHEN_SET_3_COUNTER_2, KitchenSet3Counter2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3Counter3BlockEntity>> KITCHEN_SET_3_COUNTER_3 = register("kitchen_set_3_counter_3", MdmModBlocks.KITCHEN_SET_3_COUNTER_3, KitchenSet3Counter3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3FridgeBottomBlockEntity>> KITCHEN_SET_3_FRIDGE_BOTTOM = register("kitchen_set_3_fridge_bottom", MdmModBlocks.KITCHEN_SET_3_FRIDGE_BOTTOM, KitchenSet3FridgeBottomBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3FridgeTopBlockEntity>> KITCHEN_SET_3_FRIDGE_TOP = register("kitchen_set_3_fridge_top", MdmModBlocks.KITCHEN_SET_3_FRIDGE_TOP, KitchenSet3FridgeTopBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3HangingCabinetBlockEntity>> KITCHEN_SET_3_HANGING_CABINET = register("kitchen_set_3_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CABINET,
			KitchenSet3HangingCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3SmallCornerHangingCabinetBlockEntity>> KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET = register("kitchen_set_3_small_corner_hanging_cabinet",
			MdmModBlocks.KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET, KitchenSet3SmallCornerHangingCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3SmallHangingCabinetBlockEntity>> KITCHEN_SET_3_SMALL_HANGING_CABINET = register("kitchen_set_3_small_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_SMALL_HANGING_CABINET,
			KitchenSet3SmallHangingCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<KitchenSet3HangingCornerCabinetBlockEntity>> KITCHEN_SET_3_HANGING_CORNER_CABINET = register("kitchen_set_3_hanging_corner_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CORNER_CABINET,
			KitchenSet3HangingCornerCabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greydresserleftBlockEntity>> BEDROOM_SET_1GREYDRESSERLEFT = register("bedroom_set_1greydresserleft", MdmModBlocks.BEDROOM_SET_1GREYDRESSERLEFT,
			BedroomSet1greydresserleftBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greydressermidBlockEntity>> BEDROOM_SET_1GREYDRESSERMID = register("bedroom_set_1greydressermid", MdmModBlocks.BEDROOM_SET_1GREYDRESSERMID, BedroomSet1greydressermidBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greydresserrightBlockEntity>> BEDROOM_SET_1GREYDRESSERRIGHT = register("bedroom_set_1greydresserright", MdmModBlocks.BEDROOM_SET_1GREYDRESSERRIGHT,
			BedroomSet1greydresserrightBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greySingleBedBlockEntity>> BEDROOM_SET_1GREY_SINGLE_BED = register("bedroom_set_1grey_single_bed", MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED, BedroomSet1greySingleBedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greyDoubleBedBlockEntity>> BEDROOM_SET_1GREY_DOUBLE_BED = register("bedroom_set_1grey_double_bed", MdmModBlocks.BEDROOM_SET_1GREY_DOUBLE_BED, BedroomSet1greyDoubleBedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greyBunkBedBlockEntity>> BEDROOM_SET_1GREY_BUNK_BED = register("bedroom_set_1grey_bunk_bed", MdmModBlocks.BEDROOM_SET_1GREY_BUNK_BED, BedroomSet1greyBunkBedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greywardrobeBlockEntity>> BEDROOM_SET_1GREYWARDROBE = register("bedroom_set_1greywardrobe", MdmModBlocks.BEDROOM_SET_1GREYWARDROBE, BedroomSet1greywardrobeBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greyNightstand1BlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_1 = register("bedroom_set_1grey_nightstand_1", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_1,
			BedroomSet1greyNightstand1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1greyNightstand2BlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_2 = register("bedroom_set_1grey_nightstand_2", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_2,
			BedroomSet1greyNightstand2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengebunkbedBlockEntity>> BEDROOM_SET_1WENGEBUNKBED = register("bedroom_set_1wengebunkbed", MdmModBlocks.BEDROOM_SET_1WENGEBUNKBED, BedroomSet1wengebunkbedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengeDoubleBedBlockEntity>> BEDROOM_SET_1WENGE_DOUBLE_BED = register("bedroom_set_1wenge_double_bed", MdmModBlocks.BEDROOM_SET_1WENGE_DOUBLE_BED,
			BedroomSet1wengeDoubleBedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengeSingleBedBlockEntity>> BEDROOM_SET_1WENGE_SINGLE_BED = register("bedroom_set_1wenge_single_bed", MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED,
			BedroomSet1wengeSingleBedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengedressermidBlockEntity>> BEDROOM_SET_1WENGEDRESSERMID = register("bedroom_set_1wengedressermid", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERMID,
			BedroomSet1wengedressermidBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengedresserleftBlockEntity>> BEDROOM_SET_1WENGEDRESSERLEFT = register("bedroom_set_1wengedresserleft", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERLEFT,
			BedroomSet1wengedresserleftBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengedresserrightBlockEntity>> BEDROOM_SET_1WENGEDRESSERRIGHT = register("bedroom_set_1wengedresserright", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERRIGHT,
			BedroomSet1wengedresserrightBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengewardrobeBlockEntity>> BEDROOM_SET_1WENGEWARDROBE = register("bedroom_set_1wengewardrobe", MdmModBlocks.BEDROOM_SET_1WENGEWARDROBE, BedroomSet1wengewardrobeBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengenightstand1BlockEntity>> BEDROOM_SET_1WENGENIGHTSTAND_1 = register("bedroom_set_1wengenightstand_1", MdmModBlocks.BEDROOM_SET_1WENGENIGHTSTAND_1,
			BedroomSet1wengenightstand1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1wengenighstand2BlockEntity>> BEDROOM_SET_1WENGENIGHSTAND_2 = register("bedroom_set_1wengenighstand_2", MdmModBlocks.BEDROOM_SET_1WENGENIGHSTAND_2,
			BedroomSet1wengenighstand2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitebunkbedBlockEntity>> BEDROOM_SET_1WHITEBUNKBED = register("bedroom_set_1whitebunkbed", MdmModBlocks.BEDROOM_SET_1WHITEBUNKBED, BedroomSet1whitebunkbedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whiteDoublebedBlockEntity>> BEDROOM_SET_1WHITE_DOUBLEBED = register("bedroom_set_1white_doublebed", MdmModBlocks.BEDROOM_SET_1WHITE_DOUBLEBED,
			BedroomSet1whiteDoublebedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whiteSinglebedBlockEntity>> BEDROOM_SET_1WHITE_SINGLEBED = register("bedroom_set_1white_singlebed", MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED,
			BedroomSet1whiteSinglebedBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitedresserleftBlockEntity>> BEDROOM_SET_1WHITEDRESSERLEFT = register("bedroom_set_1whitedresserleft", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERLEFT,
			BedroomSet1whitedresserleftBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitedressermidBlockEntity>> BEDROOM_SET_1WHITEDRESSERMID = register("bedroom_set_1whitedressermid", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERMID,
			BedroomSet1whitedressermidBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitedresserrightBlockEntity>> BEDROOM_SET_1WHITEDRESSERRIGHT = register("bedroom_set_1whitedresserright", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERRIGHT,
			BedroomSet1whitedresserrightBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitewardrobeBlockEntity>> BEDROOM_SET_1WHITEWARDROBE = register("bedroom_set_1whitewardrobe", MdmModBlocks.BEDROOM_SET_1WHITEWARDROBE, BedroomSet1whitewardrobeBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whiteloveseatBlockEntity>> BEDROOM_SET_1WHITELOVESEAT = register("bedroom_set_1whiteloveseat", MdmModBlocks.BEDROOM_SET_1WHITELOVESEAT, BedroomSet1whiteloveseatBlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitenightstand1BlockEntity>> BEDROOM_SET_1WHITENIGHTSTAND_1 = register("bedroom_set_1whitenightstand_1", MdmModBlocks.BEDROOM_SET_1WHITENIGHTSTAND_1,
			BedroomSet1whitenightstand1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BedroomSet1whitenighstand2BlockEntity>> BEDROOM_SET_1WHITENIGHSTAND_2 = register("bedroom_set_1whitenighstand_2", MdmModBlocks.BEDROOM_SET_1WHITENIGHSTAND_2,
			BedroomSet1whitenighstand2BlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	public static final RegistryObject<BlockEntityType<BlackDeskExtension1BlockEntity>> BLACK_DESK_EXTENSION_1 = register("black_desk_extension_1", MdmModBlocks.BLACK_DESK_EXTENSION_1, BlackDeskExtension1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BlackDeskExtension2BlockEntity>> BLACK_DESK_EXTENSION_2 = register("black_desk_extension_2", MdmModBlocks.BLACK_DESK_EXTENSION_2, BlackDeskExtension2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<BlackDeskExtension3BlockEntity>> BLACK_DESK_EXTENSION_3 = register("black_desk_extension_3", MdmModBlocks.BLACK_DESK_EXTENSION_3, BlackDeskExtension3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeCabinet1BlockEntity>> OFFICE_CABINET_1 = register("office_cabinet_1", MdmModBlocks.OFFICE_CABINET_1, OfficeCabinet1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeCabinet2BlockEntity>> OFFICE_CABINET_2 = register("office_cabinet_2", MdmModBlocks.OFFICE_CABINET_2, OfficeCabinet2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeCabinet3BlockEntity>> OFFICE_CABINET_3 = register("office_cabinet_3", MdmModBlocks.OFFICE_CABINET_3, OfficeCabinet3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<Set1NightTableBlockEntity>> SET_1_NIGHT_TABLE = register("set_1_night_table", MdmModBlocks.SET_1_NIGHT_TABLE, Set1NightTableBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1BookshelfVar1BottomBlockEntity>> OFFICE_SET_1_BOOKSHELF_VAR_1_BOTTOM = register("office_set_1_bookshelf_var_1_bottom", MdmModBlocks.OFFICE_SET_1_BOOKSHELF_VAR_1_BOTTOM, OfficeSet1BookshelfVar1BottomBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1BookshelfVar1TopBlockEntity>> OFFICE_SET_1_BOOKSHELF_VAR_1_TOP = register("office_set_1_bookshelf_var_1_top", MdmModBlocks.OFFICE_SET_1_BOOKSHELF_VAR_1_TOP, OfficeSet1BookshelfVar1TopBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1ShelfVar1BottomBlockEntity>> OFFICE_SET_1_SHELF_VAR_1_BOTTOM = register("office_set_1_shelf_var_1_bottom", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_1_BOTTOM, OfficeSet1ShelfVar1BottomBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1ShelfVar1TopBlockEntity>> OFFICE_SET_1_SHELF_VAR_1_TOP = register("office_set_1_shelf_var_1_top", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_1_TOP, OfficeSet1ShelfVar1TopBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1ShelfVar2BottomBlockEntity>> OFFICE_SET_1_SHELF_VAR_2_BOTTOM = register("office_set_1_shelf_var_2_bottom", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_2_BOTTOM, OfficeSet1ShelfVar2BottomBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1ShelfVar2TopBlockEntity>> OFFICE_SET_1_SHELF_VAR_2_TOP = register("office_set_1_shelf_var_2_top", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_2_TOP, OfficeSet1ShelfVar2TopBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1CabinetBlockEntity>> OFFICE_SET_1_DESK_1_CABINET = register("office_set_1_desk_1_cabinet", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET, OfficeSet1Desk1CabinetBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1CabinetVar2BlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_2 = register("office_set_1_desk_1_cabinet_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_2, OfficeSet1Desk1CabinetVar2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1CabinetVar3BlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_3 = register("office_set_1_desk_1_cabinet_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_3, OfficeSet1Desk1CabinetVar3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1CabinetVar4BlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_4 = register("office_set_1_desk_1_cabinet_var_4", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_4, OfficeSet1Desk1CabinetVar4BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1LeftBlockEntity>> OFFICE_SET_1_DESK_1_LEFT = register("office_set_1_desk_1_left", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT, OfficeSet1Desk1LeftBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1LeftVar2BlockEntity>> OFFICE_SET_1_DESK_1_LEFT_VAR_2 = register("office_set_1_desk_1_left_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT_VAR_2, OfficeSet1Desk1LeftVar2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1LeftVar3BlockEntity>> OFFICE_SET_1_DESK_1_LEFT_VAR_3 = register("office_set_1_desk_1_left_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT_VAR_3, OfficeSet1Desk1LeftVar3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1MiddleBlockEntity>> OFFICE_SET_1_DESK_1_MIDDLE = register("office_set_1_desk_1_middle", MdmModBlocks.OFFICE_SET_1_DESK_1_MIDDLE, OfficeSet1Desk1MiddleBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1RightBlockEntity>> OFFICE_SET_1_DESK_1_RIGHT = register("office_set_1_desk_1_right", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT, OfficeSet1Desk1RightBlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1RightVar2BlockEntity>> OFFICE_SET_1_DESK_1_RIGHT_VAR_2 = register("office_set_1_desk_1_right_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT_VAR_2, OfficeSet1Desk1RightVar2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<OfficeSet1Desk1RightVar3BlockEntity>> OFFICE_SET_1_DESK_1_RIGHT_VAR_3 = register("office_set_1_desk_1_right_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT_VAR_3, OfficeSet1Desk1RightVar3BlockEntity::new);

	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, com.cookiecraftmods.mdm.block.BedVariants.validBlocks(registryname, block.get())).build(null));
	}
}
