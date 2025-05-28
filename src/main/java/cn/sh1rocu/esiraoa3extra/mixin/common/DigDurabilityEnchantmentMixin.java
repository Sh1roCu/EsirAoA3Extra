package cn.sh1rocu.esiraoa3extra.mixin.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.potion.PotionCharmItem;

@Mixin(DigDurabilityEnchantment.class)
public class DigDurabilityEnchantmentMixin {
    @Inject(method = "canEnchant",at = @At("HEAD"), cancellable = true)
    private void esir$canNotEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (stack.getItem() instanceof PotionCharmItem)
            cir.setReturnValue(false);
    }
}
