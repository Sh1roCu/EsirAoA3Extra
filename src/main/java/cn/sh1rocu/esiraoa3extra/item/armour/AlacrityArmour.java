package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class AlacrityArmour extends AdventArmour {
    public AlacrityArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:alacrity", 55, new int[]{4, 8, 9, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ALACRITY;
    }

    @Override
    public void onPlayerLandingFall(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingFallEvent event) {
        if (slots == null) {
            event.setCanceled(true);
        } else if (plData.equipment().getCurrentFullArmourSet() != setType()) {
            event.setDamageMultiplier(1 - (slots.size() * 0.2f));
        }
        super.onPlayerLandingFall(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.alacrity_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.alacrity_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
