package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.client.gui.container.AmplifierTableScreen;
import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class AoAContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS;
    public static final RegistryObject<MenuType<AmplifierTableContainer>> AMPLIFIER_TABLE;

    public AoAContainers() {
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerContainer(String id, IContainerFactory<T> factory) {
        return CONTAINERS.register(id, () -> IForgeContainerType.create(factory));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerAbstractContainerScreens() {
        MenuScreens.register(AMPLIFIER_TABLE.get(), AmplifierTableScreen::new);
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EsirAoA3Extra.MODID);
        AMPLIFIER_TABLE = registerContainer("amplifier_table", (screenId, inventory, buffer) -> new AmplifierTableContainer(screenId, inventory, ContainerLevelAccess.create(inventory.player.level, buffer.readBlockPos())));
    }
}
