package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.world.World;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import com.cookiecraftmods.mdm.block.entity.OfficeSet1Desk1LeftVar2BlockEntity;

import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;

public class OfficeSet1Desk1LeftVar2Block extends TransparentHorizontalFurnitureBlock implements ITileEntityProvider {

	public OfficeSet1Desk1LeftVar2Block() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		if (entity instanceof ServerPlayerEntity player) {
			if (world.getBlockEntity(pos) instanceof INamedContainerProvider provider) {
				player.openMenu(provider);
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof INamedContainerProvider menuProvider ? menuProvider : null;
	}

	@Override
	public TileEntity newBlockEntity(net.minecraft.world.IBlockReader level) {
		return new OfficeSet1Desk1LeftVar2BlockEntity();
	}

	@Override
	public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		TileEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof OfficeSet1Desk1LeftVar2BlockEntity be) {
				InventoryHelper.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof OfficeSet1Desk1LeftVar2BlockEntity be)
			return Container.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}
