package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.content.entity.projectile.cannon.CannonballEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseCannon extends net.tslat.aoa3.content.item.weapon.cannon.BaseCannon {
    protected float extraDmg = 0;
    protected int amplifierLevel = 0;
    protected int starLevel = 0;

    public BaseCannon(double dmg, int durability, int fireDelayTicks, float recoil) {
        super(dmg, durability, fireDelayTicks, recoil);
    }

    @Override
    public Item getAmmoItem() {
        return AoAItems.CANNONBALL.get();
    }

    @Override
    public boolean isFullAutomatic() {
        return false;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity shooter, int count) {
        this.extraDmg = 0;
        this.amplifierLevel = 0;
        this.starLevel = 0;
        if (getGunHand(stack).equals(Hand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(stack);
            if (attribute[0] != -1) {
                this.extraDmg = attribute[0];
                this.amplifierLevel = (int) attribute[1];
                this.starLevel = (int) attribute[2];
            }
        }
        super.onUsingTick(stack, shooter, count);
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            if (target instanceof LivingEntity)
                bulletDmgMultiplier *= 1 + (((LivingEntity) target).getAttribute(Attributes.ARMOR).getValue() * 1.50) / 100;

            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) getDamage() * bulletDmgMultiplier * (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel)))))) {
                if (target instanceof PlayerEntity && ((PlayerEntity) target).isBlocking())
                    ((PlayerEntity) target).disableShield(true);

                if (target instanceof LivingEntity)
                    DamageUtil.doScaledKnockback((LivingEntity) target, shooter, ((float) getDamage() * bulletDmgMultiplier) * (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel)))) / 10f, shooter.getX() - target.getX(), shooter.getZ() - target.getZ());

                doImpactEffect(target, shooter, bullet, bulletDmgMultiplier);
            }
        }
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new CannonballEntity(shooter, this, hand, 120, 0);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World
            world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.gun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.2f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack))), 2))));
        tooltip.add(2, LocaleUtil.getFormattedItemDescriptionText("items.description.cannon.damage", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) getFiringDelay(), 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
    }
}
