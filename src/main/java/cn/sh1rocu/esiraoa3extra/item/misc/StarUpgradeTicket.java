package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class StarUpgradeTicket extends Item {
    public StarUpgradeTicket() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));

    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack ticket = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pl = (ServerPlayerEntity) player;
            ItemStack offhand = pl.getItemInHand(Hand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                Type type = getType();
                if (offhand.getItem() instanceof ArmorItem) {
                    if (type == StarUpgradeTicket.Type.WEAPON) {
                        pl.sendMessage(new StringTextComponent("武器升星券不能为防具升星").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                        return ActionResult.fail(ticket);
                    }
                } else if (type == StarUpgradeTicket.Type.ARMOUR) {
                    pl.sendMessage(new StringTextComponent("防具升星券不能为武器升星").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                    return ActionResult.fail(ticket);
                }
                float[] attribute = EsirUtil.getAttribute(offhand);
                if (attribute[0] == -1) {
                    pl.sendMessage(new StringTextComponent("该装备处于损毁状态，无法升星").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                    return ActionResult.fail(ticket);
                }
                if ((int) attribute[1] < 10) {
                    pl.sendMessage(new StringTextComponent("该装备未增幅至10级，无法升星").setStyle(Style.EMPTY.withColor(TextFormatting.GOLD)), Util.NIL_UUID);
                    return ActionResult.fail(ticket);
                }
                if (!EsirUtil.canUpgrade((int) attribute[2], ticket)) {
                    pl.sendMessage(new StringTextComponent("该装备目前的星级为" + (int) attribute[2] + "星，无法使用手上的升星券升星").setStyle(Style.EMPTY.withColor(TextFormatting.GOLD)), Util.NIL_UUID);
                    return ActionResult.fail(ticket);
                }
                pl.setItemSlot(EquipmentSlotType.OFFHAND, EsirUtil.upgradeEquip(pl, offhand, (int) attribute[1] - 10, (int) attribute[2] + 1));
                ticket.shrink(1);
                pl.inventoryMenu.broadcastChanges();
                pl.sendMessage(new StringTextComponent("升星完成，该装备目前的星级为" + ((int) attribute[2] + 1) + "，增幅等级为" + ((int) attribute[1] - 10)).setStyle(Style.EMPTY.withColor(TextFormatting.GREEN)), Util.NIL_UUID);
            }
            return ActionResult.success(ticket);
        } else
            return ActionResult.pass(ticket);
    }

    public abstract Type getType();

    public enum Type {
        WEAPON,
        ARMOUR
    }
}
