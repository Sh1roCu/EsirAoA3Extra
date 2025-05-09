package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.sounds.SoundEvent;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoASounds;

import javax.annotation.Nullable;

public class MK extends BaseGun {
    public MK(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_GENERIC_FIRE_5.get();
    }
}
