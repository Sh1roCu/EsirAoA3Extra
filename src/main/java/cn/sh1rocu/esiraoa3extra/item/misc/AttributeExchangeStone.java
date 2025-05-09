package cn.sh1rocu.esiraoa3extra.item.misc;

import cn.sh1rocu.esiraoa3extra.registration.AoAItemGroups;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AttributeExchangeStone extends Item {
    public AttributeExchangeStone() {
        super((new Item.Properties()).tab(AoAItemGroups.ESIRAOA3ITEMS).rarity(Rarity.EPIC));
    }
}
