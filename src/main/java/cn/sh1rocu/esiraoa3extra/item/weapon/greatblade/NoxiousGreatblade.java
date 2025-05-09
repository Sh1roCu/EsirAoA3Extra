package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ColourUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class NoxiousGreatblade extends BaseGreatblade {
    public NoxiousGreatblade() {
        super(23.0f, AttackSpeed.GREATBLADE, 1580);
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (target.hasEffect(MobEffects.POISON)) {
            AreaEffectCloud cloud = new AreaEffectCloud(target.level, target.getX(), target.getY(), target.getZ());

            cloud.setRadius(2);
            cloud.setPotion(Potions.STRONG_POISON);
            cloud.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (60 * attackCooldown), 2, true, true));
            cloud.setDuration(6);
            cloud.setFixedColor(ColourUtil.RGB(51, 102, 0));
            cloud.setOwner(attacker);

            target.level.addFreshEntity(cloud);
        } else {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (40 * attackCooldown), 1, true, true));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.POISONS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}