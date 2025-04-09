package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.tslat.aoa3.content.enchantment.ShellEnchantment;
import net.tslat.aoa3.content.item.weapon.cannon.BaseCannon;
import net.tslat.aoa3.content.item.weapon.gun.BaseGun;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ShellEnchantment.class, remap = false)
public class ShellEnchantmentMixin {
    @Inject(method = "canEnchant", at = @At("RETURN"), cancellable = true)
    private void shellEnchantmentMixin$canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item item = stack.getItem();
        cir.setReturnValue(item instanceof BaseGun || item instanceof BaseCannon);
    }
}