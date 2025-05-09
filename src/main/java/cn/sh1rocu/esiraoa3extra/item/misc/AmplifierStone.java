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

public abstract class AmplifierStone extends Item {
    public AmplifierStone() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack amplifierStone = player.getItemInHand(hand);
        if (player instanceof ServerPlayer) {
            ServerPlayer pl = (ServerPlayer) player;
            ItemStack offhand = pl.getItemInHand(InteractionHand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                if (amplifierStone.getItem() instanceof AmplifierStone) {
                    Type type = getType();
                    if (offhand.getItem() instanceof ArmorItem) {
                        if (type == AmplifierStone.Type.WEAPON) {
                            pl.sendMessage(new TextComponent("武器强化石不能增幅防具").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                            return InteractionResultHolder.fail(amplifierStone);
                        }
                    } else if (type == AmplifierStone.Type.ARMOUR) {
                        pl.sendMessage(new TextComponent("防具强化石不能增幅武器").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                        return InteractionResultHolder.fail(amplifierStone);
                    }
                    float[] attribute = EsirUtil.getAttribute(offhand);
                    if (attribute[0] == -1) {
                        pl.sendMessage(new TextComponent("该装备处于损毁状态，无法进行增幅").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
                        return InteractionResultHolder.fail(amplifierStone);
                    }
                    ItemStack newEquip = EsirUtil.amplifyEquip(pl, offhand, attribute);
                    if (!newEquip.isEmpty())
                        pl.setItemSlot(EquipmentSlot.OFFHAND, newEquip);
                    amplifierStone.shrink(1);
                    pl.inventoryMenu.broadcastChanges();
                }
            }
            return InteractionResultHolder.success(amplifierStone);
        } else
            return InteractionResultHolder.pass(amplifierStone);
    }

    public abstract Type getType();

    public enum Type {
        WEAPON,
        ARMOUR
    }
}


