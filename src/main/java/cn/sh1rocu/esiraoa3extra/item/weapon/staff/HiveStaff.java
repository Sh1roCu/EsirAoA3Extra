package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class HiveStaff extends BaseStaff<Object> {
    public HiveStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_SHADOW_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.ENERGY_RUNE.get(), 10);
        runes.put(AoAItems.LIFE_RUNE.get(), 2);
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, Object args) {
		/*HiveSoldierEntity hiveSoldier = new HiveSoldierEntity(AoAEntities.Minions.HIVE_SOLDIER.get(), caster.level);

		if (caster instanceof Player)
			hiveSoldier.tame((Player)caster);

		hiveSoldier.setPos(caster.getX(), caster.getY(), caster.getZ());
		caster.level.addFreshEntity(hiveSoldier);*/ // TODO
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
