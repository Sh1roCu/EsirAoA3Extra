package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.ProtonShotEntity;

import javax.annotation.Nullable;

public class Proton extends BaseBlaster {
    public Proton(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_ILLUSION_SMG_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        shooter.level.addFreshEntity(new ProtonShotEntity(shooter, this, 60));
    }
}
