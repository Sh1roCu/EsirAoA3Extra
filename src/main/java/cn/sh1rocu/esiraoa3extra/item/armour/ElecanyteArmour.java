package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.tslat.aoa3.common.registration.custom.AoAResources;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ElecanyteArmour extends AdventArmour {
    public ElecanyteArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:elecanyte", 63, new int[]{4, 8, 9, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ELECANYTE;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots != null && !DamageUtil.isEnvironmentalDamage(event.getSource()))
            plData.getResource(AoAResources.SPIRIT.get()).addValue(event.getAmount() * 2.5f * slots.size());
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.elecanyte_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.elecanyte_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
