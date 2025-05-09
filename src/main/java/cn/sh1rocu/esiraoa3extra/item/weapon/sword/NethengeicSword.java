package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class NethengeicSword extends BaseSword {
    public NethengeicSword() {
        super(ItemUtil.customItemTier(2040, AttackSpeed.NORMAL, 14.0f, 4, 10, null));
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (!attacker.level.isClientSide) {
            if (target.fireImmune() || target.isInvulnerableTo(DamageSource.ON_FIRE)) {

                target.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) (80 * attackCooldown), 2, false, true));
            } else {
                target.setSecondsOnFire((int) (4 * (attacker instanceof Player ? attackCooldown : 1)));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.BURNS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
