package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.content.capability.volatilestack.VolatileStackCapabilityProvider;

public class BaseGreatblade extends net.tslat.aoa3.content.item.weapon.greatblade.BaseGreatblade {
    public BaseGreatblade(final double baseDmg, final double attackSpeed, final int durability) {
        this(baseDmg, attackSpeed, durability, Rarity.COMMON);
    }

    public BaseGreatblade(final double baseDmg, final double attackSpeed, final int durability, Rarity rarity) {
        super(baseDmg, attackSpeed, durability, rarity);
    }

    @Override
    public double getDamageForAttack(LivingEntity target, LivingEntity attacker, ItemStack swordStack, double baseDamage) {
        if (attacker.fallDistance > 0 && !attacker.isOnGround() && !attacker.onClimbable() && !attacker.isInWater() && !attacker.isPassenger() && !attacker.hasEffect(MobEffects.BLINDNESS) && VolatileStackCapabilityProvider.getOrDefault(swordStack, Direction.NORTH).getValue() >= 1)
            baseDamage += 1.15f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SEVER.get(), swordStack);
        float extraDmg = 0;
        int amplifierLevel = 0;
        int starLevel = 0;
        float[] attribute = EsirUtil.getAttribute(swordStack);
        if (attribute[0] != -1) {
            extraDmg = attribute[0];
            amplifierLevel = (int) attribute[1];
            starLevel = (int) attribute[2];
        }
        return baseDamage * (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));
    }
}
