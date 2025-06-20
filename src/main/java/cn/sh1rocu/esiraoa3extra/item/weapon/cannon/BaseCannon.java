package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseCannon extends net.tslat.aoa3.content.item.weapon.cannon.BaseCannon {
    public BaseCannon(double dmg, int durability, int fireDelayTicks, float recoil) {
        super(dmg, fireDelayTicks, fireDelayTicks, recoil);
    }

    @Override
    protected boolean fireGun(LivingEntity shooter, ItemStack stack, InteractionHand hand) {
        BaseBullet bullet = findAndConsumeAmmo(shooter, stack, hand);

        if (bullet == null)
            return false;
        CompoundTag nbt = bullet.getPersistentData();
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        int shellLevel = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack);
        if (getGunHand(stack).equals(InteractionHand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(stack);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        nbt.putFloat("extraDmgMod", (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel)))));
        nbt.putInt("shellLevel", shellLevel);

        shooter.level.addFreshEntity(bullet);

        if (!shooter.level.isClientSide())
            doFiringEffects(shooter, bullet, stack, hand);

        return true;
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            if (target instanceof LivingEntity)
                bulletDmgMultiplier *= (float) (1 + (((LivingEntity) target).getAttribute(Attributes.ARMOR).getValue() * 1.50) / 100);
            float shellMod = 1;
            CompoundTag nbt = bullet.getPersistentData();
            shellMod += 0.1f * nbt.getInt("shellLevel");
            float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) getDamage() * bulletDmgMultiplier * shellMod * extraDmgMod)) {
                if (target instanceof Player && ((Player) target).isBlocking())
                    ((Player) target).disableShield(true);

                if (target instanceof LivingEntity)
                    DamageUtil.doScaledKnockback((LivingEntity) target, shooter, ((float) getDamage() * bulletDmgMultiplier) * shellMod * extraDmgMod / 10f, shooter.getX() - target.getX(), shooter.getZ() - target.getZ());

                doImpactEffect(target, shooter, bullet, bulletDmgMultiplier);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.gun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new TextComponent(NumberUtil.roundToNthDecimalPlace((float) this.getDamage() * (1.0F + 0.1F * (float) EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack)), 2))));
        tooltip.add(2, LocaleUtil.getFormattedItemDescriptionText("items.description.cannon.damage", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        float cd = this.getFiringDelay() * (float) (stack.getOrCreateTag().contains("CD") ? 1 - stack.getOrCreateTag().getDouble("CD") : 1);
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.gun.firingSpeed", LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(cd <= 0 ? "无限制" : NumberUtil.roundToNthDecimalPlace(20.0F / cd, 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.ammo.item", LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, this.getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this.isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
    }
}