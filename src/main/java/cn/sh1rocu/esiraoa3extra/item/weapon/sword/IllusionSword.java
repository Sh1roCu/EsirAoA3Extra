package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.List;

public class IllusionSword extends BaseSword {
    public IllusionSword() { // TODO look into false-swipe attacking
        super(ItemUtil.customItemTier(1900, AttackSpeed.NORMAL, 14.5f, 4, 10, null));
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (!attacker.level.isClientSide && !EntityUtil.isImmuneToSpecialAttacks(target, attacker) && RandomUtil.percentChance(0.1f * attackCooldown)) {
            List<LivingEntity> nearbyMobs = target.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5), EntityUtil.Predicates.HOSTILE_MOB);

            if (nearbyMobs.size() > 1) {
                LivingEntity newTarget = null;

                for (LivingEntity nearbyMob : nearbyMobs) {
                    if (nearbyMob != attacker && nearbyMob != target) {
                        newTarget = nearbyMob;

                        break;
                    }
                }

                if (newTarget == null)
                    return;

                target.setLastHurtByMob(newTarget);

                if (target instanceof CreatureEntity)
                    ((CreatureEntity) target).setTarget(newTarget);

                target.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0, false, true));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
