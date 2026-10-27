package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class OfficeSet1Desk2MidBlock extends TransparentHorizontalFurnitureBlock {

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return OfficeDeskShapes.forFacing(OfficeDeskShapes.DESK_2_MID, state.getValue(FACING));
    }

	public OfficeSet1Desk2MidBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
