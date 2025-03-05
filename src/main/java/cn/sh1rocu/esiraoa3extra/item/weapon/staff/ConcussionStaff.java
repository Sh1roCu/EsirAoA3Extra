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
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ConcussionStaff extends BaseStaff<List<LivingEntity>> {
    public ConcussionStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_CONCUSSION_STAFF_CAST.get();
    }

    @Override
    public List<LivingEntity> checkPreconditions(LivingEntity caster, ItemStack staff) {
        List<LivingEntity> list = caster.level.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(8), EntityUtil.Predicates.HOSTILE_MOB);

        if (!list.isEmpty())
            return list;

        return null;
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.POWER_RUNE.get(), 4);
        runes.put(AoAItems.STORM_RUNE.get(), 4);
    }

    @Override
    public void cast(World world, ItemStack staff, LivingEntity caster, List<LivingEntity> args) {
        for (LivingEntity e : args) {
            EntityUtil.pushEntityAway(caster, e, 3f);
            WorldUtil.createExplosion(caster, e.level, e.getX(), e.getY() + e.getBbHeight() + 0.5, e.getZ(), 2.3f);
            EntityUtil.applyPotions(e, new EffectBuilder(Effects.MOVEMENT_SLOWDOWN, 25).level(10));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
