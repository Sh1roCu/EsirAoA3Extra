package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.client.gui.container.AmplifierTableScreen;
import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class AoAContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS;
    public static final RegistryObject<ContainerType<AmplifierTableContainer>> AMPLIFIER_TABLE;

    public AoAContainers() {
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> registerContainer(String id, IContainerFactory<T> factory) {
        return CONTAINERS.register(id, () -> IForgeContainerType.create(factory));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerContainerScreens() {
        ScreenManager.register(AMPLIFIER_TABLE.get(), AmplifierTableScreen::new);
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, "esiraoa3extra");
        AMPLIFIER_TABLE = registerContainer("amplifier_table", (screenId, inventory, buffer) -> new AmplifierTableContainer(screenId, inventory, IWorldPosCallable.create(inventory.player.level, buffer.readBlockPos())));
    }
}
