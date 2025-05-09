package cn.sh1rocu.esiraoa3extra.mixin;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow
    public abstract String getDescriptionId();

    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void esir$canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (EsirUtil.BLACKLIST_ENCHANTMENTS.contains(this.getDescriptionId()))
            cir.setReturnValue(false);
    }

    @Inject(remap = false, method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true)
    private void esir$canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (EsirUtil.BLACKLIST_ENCHANTMENTS.contains(this.getDescriptionId()))
            cir.setReturnValue(false);
    }

    @Inject(remap = false, method = "isAllowedOnBooks", at = @At("HEAD"), cancellable = true)
    private void esir$isAllowedOnBooks(CallbackInfoReturnable<Boolean> cir) {
        if (EsirUtil.BLACKLIST_ENCHANTMENTS.contains(this.getDescriptionId()))
            cir.setReturnValue(false);
    }

    @Inject(method = "isDiscoverable", at = @At("HEAD"), cancellable = true)
    private void esir$isDiscoverable(CallbackInfoReturnable<Boolean> cir) {
        if (EsirUtil.BLACKLIST_ENCHANTMENTS.contains(this.getDescriptionId()))
            cir.setReturnValue(false);
    }

    @Inject(method = "isTradeable", at = @At("HEAD"), cancellable = true)
    private void esir$isTradeable(CallbackInfoReturnable<Boolean> cir) {
        if (EsirUtil.BLACKLIST_ENCHANTMENTS.contains(this.getDescriptionId()))
            cir.setReturnValue(false);
    }
}
