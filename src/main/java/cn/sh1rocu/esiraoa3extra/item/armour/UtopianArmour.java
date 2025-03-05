package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class UtopianArmour extends AdventArmour {
    public UtopianArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:utopian", 50, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 2), slot, Rarity.RARE);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.UTOPIAN;
    }

    @Override
    public void onEquip(ServerPlayerDataManager plData, @Nullable EquipmentSlotType slot) {
        if (slot == null) {
            plData.getSkills().forEach(skill -> skill.applyXpModifier(0.1f));
        } else {
            plData.getSkills().forEach(skill -> skill.applyXpModifier(0.05f));
        }
        super.onEquip(plData, slot);
    }

    @Override
    public void onUnequip(ServerPlayerDataManager plData, @Nullable EquipmentSlotType slot) {
        if (slot == null) {
            plData.getSkills().forEach(skill -> skill.removeXpModifier(0.1f));
        } else {
            plData.getSkills().forEach(skill -> skill.removeXpModifier(0.05f));
        }
        super.onUnequip(plData, slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag tooltipFlag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.utopian_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.utopian_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
