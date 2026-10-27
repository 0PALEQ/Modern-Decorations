package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class OutdoorTableBlock extends TransparentHorizontalFurnitureBlock {

	public OutdoorTableBlock() {
		super(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
