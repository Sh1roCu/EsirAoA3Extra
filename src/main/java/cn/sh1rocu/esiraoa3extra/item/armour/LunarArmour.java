package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class LunarArmour extends AdventArmour {
    public LunarArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:lunar", 63, new int[]{4, 7, 10, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.LUNAR;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots) {
        if (slots != null)
            plData.player().addEffect(new EffectInstance(Effects.JUMP, -1, slots.size() - 1, true, false));
        super.onEffectTick(plData, slots);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lunar_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lunar_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
