package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class PrimordialArmour extends AdventArmour {
    public PrimordialArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:primordial", 62, new int[]{5, 8, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.PRIMORDIAL;
    }

    @Override
    public void onAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        if (slots == null || (DamageUtil.isMeleeDamage(event.getSource()) && plData.equipment().getCurrentFullArmourSet() != setType())) {
            if (event.getSource().getEntity() instanceof LivingEntity && ((LivingEntity) event.getSource().getEntity()).hasEffect(MobEffects.WITHER))
                event.setAmount(event.getAmount() * (1 - 0.15f * (slots == null ? 4 : slots.size())));
        }
        super.onAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.primordial_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.primordial_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.primordial_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
