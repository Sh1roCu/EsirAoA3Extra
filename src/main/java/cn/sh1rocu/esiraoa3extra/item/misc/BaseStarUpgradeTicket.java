package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
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

public abstract class BaseStarUpgradeTicket extends Item {
    public BaseStarUpgradeTicket() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack ticket = player.getItemInHand(hand);
        if (player instanceof ServerPlayer && hand == InteractionHand.MAIN_HAND) {
            ServerPlayer pl = (ServerPlayer) player;
            ItemStack offhand = pl.getItemInHand(InteractionHand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                Type type = getType();
                if (offhand.getItem() instanceof ArmorItem) {
                    if (type == BaseStarUpgradeTicket.Type.WEAPON) {
                        pl.sendMessage(new TextComponent("武器升星券不能为防具升星").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                        return InteractionResultHolder.fail(ticket);
                    }
                } else if (type == BaseStarUpgradeTicket.Type.ARMOUR) {
                    pl.sendMessage(new TextComponent("防具升星券不能为武器升星").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                    return InteractionResultHolder.fail(ticket);
                }
                float[] attribute = EsirUtil.getAttribute(offhand);
                if (attribute[0] == -1) {
                    pl.sendMessage(new TextComponent("该装备处于损毁状态，无法升星").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                    return InteractionResultHolder.fail(ticket);
                }
                if ((int) attribute[1] < 10) {
                    pl.sendMessage(new TextComponent("该装备未增幅至10级，无法升星").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), Util.NIL_UUID);
                    return InteractionResultHolder.fail(ticket);
                }
                if (!EsirUtil.canUpgrade((int) attribute[2], ticket)) {
                    pl.sendMessage(new TextComponent("该装备目前的星级为" + (int) attribute[2] + "星，无法使用手上的升星券升星").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), Util.NIL_UUID);
                    return InteractionResultHolder.fail(ticket);
                }
                pl.setItemSlot(EquipmentSlot.OFFHAND, EsirUtil.upgradeEquip(pl, offhand, (int) attribute[1] - 10, (int) attribute[2] + 1));
                ticket.shrink(1);
                pl.inventoryMenu.broadcastChanges();
                pl.sendMessage(new TextComponent("升星完成，该装备目前的星级为" + ((int) attribute[2] + 1) + "，增幅等级为" + ((int) attribute[1] - 10)).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), Util.NIL_UUID);
            }
            return InteractionResultHolder.success(ticket);
        } else
            return InteractionResultHolder.fail(ticket);
    }

    public abstract Type getType();

    public enum Type {
        WEAPON,
        ARMOUR
    }
}
