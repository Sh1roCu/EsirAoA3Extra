package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ExoplateArmour extends AdventArmour {
    public ExoplateArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:exoplate", 46, new int[]{4, 6, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 3), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.EXOPLATE;
    }

    @Override
    public void onAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
        if (slots != null && !DamageUtil.isEnvironmentalDamage(event.getSource())) {
            LivingEntity entity = event.getEntityLiving();
            BlockPos playerPos = entity.blockPosition();
            int lightLvl = MathHelper.clamp(2 + WorldUtil.getLightLevel((ServerWorld) entity.level, playerPos, false, false), 2, 15);

            event.setAmount(event.getAmount() * (1 - (1 - (lightLvl / 15f)) * 0.0625f * slots.size()));
        }
        super.onAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.exoplate_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
