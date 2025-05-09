package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class WeakenArmour extends AdventArmour {
    public WeakenArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:weaken", 44, new int[]{4, 6, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.WEAKEN;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots == null) {
            if (RandomUtil.percentChance(0.7f) && DamageUtil.isMeleeDamage(event.getSource()) && event.getSource().getEntity() instanceof LivingEntity)
                ((LivingEntity) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, true, true));
        } else if (plData.equipment().getCurrentFullArmourSet() != setType()) {
            if (RandomUtil.percentChance(0.175f * slots.size()) && DamageUtil.isMeleeDamage(event.getSource()) && event.getSource().getEntity() instanceof LivingEntity)
                ((LivingEntity) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, true));
        }
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.weaken_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.weaken_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
