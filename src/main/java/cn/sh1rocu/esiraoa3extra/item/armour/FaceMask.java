package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class FaceMask extends AdventArmour {
    public FaceMask() {
        super(ItemUtil.customArmourMaterial("aoa3:face_mask", 36, new int[]{4, 7, 8, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), EquipmentSlotType.HEAD);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ALL;
    }

    @Override
    public boolean isHelmetAirTight(ServerPlayerEntity player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.helmet.airTight", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(anySetEffectHeader());
    }
}
