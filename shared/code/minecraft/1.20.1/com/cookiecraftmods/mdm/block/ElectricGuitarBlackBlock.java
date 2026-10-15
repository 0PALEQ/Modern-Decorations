package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class ElectricGuitarBlackBlock extends TransparentHorizontalFurnitureBlock {

	public ElectricGuitarBlackBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
