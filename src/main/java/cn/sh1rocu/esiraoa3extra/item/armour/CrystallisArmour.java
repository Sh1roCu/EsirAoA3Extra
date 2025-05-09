package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class CrystallisArmour extends AdventArmour {
    public CrystallisArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:crystallis", 56, new int[]{5, 6, 10, 3}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.CRYSTALLIS;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            if (slots == null && DamageUtil.isMeleeDamage(event.getSource()) || DamageUtil.isRangedDamage(event.getSource(), plData.player(), event.getAmount())) {
                event.getSource().getEntity().hurt(DamageSource.thorns(plData.player()), event.getAmount());
                plData.player().inventory.hurtArmor(DamageSource.GENERIC, event.getAmount() * 2);
            } else if (slots != null && plData.equipment().getCurrentFullArmourSet() != setType() && DamageUtil.isMeleeDamage(event.getSource())) {
                event.getSource().getEntity().hurt(DamageSource.thorns(plData.player()), event.getAmount() * slots.size() / 4f);
                plData.player().inventory.hurtArmor(DamageSource.GENERIC, event.getAmount() * 2);
            }
        }
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.crystallis_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.crystallis_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.crystallis_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
