package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import cn.sh1rocu.esiraoa3extra.item.weapon.thrown.BaseThrownWeapon;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.tslat.aoa3.common.packet.AoAPackets;
import net.tslat.aoa3.common.packet.packets.GunRecoilPacket;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.content.enchantment.BraceEnchantment;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.LimoniteBulletEntity;
import net.tslat.aoa3.library.builder.SoundBuilder;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class BaseGun extends net.tslat.aoa3.content.item.weapon.gun.BaseGun {
    private final Multimap<Attribute, AttributeModifier> attributeModifiers = HashMultimap.create();

    protected static final UUID ATTACK_SPEED_MAINHAND = UUID.fromString("99fdc256-279e-4c8e-b1c6-9209571f134e");
    protected static final UUID ATTACK_SPEED_OFFHAND = UUID.fromString("63f030a6-7269-444d-b26c-ae3514b36e1b");

    protected final double dmg;

    protected final int firingDelay;
    protected final float recoilMod;
    protected double holsterMod;

    public BaseGun(Item.Properties properties, final double dmg, final int fireDelayTicks, final float recoilMod) {
        super(properties, dmg, fireDelayTicks, recoilMod);
        this.dmg = dmg;
        this.firingDelay = fireDelayTicks;
        this.recoilMod = recoilMod;
        this.holsterMod = getDamage() == 0 ? 0.85 : this instanceof BaseThrownWeapon ? 0.5 : 0.8 + 0.17 * Math.min(((20 / (double) getFiringDelay()) * getDamage()) / 55, 0.85);
        attributeModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MAINHAND, "AoAGunMainHand", -getHolsterSpeed(), AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    public BaseGun(CreativeModeTab CreativeModeTab, final double dmg, final int durability, final int fireDelayTicks, final float recoilMod) {
        this(new Item.Properties().tab(CreativeModeTab).durability(durability), dmg, fireDelayTicks, recoilMod);
    }

    public double getDamage() {
        return dmg;
    }

    public float getRecoilModifier() {
        return 0.35f;
    }

    public int getFiringDelay() {
        return firingDelay;
    }

    private double getHolsterSpeed() {
        return holsterMod;
    }

    @Nullable
    public SoundEvent getFiringSound() {
        return null;
    }

    protected float getFiringSoundPitchAdjust() {
        return 1f;
    }

    public float getRecoilForShot(ItemStack stack, LivingEntity shooter) {
        return (getDamage() == 0 ? 1 : (float) Math.pow(dmg, 1.4f)) * getRecoilModifier();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    public Item getAmmoItem() {
        return AoAItems.LIMONITE_BULLET.get();
    }

    public InteractionHand getGunHand(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.BRACE.get(), stack) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairMaterial) {
        return false;
    }

    public boolean isFullAutomatic() {
        return true;
    }

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

    public void doRecoil(ServerPlayer player, ItemStack stack, InteractionHand hand) {
        int control = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.CONTROL.get(), stack);
        float recoilAmount = getRecoilForShot(stack, player) * 2 * (1 - control * 0.15f);

        AoAPackets.messagePlayer(player, new GunRecoilPacket(hand == InteractionHand.OFF_HAND ? recoilAmount * 1.25f : recoilAmount, getFiringDelay()));
    }

    protected void doFiringEffects(LivingEntity shooter, BaseBullet bullet, ItemStack stack, InteractionHand hand) {
        doFiringSound(shooter, bullet, stack, hand);

        ((ServerLevel) shooter.level).sendParticles(ParticleTypes.SMOKE, bullet.getX(), bullet.getY(), bullet.getZ(), 2, 0, 0, 0, 0.025f);

        if (dmg > 15) {
            if (dmg > 20) {
                ((ServerLevel) shooter.level).sendParticles(ParticleTypes.FLAME, bullet.getX(), bullet.getY(), bullet.getZ(), 2, 0, 0, 0, 0.025f);
            }

            ((ServerLevel) shooter.level).sendParticles(ParticleTypes.POOF, bullet.getX(), bullet.getY(), bullet.getZ(), 2, 0, 0, 0, 0.025f);
        }
    }

    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            float shellMod = 1;
            CompoundTag nbt = bullet.getPersistentData();
            float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
            if (bullet.getHand() != null)
                shellMod += 0.1f * nbt.getInt("shellLevel");

            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) getDamage() * bulletDmgMultiplier * shellMod * extraDmgMod)) {
                doImpactEffect(target, shooter, bullet, bulletDmgMultiplier);
            } else if (!(target instanceof LivingEntity)) {
                target.hurt(new IndirectEntityDamageSource("gun", bullet, shooter).setProjectile(), (float) getDamage() * bulletDmgMultiplier * shellMod * extraDmgMod);
            }
        }
    }

    protected void doImpactEffect(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
    }

    protected void doFiringSound(LivingEntity shooter, BaseBullet bullet, ItemStack stack, InteractionHand hand) {
        //if (getFiringSound() != null)
        //	shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), getFiringSound(), SoundSource.PLAYERS, 1.0f, getFiringSoundPitchAdjust() + (float)random.nextGaussian() * 0.075f);

        if (getFiringSound() != null)
            new SoundBuilder(getFiringSound()).isPlayer().pitch(getFiringSoundPitchAdjust()).varyPitch(0.075f).followEntity(shooter).play();
    }

    @Nullable
    public BaseBullet findAndConsumeAmmo(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        if (shooter.getType() != EntityType.PLAYER || ItemUtil.findInventoryItem((Player) shooter, new ItemStack(getAmmoItem()), !shooter.level.isClientSide(), 1 + EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.GREED.get(), gunStack)))
            return createProjectileEntity(shooter, gunStack, hand);

        return null;
    }

    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new LimoniteBulletEntity(shooter, this, hand, 120, 0);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.getOrCreateTag().putInt("HideFlags", ItemStack.TooltipPart.MODIFIERS.getMask());

        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return HashMultimap.create(attributeModifiers);
        } else if (slot == EquipmentSlot.OFFHAND) {
            Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(attributeModifiers);

            multimap.put(Attributes.MOVEMENT_SPEED, BraceEnchantment.BRACE_DEBUFF);

            return multimap;
        }

        return super.getAttributeModifiers(slot, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.gun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new TextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.1f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack))), 2))));
        float cd = this.getFiringDelay() * (float) (stack.getOrCreateTag().contains("CD") ? 1 - stack.getOrCreateTag().getDouble("CD") : 1);
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(cd <= 0 ? "无限制" : NumberUtil.roundToNthDecimalPlace(20 / cd, 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
    }
}
