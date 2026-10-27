package com.cookiecraftmods.mdm.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class ElectricGuitarWithStandBlock extends TransparentHorizontalFurnitureBlock {

	public ElectricGuitarWithStandBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
