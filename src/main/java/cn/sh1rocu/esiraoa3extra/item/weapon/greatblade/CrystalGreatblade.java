package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class CrystalGreatblade extends BaseGreatblade {
    public CrystalGreatblade() {
        super(22.0f, AttackSpeed.GREATBLADE, 1480);
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        for (LivingEntity entity : target.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2.0f), EntityUtil.Predicates.HOSTILE_MOB)) {
            DamageUtil.dealAoeDamage(null, attacker, entity, Item.random.nextFloat() * 1.5f * attackCooldown, false);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
