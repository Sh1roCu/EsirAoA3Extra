package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class CommanderArmour extends AdventArmour {
    public CommanderArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:commander", 62, new int[]{4, 9, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.COMMANDER;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots) {
        if (slots == null || plData.equipment().getCurrentFullArmourSet() != setType() && plData.player().level.getGameTime() % 20 == 0) {
            for (LivingEntity entity : plData.player().level.getEntitiesOfClass(LivingEntity.class, plData.player().getBoundingBox().inflate(2 * (slots == null ? 4 : slots.size())))) {
                if (entity != plData.player() && (entity instanceof PlayerEntity || (entity instanceof TameableEntity && ((TameableEntity) entity).getOwner() == plData.player())))
                    entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 25, slots == null ? 1 : 0, false, true));
            }
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.commander_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.commander_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.commander_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
