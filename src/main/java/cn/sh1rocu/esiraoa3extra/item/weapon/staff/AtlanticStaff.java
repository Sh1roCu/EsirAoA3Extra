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

public class AtlanticStaff extends BaseStaff<List<LivingEntity>> {
    public AtlanticStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_ATLANTIC_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.DISTORTION_RUNE.get(), 1);
        runes.put(AoAItems.ENERGY_RUNE.get(), 2);
        runes.put(AoAItems.STORM_RUNE.get(), 2);
    }

    @Override
    public List<LivingEntity> checkPreconditions(LivingEntity caster, ItemStack staff) {
        List<LivingEntity> list = caster.level.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(10), EntityUtil.Predicates.HOSTILE_MOB);

        if (!list.isEmpty())
            return list;

        return null;
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, List<LivingEntity> args) {
        EntityUtil.applyPotions(args, new EffectBuilder(MobEffects.MOVEMENT_SLOWDOWN, 100).level(2));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
