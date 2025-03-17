package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ExplosiveArmour extends AdventArmour {
    public ExplosiveArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:explosive", 48, new int[]{4, 7, 9, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public void onPreAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingAttackEvent event) {
        if (slots == null && event.getSource().isExplosion())
            event.setCanceled(true);
        super.onPreAttackReceived(plData, slots, event);
    }

    @Override
    public void onAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && event.getSource().isExplosion())
            event.setAmount(event.getAmount() * (1 - 0.15f * slots.size()));
        super.onAttackReceived(plData, slots, event);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.EXPLOSIVE;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.explosive_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.explosive_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
