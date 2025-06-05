package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import cn.sh1rocu.esiraoa3extra.item.misc.BaseAmplifierStone;
import cn.sh1rocu.esiraoa3extra.item.misc.BaseDivineBlessingTalisman;
import cn.sh1rocu.esiraoa3extra.item.misc.BaseStarUpgradeTicket;
import cn.sh1rocu.esiraoa3extra.item.misc.ResurrectionStone;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.content.item.weapon.staff.BaseStaff;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseStaff.class)
public abstract class BaseStaffMixin {
    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"
            )
    )
    private void esir$reduceCD(ItemCooldowns instance, Item item, int origin, @Local ItemStack stack) {
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            int result = (int) (origin * (1 - cdMod));
            instance.addCooldown(item, Math.max(result, 0));
        } else {
            instance.addCooldown(item, origin);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void esir$dontUse(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (hand == InteractionHand.OFF_HAND) {
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (item instanceof BaseAmplifierStone
                    || item instanceof BaseDivineBlessingTalisman
                    || item instanceof BaseStarUpgradeTicket
                    || item instanceof ResurrectionStone
            )
                cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
        }
    }
}
