package com.cookiecraftmods.mdm.client.placement;
import com.cookiecraftmods.mdm.placement.PrecisePlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;

public final class PlacementPreview {
    private PlacementPreview() {}
    public static PrecisePlacement.Plan current() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.player.isSpectator()
                || !mc.player.isShiftKeyDown() || !(mc.hitResult instanceof BlockHitResult hit) || hit.getType() != net.minecraft.world.phys.HitResult.Type.BLOCK) return null;
        for (InteractionHand hand : InteractionHand.values()) {
            var stack = mc.player.getItemInHand(hand);
            if (!(stack.getItem() instanceof BlockItem item) || !PrecisePlacement.supported(item.getBlock())) continue;
            var context = new BlockPlaceContext(mc.player, hand, stack, hit);
            return PrecisePlacement.plan(context);
        }
        return null;
    }
}
