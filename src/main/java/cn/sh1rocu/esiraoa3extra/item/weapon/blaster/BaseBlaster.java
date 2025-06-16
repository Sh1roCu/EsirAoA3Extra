package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.custom.AoAResources;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseBlaster extends net.tslat.aoa3.content.item.weapon.blaster.BaseBlaster {
    public BaseBlaster(Item.Properties properties, final double dmg, final int fireDelayTicks, final float energyCost) {
        super(properties, dmg, fireDelayTicks, energyCost);
    }

    public BaseBlaster(final double dmg, final int durability, final int fireDelayTicks, final float energyCost) {
        this(new Item.Properties().tab(AoAItemGroups.BLASTERS).durability(durability), dmg, fireDelayTicks, energyCost);
    }

    public void createEnergyShot(ItemStack blaster, LivingEntity shooter, BaseEnergyShot... shots) {
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        if (getWeaponHand(shooter).equals(InteractionHand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(blaster);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        float extraDmgMod = (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));
        int rechargeLevel = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.RECHARGE.get(), blaster);

        CompoundTag nbt;
        for (BaseEnergyShot shot : shots) {
            nbt = shot.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            nbt.putInt("rechargeLevel", rechargeLevel);
            shooter.level.addFreshEntity(shot);
        }
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        CompoundTag nbt = shot.getPersistentData();
        float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
        float rechargeMod = 1 + 0.04f * nbt.getInt("rechargeLevel");
        if (DamageUtil.dealBlasterDamage(shooter, target, shot, (float) baseDmg * rechargeMod * extraDmgMod, false)) {
            doImpactEffect(shot, target, shooter);

            return true;
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (getDamage() > 0)
            tooltip.add(1, LocaleUtil.getLocaleMessage("items.description.damage.blaster", ChatFormatting.DARK_RED, new TextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.04f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.RECHARGE.get(), stack))), 1))));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.blaster.fire", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.blaster.effect", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) getFiringDelay(), 2))));

        float energyConsumption = (1 + (0.3f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.GREED.get(), stack))) * getEnergyCost() * Math.max(0, (1 - 0.07F * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.RECHARGE.get(), stack)));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_RESOURCE, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, new TextComponent(NumberUtil.roundToNthDecimalPlace(energyConsumption, 2)), AoAResources.SPIRIT.get().getName()));
    }
}
