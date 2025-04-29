package cn.sh1rocu.esiraoa3extra.listener;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.item.armour.AdventArmour;
import cn.sh1rocu.esiraoa3extra.item.weapon.maul.BaseMaul;
import cn.sh1rocu.esiraoa3extra.registration.EsirAttributes;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = EsirAoA3Extra.MODID)
public class LivingEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onArmourChanged(LivingEquipmentChangeEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof ServerPlayerEntity)) return;
        String uuid = "";
        String modifierName = "";
        switch (event.getSlot()) {
            case HEAD:
                uuid = AdventArmour.HELMET_MODIFIER_UUID;
                modifierName = "EsirHelmetBuff";
                break;
            case CHEST:
                uuid = AdventArmour.CHESTPLATE_MODIFIER_UUID;
                modifierName = "EsirChestplateBuff";
                break;
            case LEGS:
                uuid = AdventArmour.LEGS_MODIFIER_UUID;
                modifierName = "EsirLegsBuff";
                break;
            case FEET:
                uuid = AdventArmour.BOOTS_MODIFIER_UUID;
                modifierName = "EsirBootsBuff";
                break;
        }
        if (uuid.isEmpty()) return;
        ItemStack stack = event.getTo();
        float[] attribute = EsirUtil.getAttribute(stack);
        if (EsirUtil.isEsirArmourOrWeapon(stack) && attribute[0] != -1) {
            double healthAmplifier = attribute[1] + attribute[2] * 10;
            if (healthAmplifier > 0) {
                EntityUtil.reapplyAttributeModifier((ServerPlayerEntity) event.getEntity(), Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString(uuid), modifierName, healthAmplifier, AttributeModifier.Operation.ADDITION), true);
                return;
            }
        }
        EntityUtil.removeAttributeModifier((ServerPlayerEntity) event.getEntity(), Attributes.MAX_HEALTH, UUID.fromString(uuid));
    }

    @SubscribeEvent
    public static void onMaulDamage(LivingHurtEvent ev) {
        Entity attacker = ev.getSource().getEntity();
        if (DamageUtil.isMeleeDamage(ev.getSource()) && attacker instanceof LivingEntity) {
            ItemStack weapon = ((LivingEntity) attacker).getItemInHand(Hand.MAIN_HAND);
            if (weapon.getItem() instanceof BaseMaul) {
                float extraDmg = 0;
                int amplifierLevel = 0;
                int starLevel = 0;
                float[] attribute = EsirUtil.getAttribute(weapon);
                if (attribute[0] != -1) {
                    extraDmg = attribute[0];
                    amplifierLevel = (int) attribute[1];
                    starLevel = (int) attribute[2];
                }
                ev.setAmount((((BaseMaul) weapon.getItem()).getAttackDamage() + 1) * (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel)))));
            }
        }
    }

    @SubscribeEvent
    public static void onMagicDamage(LivingHurtEvent ev) {
        Entity attacker = ev.getSource().getEntity();
        if (ev.getSource().isMagic()) {
            PlayerEntity player = null;
            if (attacker instanceof PlayerEntity)
                player = (PlayerEntity) attacker;
            else if (attacker instanceof ProjectileEntity && ((ProjectileEntity) attacker).getOwner() instanceof PlayerEntity)
                player = (PlayerEntity) ((ProjectileEntity) attacker).getOwner();
            if (player == null)
                return;
            ModifiableAttributeInstance magicModifier = player.getAttribute(EsirAttributes.MAGIC_DAMAGE.get());
            if (magicModifier != null) {
                ev.setAmount((float) (ev.getAmount() * magicModifier.getValue()));
            }
        }
    }
}
