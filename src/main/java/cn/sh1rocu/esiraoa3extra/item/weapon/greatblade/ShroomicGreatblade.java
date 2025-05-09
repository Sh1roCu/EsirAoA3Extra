package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ShroomicGreatblade extends BaseGreatblade {
    public ShroomicGreatblade() {
        super(21.5f, AttackSpeed.GREATBLADE, 1300);
    }

    @Override
    public double getDamageForAttack(LivingEntity target, LivingEntity attacker, ItemStack swordStack, double baseDamage) {
        return super.getDamageForAttack(target, attacker, swordStack, (float) (attacker.hasEffect(MobEffects.POISON) ? baseDamage * 1.3f : baseDamage));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
