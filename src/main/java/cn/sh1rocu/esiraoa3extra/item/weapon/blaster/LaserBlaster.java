package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.LaserShotEntity;

import javax.annotation.Nullable;

public class LaserBlaster extends BaseBlaster {
    public LaserBlaster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_ILLUSION_SMG_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        LaserShotEntity laserShot = new LaserShotEntity(shooter, this, 60);
        createEnergyShot(blaster, shooter, laserShot);
    }
}
