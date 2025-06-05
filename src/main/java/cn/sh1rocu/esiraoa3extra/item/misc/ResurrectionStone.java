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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class ResurrectionStone extends Item {
    public ResurrectionStone() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack resStone = player.getItemInHand(hand);
        if (player instanceof ServerPlayer && hand == InteractionHand.MAIN_HAND) {
            ServerPlayer pl = (ServerPlayer) player;
            ItemStack offhand = pl.getItemInHand(InteractionHand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                if (EsirUtil.getAttribute(offhand)[0] == -1) {
                    pl.setItemSlot(EquipmentSlot.OFFHAND, EsirUtil.fixBrokenEquip(offhand));
                    pl.sendMessage(new TextComponent("该装备的损毁状态已消除").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), Util.NIL_UUID);
                    resStone.shrink(1);
                    pl.inventoryMenu.broadcastChanges();
                } else {
                    pl.sendMessage(new TextComponent("该装备不需要消除损毁状态").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                    return InteractionResultHolder.fail(resStone);
                }
            }
            return InteractionResultHolder.success(resStone);
        } else
            return InteractionResultHolder.fail(resStone);
    }
}
