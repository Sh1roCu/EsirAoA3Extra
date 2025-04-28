package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BaseBow extends net.tslat.aoa3.content.item.weapon.bow.BaseBow {
    protected float drawSpeedMultiplier;
    protected double dmg;

    public BaseBow(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);
        this.dmg = damage;
        this.drawSpeedMultiplier = drawSpeedMultiplier;
    }

    public double getDamage() {
        return dmg;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity shooter, int timeLeft) {
        if (shooter instanceof PlayerEntity) {
            PlayerEntity pl = (PlayerEntity) shooter;
            boolean infiniteAmmo = pl.isCreative() || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack ammoStack = this.findAmmo(pl, stack, infiniteAmmo);
            float result = 1;
            if (stack.getOrCreateTag().contains("CD")) {
                double cdMod = stack.getOrCreateTag().getDouble("CD");
                result = (float) Math.max(1 + cdMod, 0);
            }
            int charge = (int) ((this.getUseDuration(stack) - timeLeft) * getDrawSpeedMultiplier() * result);
            charge = ForgeEventFactory.onArrowLoose(stack, world, pl, charge, !ammoStack.isEmpty() || infiniteAmmo);
            if (charge >= 0) {
                if (!ammoStack.isEmpty() || infiniteAmmo) {
                    if (ammoStack.isEmpty()) {
                        ammoStack = new ItemStack(Items.ARROW);
                    }

                    float velocity = getPowerForTime(charge);
                    if ((double) velocity >= 0.1) {
                        if (!world.isClientSide) {
                            CustomArrowEntity arrow = this.makeArrow(pl, stack, ammoStack, velocity, !infiniteAmmo);
                            arrow = this.doArrowMods(arrow, shooter, ammoStack, timeLeft);
                            float extraDmg = 0;
                            float amplifierLevel = 0;
                            float starLevel = 0;
                            if (shooter.getUsedItemHand().equals(Hand.MAIN_HAND)) {
                                float[] attribute = EsirUtil.getAttribute(stack);
                                if (attribute[0] != -1) {
                                    extraDmg = attribute[0];
                                    amplifierLevel = (int) attribute[1];
                                    starLevel = (int) attribute[2];
                                }
                            }
                            float extraDmgMod = (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));

                            CompoundNBT nbt = arrow.getPersistentData();
                            nbt.putFloat("extraDmgMod", extraDmgMod);
                            world.addFreshEntity(arrow);
                        }

                        world.playSound(null, pl.getX(), pl.getY(), pl.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
                        if (!infiniteAmmo && !pl.abilities.instabuild) {
                            ammoStack.shrink(1);
                            if (ammoStack.isEmpty()) {
                                pl.inventory.removeItem(ammoStack);
                            }
                        }

                        pl.awardStat(Stats.ITEM_USED.get(this));
                    }
                }

            }
        }
    }

    protected ItemStack findAmmo(PlayerEntity shooter, ItemStack bowStack, boolean infiniteAmmo) {
        return shooter.getProjectile(bowStack);
    }

    protected CustomArrowEntity makeArrow(LivingEntity shooter, ItemStack bowStack, ItemStack ammoStack, float velocity, boolean consumeAmmo) {
        ArrowItem arrowItem = (ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
        CustomArrowEntity arrow = CustomArrowEntity.fromArrow(arrowItem.createArrow(shooter.level, ammoStack, shooter), this, shooter, getDamage());

        arrow.shootFromRotation(shooter, shooter.xRot, shooter.yRot, 0.0F, velocity * 3.0F, 1.0F);

        if (velocity == 1.0F)
            arrow.setCritArrow(true);

        int powerEnchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack);
        int punchEnchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bowStack);

        if (powerEnchant > 0)
            arrow.setBaseDamage(arrow.getBaseDamage() + powerEnchant * 1.5D + 1D);

        if (punchEnchant > 0)
            arrow.setKnockback(punchEnchant);

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bowStack) > 0)
            arrow.setSecondsOnFire(100);

        bowStack.hurtAndBreak(1, shooter, (firingEntity) -> firingEntity.broadcastBreakEvent(shooter.getUsedItemHand()));

        if (!consumeAmmo || (shooter instanceof PlayerEntity && ((PlayerEntity) shooter).isCreative()) && (ammoStack.getItem() == Items.SPECTRAL_ARROW || ammoStack.getItem() == Items.TIPPED_ARROW))
            arrow.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        return arrow;
    }

    public float getDrawSpeedMultiplier() {
        return drawSpeedMultiplier;
    }

    public CustomArrowEntity doArrowMods(CustomArrowEntity arrow, LivingEntity shooter, ItemStack ammoStack, int useTicksRemaining) {
        return arrow;
    }

    public void onEntityHit(CustomArrowEntity arrow, Entity target, Entity shooter, double damage, float drawStrength) {
    }

    public void onBlockHit(CustomArrowEntity arrow, BlockRayTraceResult rayTrace, Entity shooter) {
    }

    public void onArrowTick(CustomArrowEntity arrow, Entity shooter) {
    }

    public double getArrowDamage(CustomArrowEntity arrow, Entity target, double currentDamage, float drawStrength, boolean isCritical) {
        double damage = currentDamage * 0.5d * (drawStrength / 3f);
        if (isCritical)
            damage += damage + (damage * 0.1f * random.nextGaussian());

        return damage * arrow.getPersistentData().getFloat("extraDmgMod");
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.arrows", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(Double.toString(getDamage()))));
        float result = 1;
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            result = (float) Math.max(1 + cdMod, 0);
        }
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.bow.drawSpeed", LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) ((int) (72000 / (getDrawSpeedMultiplier() * result)) / 720) / 100, 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, LocaleUtil.getLocaleMessage(Items.ARROW.getDescriptionId())));
    }
}