package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.cannon.RockFragmentEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;

import javax.annotation.Nullable;

public class JackRocker extends BaseCannon {
    double dmg;
    int firingDelay;

    public JackRocker(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
        this.dmg = dmg;
        this.firingDelay = firingDelayTicks;
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_JACK_ROCKER_FIRE.get();
    }

    @Override
    public Item getAmmoItem() {
        return Blocks.COBBLESTONE.asItem();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new RockFragmentEntity(shooter, this, hand, 120, 0);
    }
}
