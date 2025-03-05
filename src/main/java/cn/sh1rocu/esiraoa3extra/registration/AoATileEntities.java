package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.block.render.AmplifierTableTileEntityRender;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AoATileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "esiraoa3extra");
    public static final RegistryObject<TileEntityType<AmplifierTableTileEntity>> AMPLIFIER_TABLE = registerTileEntity("amplifier_table", () -> TileEntityType.Builder.of(AmplifierTableTileEntity::new, new Block[]{AoABlocks.AMPLIFIER_TABLE.get()}).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(AMPLIFIER_TABLE.get(), AmplifierTableTileEntityRender::new);
    }

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerTileEntity(String registryName, Supplier<TileEntityType<T>> tileEntity) {
        return TILE_ENTITIES.register(registryName, tileEntity);
    }
}

