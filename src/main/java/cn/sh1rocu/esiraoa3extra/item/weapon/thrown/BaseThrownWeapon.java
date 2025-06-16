package cn.sh1rocu.esiraoa3extra.item.weapon.thrown;

import cn.sh1rocu.esiraoa3extra.item.weapon.gun.BaseGun;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
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
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseThrownWeapon extends BaseGun {
    public BaseThrownWeapon(double dmg, int fireDelayTicks) {
        super(new Item.Properties().tab(AoAItemGroups.THROWN_WEAPONS).stacksTo(64), dmg, fireDelayTicks, 0);
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
        float shellMod = 1;
        CompoundTag nbt = bullet.getPersistentData();
        float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
        if (bullet.getHand() != null)
            shellMod += 0.1f * nbt.getInt("shellLevel");
        if (target != null && dmg > 0.0f && DamageUtil.dealRangedDamage(target, shooter, bullet, (float) dmg * bulletDmgMultiplier * shellMod * extraDmgMod))
            doImpactEffect(target, shooter, bullet, bulletDmgMultiplier);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (dmg > 0.0f)
            tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.ranged", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(dmg)));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.thrownWeapon", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.throwable.speed", LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) firingDelay, 2))));
    }
}
