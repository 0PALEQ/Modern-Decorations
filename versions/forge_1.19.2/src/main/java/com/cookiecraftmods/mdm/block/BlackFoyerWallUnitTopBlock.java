package com.cookiecraftmods.mdm.block;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.MulticellFurniture;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;


public class BlackFoyerWallUnitTopBlock extends HorizontalFurnitureBlock implements MulticellFurniture {

	public BlackFoyerWallUnitTopBlock() {
		super(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}


	@Override
	public VoxelShape getFurnitureShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return FoyerWallUnitShapes.forFacing(FoyerWallUnitShapes.TOP, state.getValue(FACING));
	}

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.localShape(getFurnitureShape(state, world, pos, context), BlockPos.ZERO);
    }

}
