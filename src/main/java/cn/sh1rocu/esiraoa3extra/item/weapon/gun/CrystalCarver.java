package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.Tags;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoASounds;

import javax.annotation.Nullable;

public class CrystalCarver extends BaseGun {
    public CrystalCarver(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_GENERIC_FIRE_1.get();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairMaterial) {
        return repairMaterial.getItem().is(Tags.Items.GEMS) || super.isValidRepairItem(stack, repairMaterial);
    }
}
