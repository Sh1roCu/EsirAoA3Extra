package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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

public class ResurrectionStone extends Item {
    public ResurrectionStone() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack resStone = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pl = (ServerPlayerEntity) player;
            ItemStack offhand = pl.getItemInHand(Hand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                if (EsirUtil.getAttribute(offhand)[0] == -1) {
                    pl.setItemSlot(EquipmentSlotType.OFFHAND, EsirUtil.fixBrokenEquip(offhand));
                    pl.sendMessage(new StringTextComponent("该装备的损毁状态已消除").setStyle(Style.EMPTY.withColor(TextFormatting.GREEN)), Util.NIL_UUID);
                    resStone.shrink(1);
                    pl.inventoryMenu.broadcastChanges();
                } else {
                    pl.sendMessage(new StringTextComponent("该装备不需要消除损毁状态").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                    return ActionResult.fail(resStone);
                }
            }
            return ActionResult.success(resStone);
        } else
            return ActionResult.pass(resStone);
    }
}
