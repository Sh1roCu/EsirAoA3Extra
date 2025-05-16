package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseMagazine extends Item {
    public BaseMagazine() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            ItemStack magazine = player.getItemInHand(usedHand);
            if (player.isShiftKeyDown()) {
                Inventory inv = player.inventory;
                int extraCount = 0;
                for (int i = 0; i < inv.getContainerSize(); i++) {
                    ItemStack check = inv.getItem(i);
                    if (check.isEmpty()) continue;
                    if (check.getItem().getRegistryName().getPath().equals(getBulletName(getType()).replace("item.aoa3.", ""))) {
                        extraCount += check.getCount();
                        inv.removeItem(check);
                    }
                }
                CompoundTag nbt = magazine.getOrCreateTag();
                nbt.putInt("remaining_bullets", nbt.getInt("remaining_bullets") + extraCount);
                player.inventoryMenu.broadcastChanges();
                player.sendMessage(new TextComponent("装载了")
                        .append(new TextComponent(extraCount + "发")).withStyle(ChatFormatting.GOLD)
                        .append(new TranslatableComponent(getBulletName(getType())).withStyle(ChatFormatting.GREEN))
                        .append(new TextComponent("，现在该弹匣内剩余" + nbt.getInt("remaining_bullets") + "发").withStyle(ChatFormatting.GOLD)), Util.NIL_UUID);
            }
        }
        return super.use(level, player, usedHand);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        String bulletName = getBulletName(getType());
        Component bullet = bulletName != null ? new TranslatableComponent(bulletName).withStyle(ChatFormatting.GOLD) : new TextComponent("无").withStyle(ChatFormatting.LIGHT_PURPLE);
        int count = stack.getOrCreateTag().getInt("remaining_bullets");
        tooltips.add(new TextComponent("弹药类型：").withStyle(ChatFormatting.GREEN).append(bullet));
        tooltips.add(new TextComponent("剩余弹药量：").withStyle(ChatFormatting.AQUA).append(String.valueOf(count)));
    }

    public String getBulletName(Type type) {
        switch (type) {
            case CANNONBALL:
                return "item.aoa3.cannonball";
            case LIMONITE_BULLET:
                return "item.aoa3.limonite_bullet";
            case METAL_SLUG:
                return "item.aoa3.metal_slug";
            case SPREADSHOT:
                return "item.aoa3.spreadshot";
        }
        return null;
    }

    public abstract Type getType();

    public enum Type {
        CANNONBALL,
        LIMONITE_BULLET,
        METAL_SLUG,
        SPREADSHOT
    }
}