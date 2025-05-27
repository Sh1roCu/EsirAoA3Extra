package cn.sh1rocu.esiraoa3extra.listener;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.item.armour.AdventArmour;
import cn.sh1rocu.esiraoa3extra.item.misc.BaseMagazine;
import cn.sh1rocu.esiraoa3extra.item.weapon.maul.BaseMaul;
import cn.sh1rocu.esiraoa3extra.registration.EsirAttributes;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tslat.aoa3.common.registration.custom.AoASkills;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.PlayerUtil;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = EsirAoA3Extra.MODID)
public class LivingEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onArmourChanged(LivingEquipmentChangeEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof ServerPlayer)) return;
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
            double healthAmplifier = attribute[1] + 12.5 * attribute[2];
            if (healthAmplifier > 0) {
                EntityUtil.reapplyAttributeModifier((ServerPlayer) event.getEntity(), Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString(uuid), modifierName, healthAmplifier, AttributeModifier.Operation.ADDITION), true);
                return;
            }
        }
        EntityUtil.removeAttributeModifier((ServerPlayer) event.getEntity(), Attributes.MAX_HEALTH, UUID.fromString(uuid));
    }

    @SubscribeEvent
    public static void onMaulDamage(LivingHurtEvent ev) {
        Entity attacker = ev.getSource().getEntity();
        if (DamageUtil.isMeleeDamage(ev.getSource()) && attacker instanceof LivingEntity) {
            ItemStack weapon = ((LivingEntity) attacker).getItemInHand(InteractionHand.MAIN_HAND);
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
                ev.setAmount((((BaseMaul) weapon.getItem()).getAttackDamage() + 1) * (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel)))));
            }
        }
    }

    @SubscribeEvent
    public static void onMagicDamage(LivingHurtEvent ev) {
        Entity attacker = ev.getSource().getEntity();
        if (ev.getSource().isMagic()) {
            Player player = null;
            if (attacker instanceof Player)
                player = (Player) attacker;
            else if (attacker instanceof Projectile && ((Projectile) attacker).getOwner() instanceof Player)
                player = (Player) ((Projectile) attacker).getOwner();
            if (player == null)
                return;
            AttributeInstance magicModifier = player.getAttribute(EsirAttributes.MAGIC_DAMAGE.get());
            if (magicModifier != null) {
                ev.setAmount((float) (ev.getAmount() * magicModifier.getValue()));
            }
        }
    }

    @SubscribeEvent
    public static void onAllDamageAmplify(LivingHurtEvent ev) {
        Entity attacker = ev.getSource().getEntity();
        Player player = null;
        if (attacker instanceof Player)
            player = (Player) attacker;
        else if (attacker instanceof Projectile && ((Projectile) attacker).getOwner() instanceof Player)
            player = (Player) ((Projectile) attacker).getOwner();
        if (player == null)
            return;
        int cycles = PlayerUtil.getSkill(player, AoASkills.INNERVATION.get()).getCycles();
        if (cycles > 0)
            ev.setAmount(ev.getAmount() * (1 + cycles * 0.02F));
    }

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack stack = event.getCrafting();
        if (!event.isCanceled() && stack.getItem() instanceof BaseMagazine) {
            CompoundTag nbt = stack.getOrCreateTag();
            nbt.putInt("remaining_bullets", 8);
        }
    }
}