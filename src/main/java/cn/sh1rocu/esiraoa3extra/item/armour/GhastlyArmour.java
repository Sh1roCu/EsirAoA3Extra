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
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class GhastlyArmour extends AdventArmour {
    public GhastlyArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:ghastly", 62, new int[]{5, 8, 8, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.GHASTLY;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (slots != null && plData.player().level.getGameTime() % 5 == 0 && plData.player().isShiftKeyDown()) {
            for (LivingEntity entity : plData.player().level.getEntitiesOfClass(LivingEntity.class, plData.player().getBoundingBox().inflate(4 * slots.size()), EntityUtil.Predicates.HOSTILE_MOB)) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 6, 0, true, false));
            }
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.ghastly_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.ghastly_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
