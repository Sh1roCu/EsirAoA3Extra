package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class OmniArmour extends AdventArmour {
    public OmniArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:omni", 50, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.OMNI;
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && event.getSource().isExplosion())
            event.setAmount(event.getAmount() * (1 + (0.1f * slots.size())));
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingDamageEvent event) {
        if (slots == null && DamageUtil.isMeleeDamage(event.getSource()))
            WorldUtil.createExplosion(plData.player(), plData.player().level, plData.player().blockPosition(), 1.75f);
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.omni_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.omni_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
