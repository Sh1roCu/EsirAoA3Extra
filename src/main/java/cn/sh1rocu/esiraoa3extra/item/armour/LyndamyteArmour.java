package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class LyndamyteArmour extends AdventArmour {
    public LyndamyteArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:lyndamyte", 35, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 2), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.LYNDAMYTE;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots != null && !plData.player().level.isClientSide && DamageUtil.isMeleeDamage(event.getSource()) && random.nextFloat() < 0.25f * slots.size())
            event.getSource().getEntity().setSecondsOnFire(5);
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        if (slots == null && DamageUtil.isMeleeDamage(event.getSource()))
            event.getEntityLiving().setSecondsOnFire(5);
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lyndamyte_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lyndamyte_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
