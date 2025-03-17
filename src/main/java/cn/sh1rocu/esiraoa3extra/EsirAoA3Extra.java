package cn.sh1rocu.esiraoa3extra;

import cn.sh1rocu.esiraoa3extra.client.gui.overlay.ScopeOverlayRenderer;
import cn.sh1rocu.esiraoa3extra.client.model.ModelProperties;
import cn.sh1rocu.esiraoa3extra.item.armour.AdventArmour;
import cn.sh1rocu.esiraoa3extra.item.weapon.maul.BaseMaul;
import cn.sh1rocu.esiraoa3extra.network.ChannelCheckNetwork;
import cn.sh1rocu.esiraoa3extra.registration.*;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.EntityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.aoa3.geckolib3.GeckoLib;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("esiraoa3extra")
public class EsirAoA3Extra {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public EsirAoA3Extra() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AoAWeapons.WEAPONS.register(modEventBus);
        AoAArmour.ARMOUR.register(modEventBus);
        AoABlocks.BLOCKS.register(modEventBus);
        AoABlocks.BLOCK_ITEMS.register(modEventBus);
        AoAItems.ITEMS.register(modEventBus);
        AoATileEntities.TILE_ENTITIES.register(modEventBus);
        AoAContainers.CONTAINERS.register(modEventBus);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ChannelCheckNetwork::new);
    }

    private void clientSetup(FMLClientSetupEvent ev) {
        AoABlocks.setCustomRenderTypes();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ev.enqueueWork(() -> RenderTypeLookup.setRenderLayer(AoABlocks.AMPLIFIER_TABLE.get(), RenderType.solid()));
        }
        ModelProperties.init();
        ScopeOverlayRenderer.init();
        AoATileEntities.registerRenderers();
        AoAContainers.registerContainerScreens();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onArmourChanged(LivingEquipmentChangeEvent event) {
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
    public void onPlayerDamage(LivingHurtEvent ev) {
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
}