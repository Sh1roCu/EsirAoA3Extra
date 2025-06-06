package cn.sh1rocu.esiraoa3extra.mixin.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract void setDamageValue(int damage);

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    public abstract Item getItem();

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"), cancellable = true)
    private void esir$modifyDurability(int amount, Random random, ServerPlayer user, CallbackInfoReturnable<Boolean> cir) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, (ItemStack) (Object) this) >= 15)
            amount = 0;
        else if (this.getItem() instanceof Wearable)
            amount = Math.min(amount, 3);
        int l = this.getDamageValue() + amount;
        this.setDamageValue(l);
        cir.setReturnValue(l >= this.getMaxDamage());
    }
}
