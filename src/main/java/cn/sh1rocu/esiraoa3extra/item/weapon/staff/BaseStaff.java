package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class BaseStaff<T> extends net.tslat.aoa3.content.item.weapon.staff.BaseStaff<T> {
    public BaseStaff(int durability) {
        super(durability);
    }

    public void createEnergyShot(Level world, ItemStack staff, LivingEntity caster, BaseEnergyShot... shots) {
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        int archMageLevel = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.ARCHMAGE.get(), staff);
        if (getWeaponHand(caster).equals(InteractionHand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(staff);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        float extraDmgMod = (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));

        CompoundTag nbt;
        for (BaseEnergyShot shot : shots) {
            nbt = shot.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            nbt.putInt("archMageLevel", archMageLevel);
            world.addFreshEntity(shot);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (getDmg() > 0)
            tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.magic", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(getDmg() * (1 + 0.1f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.ARCHMAGE.get(), stack)))));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.staff.runesRequired", LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST));

        for (Map.Entry<Item, Integer> runeEntry : getRunes().entrySet()) {
            tooltip.add(LocaleUtil.getLocaleMessage("items.description.staff.runesRequired.specific", ChatFormatting.WHITE, LocaleUtil.numToComponent(runeEntry.getValue()), LocaleUtil.getLocaleMessage(runeEntry.getKey().getDescriptionId())));
        }
    }
}
