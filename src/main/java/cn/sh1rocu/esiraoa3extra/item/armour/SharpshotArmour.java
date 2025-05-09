package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.tslat.aoa3.content.item.weapon.gun.BaseGun;
import net.tslat.aoa3.content.item.weapon.sniper.BaseSniper;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class SharpshotArmour extends AdventArmour {
    public SharpshotArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:sharpshot", 54, new int[]{4, 6, 9, 5}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 5), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.SHARPSHOT;
    }

    @Override
    public void onDamageDealt(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingHurtEvent event) {
        Item gun;

        if (DamageUtil.isGunDamage(event.getSource()) && ((gun = plData.player().getMainHandItem().getItem()) instanceof BaseGun || (gun = plData.player().getOffhandItem().getItem()) instanceof BaseGun)) {
            if (slots == null) {
                if (gun instanceof BaseSniper)
                    event.setAmount(event.getAmount() * 1.1f);
            } else {
                event.setAmount(event.getAmount() * (1 + (0.07f * slots.size())));
            }
        }
        super.onDamageDealt(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.sharpshot_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.sharpshot_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
