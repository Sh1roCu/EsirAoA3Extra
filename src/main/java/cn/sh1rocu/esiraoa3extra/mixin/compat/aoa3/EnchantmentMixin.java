package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.tslat.aoa3.content.enchantment.ShellEnchantment;
import net.tslat.aoa3.content.item.weapon.cannon.BaseCannon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(remap = false, method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true)
    private void esir$canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Enchantment) (Object) this instanceof ShellEnchantment && stack.getItem() instanceof BaseCannon)
            cir.setReturnValue(true);
    }
}
