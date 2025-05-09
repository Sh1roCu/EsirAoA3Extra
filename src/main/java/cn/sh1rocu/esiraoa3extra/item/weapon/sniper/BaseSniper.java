package cn.sh1rocu.esiraoa3extra.item.weapon.sniper;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.advent.AdventOfAscension;
import net.tslat.aoa3.common.packet.AoAPackets;
import net.tslat.aoa3.common.packet.packets.GunRecoilPacket;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.SniperSlugEntity;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseSniper extends net.tslat.aoa3.content.item.weapon.sniper.BaseSniper {
    protected static final ResourceLocation SCOPE_1 = new ResourceLocation(AdventOfAscension.MOD_ID, "textures/gui/overlay/scope/scope1.png");
    protected static final ResourceLocation SCOPE_2 = new ResourceLocation(AdventOfAscension.MOD_ID, "textures/gui/overlay/scope/scope2.png");
    protected static final ResourceLocation SCOPE_3 = new ResourceLocation(AdventOfAscension.MOD_ID, "textures/gui/overlay/scope/scope3.png");
    protected static final ResourceLocation SCOPE_4 = new ResourceLocation(AdventOfAscension.MOD_ID, "textures/gui/overlay/scope/scope4.png");

    public BaseSniper(double dmg, int durability, int fireDelayTicks, float recoil) {
        super(dmg, durability, fireDelayTicks, recoil);
    }

    @Override
    public Item getAmmoItem() {
        return AoAItems.METAL_SLUG.get();
    }

    @Override
    public boolean isFullAutomatic() {
        return false;
    }

    @Override
    public void doRecoil(ServerPlayer player, ItemStack stack, InteractionHand hand) {
        int control = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.CONTROL.get(), stack);
        float recoilAmount = getRecoilForShot(stack, player) * 0.25f * (1 - control * 0.15f);

        if (!player.isShiftKeyDown() || !player.isOnGround())
            recoilAmount *= 3.5f;

        AoAPackets.messagePlayer(player, new GunRecoilPacket(recoilAmount, getFiringDelay()));
    }

    protected boolean fireGun(LivingEntity shooter, ItemStack stack, InteractionHand hand) {
        BaseBullet bullet = this.findAndConsumeAmmo(shooter, stack, hand);
        if (bullet == null) {
            return false;
        } else {
            if (!shooter.isOnGround() || !shooter.isShiftKeyDown()) {
                bullet.shootFromRotation(shooter, shooter.xRot, shooter.yRot, 0.0F, 20.0F, 50.0F);
            }

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
            nbt.putFloat("extraDmgMod", (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel)))));
            nbt.putInt("shellLevel", shellLevel);
            shooter.level.addFreshEntity(bullet);
            if (!shooter.level.isClientSide()) {
                this.doFiringEffects(shooter, bullet, stack, hand);
            }

            return true;
        }
    }

    @Override
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

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new SniperSlugEntity(shooter, this, 0);
    }

    public ResourceLocation getScopeTexture(ItemStack stack) {
        return SCOPE_1;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.gun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new TextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.1f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack))), 2))));
        tooltip.add(2, LocaleUtil.getFormattedItemDescriptionText("items.description.sniper.use", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        float cd = this.getFiringDelay() * (float) (stack.getOrCreateTag().contains("CD") ? 1 - stack.getOrCreateTag().getDouble("CD") : 1);
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(cd <= 0 ? "无限制" : NumberUtil.roundToNthDecimalPlace(20 / cd, 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));

    }
}
