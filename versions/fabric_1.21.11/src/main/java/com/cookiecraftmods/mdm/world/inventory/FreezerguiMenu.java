package com.cookiecraftmods.mdm.world.inventory;


import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.cookiecraftmods.mdm.init.MdmModMenus;

public class FreezerguiMenu extends AbstractContainerMenu implements MdmModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 19)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private Container internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private BlockEntity boundBlockEntity = null;

	public FreezerguiMenu(int id, Inventory inv) {
		this(id, inv, null);
	}

	public FreezerguiMenu(int id, Inventory inv, BlockPos pos) {
		super(MdmModMenus.FREEZERGUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new SimpleContainer(18);
		if (pos != null) {
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			this.access = ContainerLevelAccess.create(world, pos);
			this.boundBlockEntity = world.getBlockEntity(pos);
			if (this.boundBlockEntity instanceof Container container) {
				this.internal = container;
				this.bound = true;
			}
		}
		this.customSlots.put(0, this.addSlot(new Slot(internal, 0, 39, 29) {
			private final int slot = 0;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(1, this.addSlot(new Slot(internal, 1, 57, 29) {
			private final int slot = 1;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(2, this.addSlot(new Slot(internal, 2, 75, 29) {
			private final int slot = 2;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(3, this.addSlot(new Slot(internal, 3, 93, 29) {
			private final int slot = 3;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(4, this.addSlot(new Slot(internal, 4, 111, 29) {
			private final int slot = 4;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(5, this.addSlot(new Slot(internal, 5, 129, 29) {
			private final int slot = 5;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(6, this.addSlot(new Slot(internal, 6, 39, 47) {
			private final int slot = 6;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(7, this.addSlot(new Slot(internal, 7, 57, 47) {
			private final int slot = 7;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(8, this.addSlot(new Slot(internal, 8, 75, 47) {
			private final int slot = 8;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(9, this.addSlot(new Slot(internal, 9, 93, 47) {
			private final int slot = 9;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(10, this.addSlot(new Slot(internal, 10, 111, 47) {
			private final int slot = 10;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(11, this.addSlot(new Slot(internal, 11, 129, 47) {
			private final int slot = 11;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(12, this.addSlot(new Slot(internal, 12, 39, 65) {
			private final int slot = 12;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(13, this.addSlot(new Slot(internal, 13, 57, 65) {
			private final int slot = 13;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(14, this.addSlot(new Slot(internal, 14, 75, 65) {
			private final int slot = 14;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(15, this.addSlot(new Slot(internal, 15, 93, 65) {
			private final int slot = 15;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(16, this.addSlot(new Slot(internal, 16, 111, 65) {
			private final int slot = 16;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		this.customSlots.put(17, this.addSlot(new Slot(internal, 17, 129, 65) {
			private final int slot = 17;
			private int x = FreezerguiMenu.this.x;
			private int y = FreezerguiMenu.this.y;
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 5 + 8 + sj * 18, 12 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 5 + 8 + si * 18, 12 + 142));
	}

	@Override
	public boolean stillValid(Player player) {
		return this.boundBlockEntity == null || AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 18) {
				if (!this.moveItemStackTo(itemstack1, 18, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 18, false)) {
				if (index < 18 + 27) {
					if (!this.moveItemStackTo(itemstack1, 18 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 18, 18 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
		boolean flag = false;
		int i = p_38905_;
		if (p_38907_) {
			i = p_38906_ - 1;
		}
		if (p_38904_.isStackable()) {
			while (!p_38904_.isEmpty() && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
				Slot slot = this.slots.get(i);
				ItemStack itemstack = slot.getItem();
				if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameComponents(p_38904_, itemstack)) {
					int j = itemstack.getCount() + p_38904_.getCount();
					int k = slot.getMaxStackSize(itemstack);
					if (j <= k) {
						p_38904_.setCount(0);
						itemstack.setCount(j);
						slot.set(itemstack);
						flag = true;
					} else if (itemstack.getCount() < k) {
						p_38904_.shrink(k - itemstack.getCount());
						itemstack.setCount(k);
						slot.set(itemstack);
						flag = true;
					}
				}
				if (p_38907_) {
					i--;
				} else {
					i++;
				}
			}
		}
		if (!p_38904_.isEmpty()) {
			if (p_38907_) {
				i = p_38906_ - 1;
			} else {
				i = p_38905_;
			}
			while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
				Slot slot1 = this.slots.get(i);
				ItemStack itemstack1 = slot1.getItem();
				if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
					int l = slot1.getMaxStackSize(p_38904_);
					slot1.setByPlayer(p_38904_.split(Math.min(p_38904_.getCount(), l)));
					slot1.setChanged();
					flag = true;
					break;
				}
				if (p_38907_) {
					i--;
				} else {
					i++;
				}
			}
		}
		return flag;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int j = 0; j < internal.getContainerSize(); ++j) {
					playerIn.drop(internal.getItem(j), false);
					internal.setItem(j, ItemStack.EMPTY);
				}
			} else {
				for (int i = 0; i < internal.getContainerSize(); ++i) {
					playerIn.getInventory().placeItemBackInInventory(internal.getItem(i));
					internal.setItem(i, ItemStack.EMPTY);
				}
			}
		}
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}
}