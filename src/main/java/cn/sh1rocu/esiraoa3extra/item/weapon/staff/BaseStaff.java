package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseStaff<T> extends net.tslat.aoa3.content.item.weapon.staff.BaseStaff<T> {
    protected final HashMap<Item, Integer> runes = new HashMap<Item, Integer>(2);

    public BaseStaff(int durability) {
        super(durability);
    }

    public boolean findAndConsumeRunes(HashMap<Item, Integer> runes, ServerPlayerEntity player, boolean allowBuffs, ItemStack staff) {
        return ItemUtil.findAndConsumeRunes(runes, player, allowBuffs, staff);
    }

    @Nullable
    public T checkPreconditions(LivingEntity caster, ItemStack staff) {
        return (T) new Object();
    }

    public HashMap<Item, Integer> getRunes() {
        if (runes.isEmpty())
            populateRunes(runes);

        return runes;
    }

    @Nullable
    public SoundEvent getCastingSound() {
        return null;
    }

    protected abstract void populateRunes(HashMap<Item, Integer> runes);

    public abstract void cast(World world, ItemStack staff, LivingEntity caster, T args);

    @Override
    public void doBlockImpact(BaseEnergyShot shot, Vector3d hitPos, LivingEntity shooter) {
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        return true;
    }

    @Override
    public Hand getWeaponHand(LivingEntity holder) {
        return holder.getMainHandItem().getItem() == this || holder.getOffhandItem().getItem() != this ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    public float getDmg() {
        return 0;
    }

    public void createEnergyShot(World world, ItemStack staff, LivingEntity caster, BaseEnergyShot... shots) {
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        int archMageLevel = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.ARCHMAGE.get(), staff);
        if (getWeaponHand(caster).equals(Hand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(staff);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        float extraDmgMod = (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));

        CompoundNBT nbt;
        for (BaseEnergyShot shot : shots) {
            nbt = shot.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            nbt.putInt("archMageLevel", archMageLevel);
            world.addFreshEntity(shot);
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (getDmg() > 0)
            tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.magic", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(getDmg() * (1 + 0.1f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.ARCHMAGE.get(), stack)))));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.staff.runesRequired", LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST));

        for (Map.Entry<Item, Integer> runeEntry : getRunes().entrySet()) {
            tooltip.add(LocaleUtil.getLocaleMessage("items.description.staff.runesRequired.specific", TextFormatting.WHITE, LocaleUtil.numToComponent(runeEntry.getValue()), LocaleUtil.getLocaleMessage(runeEntry.getKey().getDescriptionId())));
        }
    }
}
