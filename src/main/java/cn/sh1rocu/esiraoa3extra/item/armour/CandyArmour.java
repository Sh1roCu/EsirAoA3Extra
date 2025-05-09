package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class CandyArmour extends AdventArmour {
    public CandyArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:candy", 59, new int[]{4, 7, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.CANDY;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (plData.player().getFoodData().needsFood()) {
            if (slots == null || plData.equipment().isCooledDown("candy_armour")) {
                if (findAndConsumeFood(plData.player()))
                    plData.equipment().setCooldown("candy_armour", 12000 / (slots == null ? 4 : slots.size()));
            }
        }
        super.onEffectTick(plData, slots);
    }

    private boolean findAndConsumeFood(Player player) {
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack stack = player.inventory.getItem(i);

            if (stack.getItem().isEdible()) {
                FoodProperties food = stack.getItem().getFoodProperties();

                if (food != null && food.getNutrition() > 0 && food.getSaturationModifier() > 0) {
                    player.inventory.setItem(i, stack.getItem().finishUsingItem(stack, player.level, player));

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.candy_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.candy_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.candy_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
