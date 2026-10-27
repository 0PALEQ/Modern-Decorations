package com.cookiecraftmods.mdm.block;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class OfficeSet1Desk2SideVar2Block extends TransparentHorizontalFurnitureBlock {

	public OfficeSet1Desk2SideVar2Block() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
