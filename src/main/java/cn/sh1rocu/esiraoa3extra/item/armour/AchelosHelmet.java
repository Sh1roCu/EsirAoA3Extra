package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class AchelosHelmet extends AdventArmour {

    public AchelosHelmet() {
        super(ItemUtil.customArmourMaterial("aoa3:achelos", 60, new int[]{5, 7, 9, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), EquipmentSlot.HEAD);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ALL;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        plData.player().addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, -1, 1, true, false));
        plData.player().addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, -1, 0, true, false));
        super.onEffectTick(plData, slots);
    }

    @Override
    public boolean isHelmetAirTight(ServerPlayer player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.helmet.airTight", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 2));
        tooltip.add(anySetEffectHeader());
    }
}
