package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class LyonicArmour extends AdventArmour {
    public LyonicArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:lyonic", 56, new int[]{4, 7, 8, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.LYONIC;
    }

    @Override
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots) {
        if (plData.player().level.getGameTime() % 2 == 0) {
            Player pl = plData.player();
            int pulledCount = 0;

            if (slots != null) {
                float range = 1.5f * (float) slots.size();
                ItemEntity item;
                Iterator<ItemEntity> iterator = plData.player().level.getEntitiesOfClass(ItemEntity.class, new AABB(pl.getX() - range, pl.getY() - range, pl.getZ() - range, pl.getX() + range, pl.getY() + range, pl.getZ() + range)).iterator();

                while (iterator.hasNext() && pulledCount <= 200 && canPullItem(item = iterator.next())) {
                    EntityUtil.pullEntityIn(pl, item, 0.05f, true);
                    pulledCount++;
                }
            } else {
                Iterator<ExperienceOrb> iterator = plData.player().level.getEntitiesOfClass(ExperienceOrb.class, new AABB(pl.getX() - 6, pl.getY() - 6, pl.getZ() - 6, pl.getX() + 6, pl.getY() + 6, pl.getZ() + 6)).iterator();

                while (iterator.hasNext() && pulledCount <= 200) {
                    EntityUtil.pullEntityIn(pl, iterator.next(), 0.05f, true);
                    pulledCount++;
                }
            }
        }
        super.onEffectTick(plData, slots);
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        super.onDamageDealt(plData, slots, event);
    }

    private boolean canPullItem(ItemEntity item) {
        return item.isAlive() && !item.getItem().isEmpty() && !item.hasPickUpDelay();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lyonic_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lyonic_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.lyonic_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
