package cn.sh1rocu.esiraoa3extra.item.weapon.shotgun;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.LimoniteBulletEntity;
import net.tslat.aoa3.content.entity.projectile.gun.MetalSlugEntity;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BaseShotgun extends net.tslat.aoa3.content.item.weapon.shotgun.BaseShotgun {
    protected final int pelletCount;
    protected final float knockbackFactor;

    protected float extraDmg = 0;
    protected int amplifierLevel = 0;
    protected int starLevel = 0;

    public BaseShotgun(final double dmg, final int pellets, final int durability, final int fireDelayTicks, final float knockbackFactor, final float recoil) {
        super(dmg, pellets, durability, fireDelayTicks, knockbackFactor, recoil);

        this.pelletCount = pellets;
        this.knockbackFactor = knockbackFactor;
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_SHOTGUN_MEDIUM_FIRE_LONG.get();
    }

    @Override
    public Item getAmmoItem() {
        return AoAItems.SPREADSHOT.get();
    }

    @Override
    public boolean isFullAutomatic() {
        return false;
    }

    public int getPelletCount() {
        return pelletCount;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        this.extraDmg = 0;
        this.amplifierLevel = 0;
        this.starLevel = 0;
        if (hand.equals(Hand.MAIN_HAND)) {
            ItemStack stack = player.getItemInHand(hand);
            float[] attribute = EsirUtil.getAttribute(stack);
            if (attribute[0] != -1) {
                this.extraDmg = attribute[0];
                this.amplifierLevel = (int) attribute[1];
                this.starLevel = (int) attribute[2];
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            float shellMod = 1;

            if (bullet.getHand() != null)
                shellMod += 0.2f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), shooter.getItemInHand(bullet.getHand()));

            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) getDamage() * bulletDmgMultiplier * shellMod * (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel)))))) {
                doImpactEffect(target, shooter, bullet, bulletDmgMultiplier);
            } else if (!(target instanceof LivingEntity)) {
                target.hurt(new IndirectEntityDamageSource("gun", bullet, shooter).setProjectile(), (float) getDamage() * bulletDmgMultiplier * shellMod);
            }
        }
    }

    @Override
    protected boolean fireGun(LivingEntity shooter, ItemStack stack, Hand hand) {
        BaseBullet bullet = this.findAndConsumeAmmo(shooter, stack, hand);
        if (bullet == null) {
            return false;
        } else {
            int pellets = this.getPelletCount();
            float spreadFactor = 0.1F * (float) pellets * (1.0F - 0.25F * (float) EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.FORM.get(), stack));

            for (int i = 0; i < pellets; ++i) {
                BaseBullet pellet = new LimoniteBulletEntity(shooter, this, hand, 4, 1.0F, 0, (random.nextFloat() - 0.5F) * spreadFactor, (random.nextFloat() - 0.5F) * spreadFactor, (random.nextFloat() - 0.5F) * spreadFactor);
                shooter.level.addFreshEntity(pellet);
            }

            if (!shooter.level.isClientSide()) {
                this.doFiringEffects(shooter, bullet, stack, hand);
            }

            return true;
        }
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new MetalSlugEntity(shooter, this, hand, 4, 0);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.shotgun", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage() * (1 + (0.2f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.SHELL.get(), stack))), 2)), LocaleUtil.numToComponent(pelletCount)));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) getFiringDelay(), 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, getAmmoItem().getDescription()));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(isFullAutomatic() ? "items.description.gun.fully_automatic" : "items.description.gun.semi_automatic", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
    }
}
