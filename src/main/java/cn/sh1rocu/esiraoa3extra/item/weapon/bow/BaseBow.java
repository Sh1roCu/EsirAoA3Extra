package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BaseBow extends net.tslat.aoa3.content.item.weapon.bow.BaseBow {
    public BaseBow(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity shooter, int timeLeft) {
        if (shooter instanceof Player) {
            Player pl = (Player) shooter;
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
                            if (shooter.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
                                float[] attribute = EsirUtil.getAttribute(stack);
                                if (attribute[0] != -1) {
                                    extraDmg = attribute[0];
                                    amplifierLevel = (int) attribute[1];
                                    starLevel = (int) attribute[2];
                                }
                            }
                            float extraDmgMod = (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));

                            CompoundTag nbt = arrow.getPersistentData();
                            nbt.putFloat("extraDmgMod", extraDmgMod);
                            world.addFreshEntity(arrow);
                        }

                        world.playSound(null, pl.getX(), pl.getY(), pl.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
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

    @Override
    public double getArrowDamage(CustomArrowEntity arrow, Entity target, double currentDamage, float drawStrength, boolean isCritical) {
        double damage = currentDamage * 0.5d * (drawStrength / 3f);
        if (isCritical)
            damage += damage + (damage * 0.1f * random.nextGaussian());

        return damage * arrow.getPersistentData().getFloat("extraDmgMod");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.arrows", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new TextComponent(Double.toString(getDamage()))));
        float result = 1;
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            result = (float) Math.max(1 + cdMod, 0);
        }
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.bow.drawSpeed", LocaleUtil.ItemDescriptionType.NEUTRAL, new TextComponent(NumberUtil.roundToNthDecimalPlace((float) ((int) (72000 / (getDrawSpeedMultiplier() * result)) / 720) / 100, 2))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, LocaleUtil.getLocaleMessage(Items.ARROW.getDescriptionId())));
    }
}