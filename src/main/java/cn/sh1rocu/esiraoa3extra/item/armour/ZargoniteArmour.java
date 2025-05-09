package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ZargoniteArmour extends AdventArmour {
    public ZargoniteArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:zargonite", 64, new int[]{5, 8, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ZARGONITE;
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        if (slots != null && DamageUtil.isMagicDamage(event.getSource(), event.getEntityLiving(), event.getAmount()))
            event.setAmount((float) (event.getAmount() * (1 + (0.1 * slots.size()))));
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.zargonite_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
