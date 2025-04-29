package cn.sh1rocu.esiraoa3extra;

import cn.sh1rocu.esiraoa3extra.client.gui.overlay.ScopeOverlayRenderer;
import cn.sh1rocu.esiraoa3extra.client.model.ModelProperties;
import cn.sh1rocu.esiraoa3extra.network.ChannelCheckNetwork;
import cn.sh1rocu.esiraoa3extra.registration.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.aoa3.geckolib3.GeckoLib;

@Mod.EventBusSubscriber(modid = EsirAoA3Extra.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(EsirAoA3Extra.MODID)
public class EsirAoA3Extra {

    public static final String MODID = "esiraoa3extra";
    private static final Logger LOGGER = LogManager.getLogger();

    public EsirAoA3Extra() {
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EsirAttributes.ATTRIBUTES.register(modEventBus);
        AoAWeapons.WEAPONS.register(modEventBus);
        AoAArmour.ARMOUR.register(modEventBus);
        AoABlocks.BLOCKS.register(modEventBus);
        AoABlocks.BLOCK_ITEMS.register(modEventBus);
        AoAItems.ITEMS.register(modEventBus);
        AoATileEntities.TILE_ENTITIES.register(modEventBus);
        AoAContainers.CONTAINERS.register(modEventBus);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ChannelCheckNetwork::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent ev) {
        AoABlocks.setCustomRenderTypes();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ev.enqueueWork(() -> RenderTypeLookup.setRenderLayer(AoABlocks.AMPLIFIER_TABLE.get(), RenderType.solid()));
        }
        ModelProperties.init();
        ScopeOverlayRenderer.init();
        AoATileEntities.registerRenderers();
        AoAContainers.registerContainerScreens();
    }
}