package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
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

public class CrystikStaff extends BaseStaff<List<LivingEntity>> {
    public CrystikStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_CRYSTEVIA_STAFF_CAST.get();
    }

    @Override
    public List<LivingEntity> checkPreconditions(LivingEntity caster, ItemStack staff) {
        List<LivingEntity> targetList = caster.level.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(10), EntityUtil.Predicates.HOSTILE_MOB);

        if (targetList.isEmpty())
            return null;

        return targetList;
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.DISTORTION_RUNE.get(), 2);
        runes.put(AoAItems.LIFE_RUNE.get(), 1);
        runes.put(AoAItems.WATER_RUNE.get(), 1);
    }

    @Override
    public void cast(World world, ItemStack staff, LivingEntity caster, List<LivingEntity> args) {
        EntityUtil.applyPotions(args, new EffectBuilder(Effects.MOVEMENT_SLOWDOWN, 50).level(20), new EffectBuilder(Effects.DIG_SLOWDOWN, 50).level(20));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
