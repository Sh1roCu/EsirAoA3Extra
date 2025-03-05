package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.OrbocronEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Orbocron extends BaseBlaster {
    public Orbocron(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_SHADOW_BLASTER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        shooter.level.addFreshEntity(new OrbocronEntity(shooter, this, 60));
    }

    @Override
    protected void doImpactEffect(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        for (LivingEntity e : shot.level.getEntitiesOfClass(LivingEntity.class, shot.getBoundingBox().inflate(15), EntityUtil.Predicates.HOSTILE_MOB)) {
            if (!EntityUtil.isImmuneToSpecialAttacks(e, shooter))
                EntityUtil.pullEntityIn(target, e, 0.5f, false);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
