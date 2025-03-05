package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.staff.BaronShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class BaronStaff extends BaseStaff<Object> {
    public BaronStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.WIND_RUNE.get(), 2);
        runes.put(AoAItems.POWER_RUNE.get(), 3);
    }

    @Override
    public void cast(World world, ItemStack staff, LivingEntity caster, Object args) {
        world.addFreshEntity(new BaronShotEntity(caster, this, 60));
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        if (DamageUtil.dealMagicDamage(shot, shooter, target, getDmg(), false)) {
            if (target instanceof LivingEntity && RandomUtil.oneInNChance(5))
                EntityUtil.applyPotions(target, new EffectBuilder(Effects.WEAKNESS, 120).level(2));

            return true;
        }

        return false;
    }

    @Override
    public float getDmg() {
        return 24 * this.getExtraDmg();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 2));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
