package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

public class OfficeSet1Desk2SideBlock extends TransparentHorizontalFurnitureBlock {

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OfficeDeskShapes.forFacing(OfficeDeskShapes.DESK_2_SIDE, state.getValue(FACING));
    }

	public OfficeSet1Desk2SideBlock() {
		super(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

}
