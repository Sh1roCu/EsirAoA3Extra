package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.cannon.UltraGreenBallEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;

import javax.annotation.Nullable;

public class UltraCannon extends BaseCannon {
    public UltraCannon(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_UPPER_CANNON_FIRE.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new UltraGreenBallEntity(shooter, this, hand, 120, 0);
    }
}
