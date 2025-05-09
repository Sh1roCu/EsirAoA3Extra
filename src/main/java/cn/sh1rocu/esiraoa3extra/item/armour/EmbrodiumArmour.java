package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.common.registration.custom.AoAResources;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class EmbrodiumArmour extends AdventArmour {
    public EmbrodiumArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:embrodium", 45, new int[]{4, 7, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.EMBRODIUM;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (slots == null) {
            plData.getResource(AoAResources.SPIRIT.get()).addValue(0.08f);
        } else {
            Player pl = plData.player();
            float temp = WorldUtil.getAmbientTemperature(pl.level, pl.blockPosition());

            if (temp > 0.8f)
                plData.getResource(AoAResources.SPIRIT.get()).addValue(0.08f * slots.size() * Math.min(1f, (temp / 2f)));
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.embrodium_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.embrodium_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}