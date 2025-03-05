package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class DivineBlessingTalisman extends Item {
    public DivineBlessingTalisman() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack talisman = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pl = (ServerPlayerEntity)player;
            ItemStack offhand = pl.getItemInHand(Hand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                CompoundNBT compoundNBT = offhand.getTag() != null ? offhand.getTag() : new CompoundNBT();
                if (!compoundNBT.contains("amplifierProtection")) {
                    compoundNBT.putBoolean("amplifierProtection", true);
                    offhand.setTag(compoundNBT);
                    pl.setItemSlot(EquipmentSlotType.OFFHAND, offhand);
                    pl.sendMessage(new StringTextComponent("已为该装备附加神恩符的保护，下次增幅失败时将抵消一次失败惩罚").setStyle(Style.EMPTY.withColor(TextFormatting.GREEN)), Util.NIL_UUID);
                    talisman.shrink(1);
                    player.inventoryMenu.broadcastChanges();
                } else {
                    pl.sendMessage(new StringTextComponent("该装备已存在神恩符的保护，无法再次附加").setStyle(Style.EMPTY.withColor(TextFormatting.GOLD)), Util.NIL_UUID);
                }
            }
            return ActionResult.success(talisman);
        } else
            return ActionResult.pass(talisman);
    }
}
