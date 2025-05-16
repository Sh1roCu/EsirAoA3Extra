package cn.sh1rocu.esiraoa3extra.registration;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.Supplier;

public class AoAItemGroups {
    public static final ArrayList<AoACreativeModeTab> REGISTERED_ITEM_GROUPS = new ArrayList<>();
    public static final CreativeModeTab ESIRAOA3ITEMS = new AoACreativeModeTab("Esir-AoA All Items", "all", () -> new ItemStack(AoABlocks.AMPLIFIER_TABLE.get()));

    public AoAItemGroups() {
    }

    public static class AoACreativeModeTab extends CreativeModeTab {
        private final String localeName;
        private final Supplier<ItemStack> displayStack;

        public AoACreativeModeTab(String name, String id, Supplier<ItemStack> iconStack) {
            super("esiraoa3extra." + id);
            this.localeName = name;
            this.displayStack = iconStack;
            AoAItemGroups.REGISTERED_ITEM_GROUPS.add(this);
        }

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return this.displayStack.get();
        }

        public String getLocaleName() {
            return this.localeName;
        }
    }
}
