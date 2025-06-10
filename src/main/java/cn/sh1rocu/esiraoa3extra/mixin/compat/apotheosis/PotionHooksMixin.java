package cn.sh1rocu.esiraoa3extra.mixin.compat.apotheosis;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Registry;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import shadows.apotheosis.ench.enchantments.MagicProtEnchant;
import shadows.apotheosis.potion.asm.PotionHooks;

@Mixin(PotionHooks.class)
public class PotionHooksMixin {
    @Unique
    private static int esir$getDamageProtectionWithoutMagicProtection(LivingEntity entity, DamageSource source) {
        MutableInt mutableInt = new MutableInt();
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!stack.isEmpty()) {
                ListTag listNBT = stack.getEnchantmentTags();
                for (int i = 0; i < listNBT.size(); ++i) {
                    String s = listNBT.getCompound(i).getString("id");
                    int j = listNBT.getCompound(i).getInt("lvl");
                    Registry.ENCHANTMENT.getOptional(ResourceLocation.tryParse(s)).ifPresent((arg2) -> {
                        if (entity instanceof Player && source.getEntity() instanceof Player && arg2 instanceof MagicProtEnchant)
                            return;
                        mutableInt.add(arg2.getDamageProtection(j, source));
                    });
                }
            }
        }
        return mutableInt.getValue();
    }

    @ModifyExpressionValue(remap = false, method = "applyPotionDamageCalculations", at = @At(remap = true, value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/world/damagesource/DamageSource;)I"))
    private static int esir$getDamageProtection(int origin, @Local(argsOnly = true) LivingEntity entity, @Local(argsOnly = true) DamageSource source) {
        return esir$getDamageProtectionWithoutMagicProtection(entity, source);
    }
}
