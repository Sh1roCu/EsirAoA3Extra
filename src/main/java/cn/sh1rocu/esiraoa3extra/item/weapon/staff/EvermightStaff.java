package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class EvermightStaff extends BaseStaff<Float> {
    public EvermightStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_EMBER_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.DISTORTION_RUNE.get(), 2);
        runes.put(AoAItems.POWER_RUNE.get(), 4);
    }

    public Float checkPreconditions(LivingEntity caster, ItemStack staff) {
        float healthPercent = EntityUtil.getCurrentHealthPercent(caster);

        return healthPercent < 1 && healthPercent > 0 ? healthPercent : null;
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, Float args) {
        EntityUtil.applyPotions(caster, new EffectBuilder(MobEffects.DAMAGE_BOOST, (int) (1200f * (1 - args))).level(args < 0.1 ? 3 : args < 0.5 ? 2 : 1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
