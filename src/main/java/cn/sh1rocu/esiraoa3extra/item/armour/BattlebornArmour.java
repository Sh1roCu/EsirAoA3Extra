package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class BattlebornArmour extends AdventArmour {
    private static final UUID BATTLEBORN_ARMOUR_BUFF = UUID.fromString("5cf50cfa-4298-46d1-b7ec-c648f8e8d5c9");

    public BattlebornArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:battleborn", 65, new int[]{4, 8, 9, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.BATTLEBORN;
    }

    private AttributeModifier buff(double currentValue) {
        return new AttributeModifier(BATTLEBORN_ARMOUR_BUFF, "AoABattlebornArmour", currentValue, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        if (slots != null && DamageUtil.isMeleeDamage(event.getSource())) {
            int counter = plData.equipment().getCooldown("battleborn");
            int newAmount = Math.min(300, counter + slots.size() * 6);

            plData.equipment().setCooldown("battleborn", newAmount);
            EntityUtil.removeAttributeModifier(plData.player(), Attributes.ATTACK_SPEED, BATTLEBORN_ARMOUR_BUFF);
            EntityUtil.applyAttributeModifierSafely(plData.player(), Attributes.ATTACK_SPEED, buff(Math.min(0.65, newAmount / 240d)), false);
        }
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        int counter = plData.equipment().getCooldown("battleborn");

        if (counter == 1) {
            EntityUtil.removeAttributeModifier(plData.player(), Attributes.ATTACK_SPEED, BATTLEBORN_ARMOUR_BUFF);
        } else if (counter > 0 && plData.player().level.getGameTime() % 10 == 0) {
            EntityUtil.removeAttributeModifier(plData.player(), Attributes.ATTACK_SPEED, BATTLEBORN_ARMOUR_BUFF);
            EntityUtil.applyAttributeModifierSafely(plData.player(), Attributes.ATTACK_SPEED, buff(Math.min(0.4, counter / 240d)), false);
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void onUnequip(ServerPlayerDataManager plData, @Nullable EquipmentSlot slot) {
        if (slot != null) {
            int cooldown = plData.equipment().getCooldown("battleborn");

            if (cooldown > 0) {
                EntityUtil.removeAttributeModifier(plData.player(), Attributes.ATTACK_SPEED, BATTLEBORN_ARMOUR_BUFF);
                plData.equipment().setCooldown("battleborn", (int) (cooldown * 0.75f));
            }
        }
        super.onUnequip(plData, slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.battleborn_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.battleborn_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
