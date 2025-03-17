package cn.sh1rocu.esiraoa3extra.item.weapon.sniper;

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
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
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
    public void doRecoil(ServerPlayerEntity player, ItemStack stack, Hand hand) {
        int control = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.CONTROL.get(), stack);
        float recoilAmount = getRecoilForShot(stack, player) * 0.25f * (1 - control * 0.15f);

        if (!player.isShiftKeyDown() || !player.isOnGround())
            recoilAmount *= 3.5f;

        AoAPackets.messagePlayer(player, new GunRecoilPacket(recoilAmount, getFiringDelay()));
    }

    protected boolean fireGun(LivingEntity shooter, ItemStack stack, Hand hand) {
        BaseBullet bullet = this.findAndConsumeAmmo(shooter, stack, hand);
        if (bullet == null) {
            return false;
        } else {
            if (!shooter.isOnGround() || !shooter.isShiftKeyDown()) {
                bullet.shootFromRotation(shooter, shooter.xRot, shooter.yRot, 0.0F, 20.0F, 50.0F);
            }

            CompoundNBT nbt = bullet.getPersistentData();
            float extraDmg = 0;
            float amplifierLevel = 0;
            float starLevel = 0;
            int shellLevel = EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack);
            if (getGunHand(stack).equals(Hand.MAIN_HAND)) {
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
            CompoundNBT nbt = bullet.getPersistentData();
            float extraDmgMod = nbt.getFloat("extraDmgMod");
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
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new SniperSlugEntity(shooter, this, 0);
    }

    public ResourceLocation getScopeTexture(ItemStack stack) {
        return SCOPE_1;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.gun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.1f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack))), 2))));
        tooltip.add(2, LocaleUtil.getFormattedItemDescriptionText("items.description.sniper.use", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) getFiringDelay(), 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));

    }
}
