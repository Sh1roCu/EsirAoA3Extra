package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public abstract class BaseDivineBlessingTalisman extends Item {
    public BaseDivineBlessingTalisman() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack talisman = player.getItemInHand(hand);
        if (player instanceof ServerPlayer && hand == InteractionHand.MAIN_HAND) {
            ServerPlayer pl = (ServerPlayer) player;
            ItemStack offhand = pl.getItemInHand(InteractionHand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                CompoundTag CompoundTag = offhand.getTag() != null ? offhand.getTag() : new CompoundTag();
                if (!CompoundTag.contains("amplifierProtection")) {
                    Type type = getType();
                    if (offhand.getItem() instanceof ArmorItem) {
                        if (type == Type.WEAPON) {
                            pl.sendMessage(new TextComponent("武器神恩符不能保护防具").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                            return InteractionResultHolder.fail(talisman);
                        }
                    } else if (type == Type.ARMOUR) {
                        pl.sendMessage(new TextComponent("防具神恩符不能保护武器").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                        return InteractionResultHolder.fail(talisman);
                    }
                    CompoundTag.putBoolean("amplifierProtection", true);
                    offhand.setTag(CompoundTag);
                    pl.setItemSlot(EquipmentSlot.OFFHAND, offhand);
                    pl.sendMessage(new TextComponent("已为该装备附加神恩符的保护，下次增幅失败时将抵消一次失败惩罚").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), Util.NIL_UUID);
                    talisman.shrink(1);
                    player.inventoryMenu.broadcastChanges();
                } else {
                    pl.sendMessage(new TextComponent("该装备已存在神恩符的保护，无法再次附加").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), Util.NIL_UUID);
                }
            }
            return InteractionResultHolder.success(talisman);
        } else
            return InteractionResultHolder.fail(talisman);
    }

    public abstract Type getType();

    public enum Type {
        WEAPON,
        ARMOUR
    }
}
