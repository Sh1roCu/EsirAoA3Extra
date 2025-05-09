package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.ToxicShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GasBlaster extends BaseBlaster {
    public GasBlaster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GAS_GUN_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot toxicShot = new ToxicShotEntity(shooter, this, 1);
        createEnergyShot(blaster, shooter, toxicShot);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        if (target instanceof LivingEntity) {
            if (target instanceof Player || (target instanceof TamableAnimal && shooter.getUUID().equals(((TamableAnimal) target).getOwnerUUID()))) {
                EntityUtil.healEntity((LivingEntity) target, 0.05f);

                return true;
            }
        }

        return super.doEntityImpact(shot, target, shooter);
    }

    @Override
    protected void doImpactEffect(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        if (!((LivingEntity) target).hasEffect(MobEffects.POISON))
            EntityUtil.applyPotions(target, new EffectBuilder(MobEffects.POISON, 13).level(2));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
