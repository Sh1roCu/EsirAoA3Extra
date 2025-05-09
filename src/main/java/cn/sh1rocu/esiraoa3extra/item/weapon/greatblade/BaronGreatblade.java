package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.common.registration.AoAWeapons;
import net.tslat.aoa3.content.entity.projectile.thrown.GrenadeEntity;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BaronGreatblade extends BaseGreatblade {
    public BaronGreatblade() {
        super(19.5f, AttackSpeed.GREATBLADE, 1200);
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (!attacker.level.isClientSide && attackCooldown > 0.85f) {
            if (!(attacker instanceof Player) || ((Player) attacker).isCreative() || ItemUtil.findInventoryItem((Player) attacker, new ItemStack(AoAWeapons.GRENADE.get()), true, 1)) {
                attacker.level.addFreshEntity(new GrenadeEntity(attacker, null));
                ItemUtil.damageItem(stack, attacker, 1, EquipmentSlot.MAINHAND);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
