package cn.sh1rocu.esiraoa3extra.item.weapon.thrown;

import cn.sh1rocu.esiraoa3extra.item.weapon.gun.BaseGun;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
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
    double dmg;
    int firingDelay;

    public BaseThrownWeapon(double dmg, int fireDelayTicks) {
        super(new Item.Properties().tab(AoAItemGroups.THROWN_WEAPONS).stacksTo(64), dmg, fireDelayTicks, 0);

        this.dmg = dmg;
        this.firingDelay = fireDelayTicks;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFullAutomatic() {
        return false;
    }

    @Override
    public Item getAmmoItem() {
        return this;
    }

    @Override
    public float getRecoilForShot(ItemStack stack, LivingEntity shooter) {
        return 0;
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return SoundEvents.SPLASH_POTION_THROW;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
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

    @Override
    public BaseBullet findAndConsumeAmmo(LivingEntity shooter, ItemStack weaponStack, InteractionHand hand) {
        if (weaponStack.isEmpty())
            return null;

        if (!shooter.level.isClientSide() && shooter instanceof Player && !((Player) shooter).isCreative())
            weaponStack.shrink(1);

        return createProjectileEntity(shooter, weaponStack, hand);
    }

    @Override
    protected void doFiringEffects(LivingEntity shooter, BaseBullet bullet, ItemStack stack, InteractionHand hand) {
        doFiringSound(shooter, bullet, stack, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (dmg > 0.0f)
            tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.ranged", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(dmg)));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.thrownWeapon", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.throwable.speed", LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) firingDelay, 2))));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        return HashMultimap.create();
    }
}
