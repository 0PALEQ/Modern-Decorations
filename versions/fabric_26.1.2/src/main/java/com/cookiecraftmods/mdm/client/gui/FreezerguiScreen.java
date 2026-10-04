package com.cookiecraftmods.mdm.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;

import com.cookiecraftmods.mdm.world.inventory.FreezerguiMenu;

public class FreezerguiScreen extends AbstractContainerScreen<FreezerguiMenu> {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private static final Identifier BACKGROUND = Identifier.parse("mdm:textures/screens/freezergui.png");

	public FreezerguiScreen(FreezerguiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 186, 190);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0,
				this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (event.key() == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.mdm.freezergui.label_freezer"), 74, 10, -10027009, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
