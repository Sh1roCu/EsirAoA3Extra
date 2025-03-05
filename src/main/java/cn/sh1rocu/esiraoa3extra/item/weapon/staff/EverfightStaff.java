package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class EverfightStaff extends BaseStaff<Float> {
    public EverfightStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_EVER_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.KINETIC_RUNE.get(), 4);
        runes.put(AoAItems.POWER_RUNE.get(), 4);
    }

    public Float checkPreconditions(LivingEntity caster, ItemStack staff) {
        float healthPercent = EntityUtil.getCurrentHealthPercent(caster);

        return healthPercent < 1 && healthPercent > 0 ? healthPercent : null;
    }

    @Override
    public void cast(World world, ItemStack staff, LivingEntity caster, Float args) {
        EntityUtil.applyPotions(caster, new EffectBuilder(Effects.DAMAGE_RESISTANCE, (int) (1200f * (1 - args))));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
