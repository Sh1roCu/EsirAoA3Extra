package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.block.render.AmplifierTableTileEntityRender;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AoATileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, EsirAoA3Extra.MODID);
    public static final RegistryObject<BlockEntityType<AmplifierTableTileEntity>> AMPLIFIER_TABLE = registerTileEntity("amplifier_table", () -> BlockEntityType.Builder.of(AmplifierTableTileEntity::new, new Block[]{AoABlocks.AMPLIFIER_TABLE.get()}).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(AMPLIFIER_TABLE.get(), AmplifierTableTileEntityRender::new);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerTileEntity(String registryName, Supplier<BlockEntityType<T>> TileEntity) {
        return TILE_ENTITIES.register(registryName, TileEntity);
    }
}

