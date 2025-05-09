package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.block.AmplifierTable;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public final class AoABlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EsirAoA3Extra.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EsirAoA3Extra.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, EsirAoA3Extra.MODID);
    public static final HashMap<RegistryObject<? extends Block>, CustomRenderType> CUSTOM_RENDER_TYPES = new HashMap<>();
    public static final RegistryObject<Block> AMPLIFIER_TABLE = registerBlock("amplifier_table", AmplifierTable::new, AoAItemGroups.ESIRAOA3ITEMS, Rarity.EPIC);

    public AoABlocks() {
    }

    private static <T extends Block> RegistryObject<T> registerItemlessBlock(String registryName, Supplier<T> supplier) {
        return BLOCKS.register(registryName, supplier);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> supplier, CreativeModeTab CreativeModeTab) {
        return registerBlock(registryName, supplier, CreativeModeTab, Rarity.COMMON, -1);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> supplier, CreativeModeTab CreativeModeTab, int furnaceBurnTime) {
        return registerBlock(registryName, supplier, CreativeModeTab, Rarity.COMMON, furnaceBurnTime);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> supplier, CreativeModeTab CreativeModeTab, Rarity rarity) {
        return registerBlock(registryName, supplier, CreativeModeTab, rarity, -1);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> supplier, CreativeModeTab CreativeModeTab, Rarity rarity, int furnaceBurnTime) {
        RegistryObject<T> block = BLOCKS.register(registryName, supplier);
        RegistryObject<Item> blockItem = BLOCK_ITEMS.register(registryName, furnaceBurnTime > 0 ? () -> new BlockItem(block.get(), (new Item.Properties()).tab(CreativeModeTab).rarity(rarity)) {
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return furnaceBurnTime;
            }
        } : () -> new BlockItem(block.get(), (new Item.Properties()).tab(CreativeModeTab).rarity(rarity)));
        return block;
    }

    public static <T extends Block> RegistryObject<T> customRender(RegistryObject<T> object, CustomRenderType renderType) {
        if (FMLEnvironment.dist == Dist.CLIENT)
            CUSTOM_RENDER_TYPES.put(object, renderType);

        return object;
    }

    private static Block.Properties properties(Material material, MaterialColor mapColour) {
        return Block.Properties.of(material, mapColour);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setCustomRenderTypes() {
        for (Map.Entry<RegistryObject<? extends Block>, CustomRenderType> entry : CUSTOM_RENDER_TYPES.entrySet()) {
            Block bl = entry.getKey().get();

            switch (entry.getValue()) {
                case CUTOUT:
                    ItemBlockRenderTypes.setRenderLayer(bl, RenderType.cutout());
                    break;
                case CUTOUT_MIPPED:
                    ItemBlockRenderTypes.setRenderLayer(bl, RenderType.cutoutMipped());
                    break;
                case TRANSLUCENT:
                    ItemBlockRenderTypes.setRenderLayer(bl, RenderType.translucent());
                    break;
            }

            if (bl instanceof LiquidBlock) {
                ItemBlockRenderTypes.setRenderLayer(((LiquidBlock) bl).getFluid().getFlowing(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(((LiquidBlock) bl).getFluid().getSource(), RenderType.translucent());
            }
        }

        CUSTOM_RENDER_TYPES.clear();
    }

    public enum CustomRenderType {
        CUTOUT,
        CUTOUT_MIPPED,
        TRANSLUCENT
    }
}
