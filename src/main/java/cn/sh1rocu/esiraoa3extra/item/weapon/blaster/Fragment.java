package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.FragmentShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;

import javax.annotation.Nullable;

public class Fragment extends BaseBlaster {
    public Fragment(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_ILLUSION_SMG_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot fragmentShot = new FragmentShotEntity(shooter, this, 60);
        createEnergyShot(blaster, shooter, fragmentShot);
    }
}
