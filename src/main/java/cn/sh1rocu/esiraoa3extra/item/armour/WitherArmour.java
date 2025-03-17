package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class WitherArmour extends AdventArmour {
    public WitherArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:wither", 53, new int[]{4, 8, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.WITHER;
    }

    @Override
    public void onPreAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingAttackEvent event) {
        if (slots == null && event.getSource() == DamageSource.WITHER) {
            event.setCanceled(true);
            plData.player().addEffect(new EffectBuilder(Effects.DAMAGE_RESISTANCE, 60).isAmbient().hideEffectIcon().build());
        }
        super.onPreAttackReceived(plData, slots, event);
    }

    @Override
    public void onAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && event.getSource() == DamageSource.WITHER)
            event.setAmount(event.getAmount() * (1 - (slots.size() * 0.25f)));
        super.onAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.wither_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.wither_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.wither_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
