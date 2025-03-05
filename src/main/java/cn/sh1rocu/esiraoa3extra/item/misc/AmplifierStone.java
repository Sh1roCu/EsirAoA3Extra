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

public abstract class AmplifierStone extends Item {
    public AmplifierStone() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack amplifierStone = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pl = (ServerPlayerEntity) player;
            ItemStack offhand = pl.getItemInHand(Hand.OFF_HAND);
            if (EsirUtil.isEsirArmourOrWeapon(offhand)) {
                if (amplifierStone.getItem() instanceof AmplifierStone) {
                    Type type = getType();
                    if (offhand.getItem() instanceof ArmorItem) {
                        if (type == AmplifierStone.Type.WEAPON) {
                            pl.sendMessage(new StringTextComponent("武器强化石不能增幅防具").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                            return ActionResult.fail(amplifierStone);
                        }
                    } else if (type == AmplifierStone.Type.ARMOUR) {
                        pl.sendMessage(new StringTextComponent("防具强化石不能增幅武器").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                        return ActionResult.fail(amplifierStone);
                    }
                    float[] attribute = EsirUtil.getAttribute(offhand);
                    if (attribute[0] == -1) {
                        pl.sendMessage(new StringTextComponent("该装备处于损毁状态，无法进行增幅").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
                        return ActionResult.fail(amplifierStone);
                    }
                    ItemStack newEquip = EsirUtil.amplifyEquip(pl, offhand, attribute);
                    if (!newEquip.isEmpty())
                        pl.setItemSlot(EquipmentSlotType.OFFHAND, newEquip);
                    amplifierStone.shrink(1);
                    pl.inventoryMenu.broadcastChanges();
                }
            }
            return ActionResult.success(amplifierStone);
        } else
            return ActionResult.pass(amplifierStone);
    }

    public abstract Type getType();

    public enum Type {
        WEAPON,
        ARMOUR
    }
}


