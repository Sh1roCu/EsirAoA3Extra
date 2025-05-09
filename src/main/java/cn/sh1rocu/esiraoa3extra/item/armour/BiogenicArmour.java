package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class BiogenicArmour extends AdventArmour {
    public BiogenicArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:biogenic", 38, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.BIOGENIC;
    }

    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (slots == null) {
            if (plData.player().isInWater())
                plData.player().setAirSupply(-10);

            if (plData.player().isEyeInFluid(FluidTags.WATER)) {
                plData.player().addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, false));
            } else {
                MobEffectInstance nightVision = plData.player().getEffect(MobEffects.NIGHT_VISION);

                if (nightVision != null && nightVision.getDuration() <= 300)
                    plData.player().removeEffect(MobEffects.NIGHT_VISION);
            }
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots != null && DamageUtil.isMeleeDamage(event.getSource()) && event.getSource().getEntity() instanceof LivingEntity)
            ((LivingEntity) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) event.getAmount() * 3 * slots.size(), slots.size() >= 2 ? 1 : 0, false, true));
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void onUnequip(ServerPlayerDataManager plData, @Nullable EquipmentSlot slot) {
        if (slot == null) {
            MobEffectInstance nightVision = plData.player().getEffect(MobEffects.NIGHT_VISION);

            if (nightVision != null && nightVision.getDuration() <= 300)
                plData.player().removeEffect(MobEffects.NIGHT_VISION);
        }
        super.onUnequip(plData, slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.biogenic_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.biogenic_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.biogenic_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
