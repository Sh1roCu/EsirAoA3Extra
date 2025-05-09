package cn.sh1rocu.esiraoa3extra.item.weapon.sniper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.common.registration.AoASounds;

import javax.annotation.Nullable;

public class ClownCracker extends BaseSniper {
    public ClownCracker(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_RIFLE_MEDIUM_FIRE_LONG.get();
    }

    @Override
    public ResourceLocation getScopeTexture(ItemStack stack) {
        return SCOPE_4;
    }
}
