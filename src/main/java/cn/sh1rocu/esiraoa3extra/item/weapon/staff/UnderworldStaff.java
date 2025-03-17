package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.content.entity.projectile.staff.WitherShotEntity;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class UnderworldStaff extends BaseStaff<Object> {
    public UnderworldStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.COMPASS_RUNE.get(), 1);
        runes.put(AoAItems.KINETIC_RUNE.get(), 3);
    }

    @Override
    public void cast(World world, ItemStack staff, LivingEntity caster, Object args) {
        WitherShotEntity witherShot = new WitherShotEntity(caster, this, 60);
        createEnergyShot(world, staff, caster, witherShot);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        CompoundNBT nbt = shot.getPersistentData();
        float archMageMod = 1;
        archMageMod += 0.1f * nbt.getInt("archMageLevel");
        float totalMod = archMageMod * nbt.getFloat("extraDmgMod");
        if (DamageUtil.dealMagicDamage(shot, shooter, target, getDmg() * totalMod, false)) {
            target.push(0, -3.0f, 0);

            return true;
        }

        return false;
    }

    @Override
    public float getDmg() {
        return 10;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
