package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.content.capability.volatilestack.VolatileStackCapabilityProvider;

import java.util.UUID;

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
        return baseDamage * (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));
    }

    @Override
    protected Lazy<ImmutableSetMultimap<Attribute, AttributeModifier>> buildDefaultAttributes() {
        return Lazy.of(() -> ImmutableSetMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION),
                Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION),
                ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("93bb7485-ce86-4e78-ab50-26f53d78ad9d"), "AoAGreatbladeReach", 1.5f, AttributeModifier.Operation.ADDITION)));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();

        if (material == Material.WEB) {
            return 25.0f;
        } else if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || material == Material.CORAL || material == Material.LEAVES || material == Material.VEGETABLE) {
            return 2.0f;
        } else {
            return 1.0f;
        }
    }
}
