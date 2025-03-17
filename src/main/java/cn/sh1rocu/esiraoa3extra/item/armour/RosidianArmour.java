package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class RosidianArmour extends AdventArmour {
    public RosidianArmour(EquipmentSlotType slot) {
        super(ItemUtil.customArmourMaterial("aoa3:rosidian", 55, new int[]{4, 7, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.ROSIDIAN;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots, LivingDamageEvent event) {
        if (!DamageUtil.isEnvironmentalDamage(event.getSource()) && !DamageUtil.isPoisonDamage(event.getSource(), plData.player(), event.getAmount())) {
            if (slots == null) {
                //if (event.getAmount() >= 4)
                //	spawnRosid(plData.player());
            } else {
                //if (random.nextFloat() < 0.04 * slots.size())
                //	spawnRosid(plData.player());
            }
        }
        super.onPostAttackReceived(plData, slots, event);
    }

	/*private void spawnRosid(PlayerEntity pl) {
		RosidEntity rosid = new RosidEntity(AoAEntities.Minions.ROSID.get(), pl.level);

		rosid.tame(pl);
		rosid.setPos(pl.getX(), pl.getY(), pl.getZ());
		pl.level.addFreshEntity(rosid);
	}*/ // TODO

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        //tooltip.add(pieceEffectHeader());
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.rosidian_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        //tooltip.add(setEffectHeader());
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.rosidian_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
