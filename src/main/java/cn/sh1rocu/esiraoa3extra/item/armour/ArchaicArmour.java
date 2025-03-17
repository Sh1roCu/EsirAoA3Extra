package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ArchaicArmour extends AdventArmour {
    public ArchaicArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:archaic", 67, new int[]{5, 9, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && DamageUtil.isMeleeDamage(event.getSource()))
            event.setAmount(event.getAmount() * (1 + 0.1875f * slots.size() * (1 - EntityUtil.getCurrentHealthPercent(plData.player()))));
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void onAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && plData.equipment().getCurrentFullArmourSet() != setType() && DamageUtil.isMeleeDamage(event.getSource()))
            event.setAmount(event.getAmount() * (1 + 0.16f * slots.size() * (1 - EntityUtil.getCurrentHealthPercent(plData.player()))));
        super.onAttackReceived(plData, slots, event);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ARCHAIC;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.archaic_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.archaic_armour.desc.2", LocaleUtil.ItemDescriptionType.HARMFUL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.archaic_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
