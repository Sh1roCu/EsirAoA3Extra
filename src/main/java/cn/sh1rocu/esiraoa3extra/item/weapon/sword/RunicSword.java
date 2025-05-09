package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoATags;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class RunicSword extends BaseSword {
    public RunicSword() {
        super(ItemUtil.customItemTier(2450, AttackSpeed.NORMAL, 17.5f, 4, 10, null));
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (!attacker.level.isClientSide && attackCooldown > 0.75 && attacker instanceof Player) {
            ItemStack offhandStack = attacker.getOffhandItem();

            if (offhandStack.getItem().is(AoATags.Items.ADVENT_RUNE) && offhandStack.getCount() >= 5) {
                Item rune = offhandStack.getItem();

                if (rune == AoAItems.POISON_RUNE.get()) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 72, 1, false, true));
                } else if (rune == AoAItems.WITHER_RUNE.get()) {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 2, false, true));
                } else if (rune == AoAItems.FIRE_RUNE.get()) {
                    target.setSecondsOnFire(5);
                } else if (rune == AoAItems.WIND_RUNE.get()) {
                    DamageUtil.doScaledKnockback(target, attacker, 0.5f, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
                } else if (rune == AoAItems.WATER_RUNE.get()) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, true));
                } else if (rune == AoAItems.CHARGED_RUNE.get()) {
                    ((ServerLevel) target.level).sendParticles(ParticleTypes.ANGRY_VILLAGER, target.getX() + (random.nextFloat() * target.getBbWidth() * 2f) - target.getBbWidth(), target.getY() + 1 + (random.nextFloat() * target.getBbHeight()), target.getZ() + (random.nextFloat() * target.getBbWidth() * 2f) - target.getBbWidth(), 3, 0, 0, 0, 0);
                } else {
                    return;
                }

                if (!((Player) attacker).isCreative()) {
                    offhandStack.shrink(5);
                    ItemUtil.damageItem(stack, attacker, 1, EquipmentSlot.MAINHAND);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.HARMFUL, 2));
    }
}
