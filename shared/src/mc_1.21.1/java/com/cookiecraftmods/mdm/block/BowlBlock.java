package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class BowlBlock extends TransparentHorizontalFurnitureBlock {

	public BowlBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
