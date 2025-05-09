package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class NecroArmour extends AdventArmour {
    public NecroArmour(EquipmentSlot slot) {
        super(ItemUtil.customArmourMaterial("aoa3:necro", 64, new int[]{5, 8, 9, 4}, 10, SoundEvents.ARMOR_EQUIP_GENERIC, 7), slot);
    }

    @Override
    public AdventArmour.Type setType() {
        return AdventArmour.Type.NECRO;
    }

    @Override
    public void onPostAttackReceived(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDamageEvent event) {
        if (slots == null && !DamageUtil.isEnvironmentalDamage(event.getSource()) && event.getAmount() > plData.player().getHealth() && plData.equipment().isCooledDown("necro_armour")) {
            Player pl = plData.player();

            event.setAmount(0);
            plData.equipment().setCooldown("necro_armour", 72000);
            pl.inventory.hurtArmor(DamageSource.GENERIC, 2000);

            if (pl.getHealth() < 4)
                pl.setHealth(4);

            ((ServerLevel) pl.level).sendParticles(ParticleTypes.HEART, pl.getX(), pl.getBoundingBox().maxY, pl.getZ(), 5, 0, 0, 0, 0);
        }
        super.onPostAttackReceived(plData, slots, event);
    }

    @Override
    public void onPlayerDeath(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlot> slots, LivingDeathEvent event) {
        if (slots != null) {
            int count = slots.size();
            int inventoryIndex = 0;
            Inventory inv = plData.player().inventory;

            while (count > 0 && inventoryIndex < inv.getContainerSize()) {
                ItemStack stack = inv.getItem(inventoryIndex);

                if (!stack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.INTERVENTION.get(), stack) == 0 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.VANISHING_CURSE, stack) == 0) {
                    plData.storeItems(stack);
                    inv.setItem(inventoryIndex, ItemStack.EMPTY);
                    count--;
                }

                inventoryIndex++;
            }
        }
        super.onPlayerDeath(plData, slots, event);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.necro_armour.desc.1", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.necro_armour.desc.2", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(pieceEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.necro_armour.desc.3", LocaleUtil.ItemDescriptionType.BENEFICIAL));
        tooltip.add(setEffectHeader());
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("item.esiraoa3extra.necro_armour.desc.4", LocaleUtil.ItemDescriptionType.BENEFICIAL));
    }
}
