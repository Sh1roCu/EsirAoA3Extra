package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SubterraneanArmour extends AdventArmour {
    private static final AttributeModifier ATTACK_SPEED_DEBUFF = new AttributeModifier(UUID.fromString("d4631555-8ceb-490d-9066-fb4188560b15"), "AoASubterraneanAttackSpeedDebuff", -0.16666667, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public SubterraneanArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:subterranean", 47, new int[]{3, 7, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.SUBTERRANEAN;
    }

    @Override
    public void onEquip(ServerPlayerDataManager plData, @Nullable EquipmentSlot slot) {
        if (slot == null)
            EntityUtil.applyAttributeModifierSafely(plData.player(), Attributes.ATTACK_SPEED, ATTACK_SPEED_DEBUFF, false);
        super.onEquip(plData, slot);
    }

    @Override
    public void onUnequip(ServerPlayerDataManager plData, @Nullable EquipmentSlot slot) {
        if (slot == null)
            EntityUtil.removeAttributeModifier(plData.player(), Attributes.ATTACK_SPEED, ATTACK_SPEED_DEBUFF);
        super.onUnequip(plData, slot);
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (slots == null)
            plData.player().addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, true, false));
        super.onEffectTick(plData, slots);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.subterranean_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
