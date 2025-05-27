package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.content.item.tool.misc.HaulingRod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(HaulingRod.class)
public class HaulingRodMixin {
    @Unique
    private final Map<UUID, Long> esirAoA3Extra$timeStampMap = new HashMap<>();

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void esir$fixAoARod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        UUID uuid = player.getUUID();
        if (!esirAoA3Extra$timeStampMap.containsKey(uuid))
            esirAoA3Extra$timeStampMap.put(uuid, System.currentTimeMillis());
        else if (System.currentTimeMillis() - esirAoA3Extra$timeStampMap.get(uuid) < 60)
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide()));
        else esirAoA3Extra$timeStampMap.put(uuid, System.currentTimeMillis());
    }
}
