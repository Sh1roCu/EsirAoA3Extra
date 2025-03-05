package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.FireBulletEntity;

import javax.annotation.Nullable;

public class FlameWrecker extends BaseGun {
    public FlameWrecker(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_HEAVY_GENERIC_FIRE_1.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new FireBulletEntity(shooter, this, hand, 120, 0);
    }
}
