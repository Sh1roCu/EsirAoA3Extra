package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class SpacekingArmour extends AdventArmour {
    public SpacekingArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:spaceking", 62, new int[]{4, 8, 9, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.SPACEKING;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots == null && !DamageUtil.isEnvironmentalDamage(event.getSource()) && !DamageUtil.isPoisonDamage(event.getSource(), plData.player(), event.getAmount())) {
            Player pl = plData.player();

            if (!pl.level.isClientSide && pl.getHealth() > 0 && RandomUtil.oneInNChance(3)) {
				/*OrblingEntity orbling = new OrblingEntity(AoAEntities.Minions.ORBLING.get(), pl.level);

				orbling.setPos(pl.getX(), pl.getY() + 1.5, pl.getZ());
				orbling.tame(pl);
				pl.level.addFreshEntity(orbling);*/ // TODO
            }
        }
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        //tooltip.add(setEffectHeader());
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.spaceking_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.spaceking_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
