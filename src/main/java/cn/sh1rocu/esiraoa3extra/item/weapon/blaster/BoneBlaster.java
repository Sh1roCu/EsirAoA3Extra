package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.BonePelletEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;

import javax.annotation.Nullable;

public class BoneBlaster extends BaseBlaster {
    public BoneBlaster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_MINI_PISTOL_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot bonePellet = new BonePelletEntity(shooter, this, 60);
        createEnergyShot(blaster, shooter, bonePellet);
    }
}
