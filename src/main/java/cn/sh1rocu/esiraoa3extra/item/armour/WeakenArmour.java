package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
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
    public WeakenArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:weaken", 44, new int[]{4, 6, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.WEAKEN;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingDamageEvent event) {
        if (slots == null) {
            if (RandomUtil.percentChance(0.7f) && DamageUtil.isMeleeDamage(event.getSource()) && event.getSource().getEntity() instanceof LivingEntity)
                ((LivingEntity) event.getSource().getEntity()).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1, true, true));
        } else if (plData.equipment().getCurrentFullArmourSet() != setType()) {
            if (RandomUtil.percentChance(0.175f * slots.size()) && DamageUtil.isMeleeDamage(event.getSource()) && event.getSource().getEntity() instanceof LivingEntity)
                ((LivingEntity) event.getSource().getEntity()).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 0, true, true));
        }
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.weaken_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.weaken_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
