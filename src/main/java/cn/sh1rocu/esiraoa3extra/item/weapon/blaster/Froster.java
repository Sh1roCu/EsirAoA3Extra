package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.BeamerShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;

import javax.annotation.Nullable;

public class Froster extends BaseBlaster {
    public Froster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_SPRAYER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot beamerShot = new BeamerShotEntity(shooter, this, 60, 0, 0, 0);
        createEnergyShot(blaster, shooter, beamerShot);
    }
}
