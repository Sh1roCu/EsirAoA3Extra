package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.cannon.OrangeCannonballEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;

import javax.annotation.Nullable;

public class MechaCannon extends BaseCannon {
    public MechaCannon(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_MECHA_CANNON_FIRE.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new OrangeCannonballEntity(shooter, this, hand, 120, 0);
    }
}
