package cn.sh1rocu.esiraoa3extra.mixin.compat.apotheosis;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import shadows.apotheosis.ench.enchantments.MagicProtEnchant;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract Iterable<ItemStack> getArmorSlots();

    @ModifyExpressionValue(method = "getDamageAfterMagicAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/world/damagesource/DamageSource;)I"))
    private int esir$getDamageProtection(int origin, @Local(argsOnly = true) DamageSource source) {
        MutableInt mutableInt = new MutableInt();
        EnchantmentHelper.runIterationOnInventory((arg2, i) -> {
            if ((LivingEntity) (Object) this instanceof Player && source.getEntity() instanceof Player && arg2 instanceof MagicProtEnchant)
                return;
            mutableInt.add(arg2.getDamageProtection(i, source));
        }, this.getArmorSlots());
        return mutableInt.intValue();
    }
}
