package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.tslat.aoa3.common.registration.AoAItemGroups;

public abstract class AdventArmour extends net.tslat.aoa3.content.item.armour.AdventArmour {
    public static final String HELMET_MODIFIER_UUID = "dd109257-9a75-463a-85b5-45176d64c7f4";
    public static final String CHESTPLATE_MODIFIER_UUID = "764ed831-1f73-4c1c-84aa-7ba7224b768c";
    public static final String LEGS_MODIFIER_UUID = "74f00914-1e93-4f8a-9ed0-0a12e098671e";
    public static final String BOOTS_MODIFIER_UUID = "9261dfbe-1ee3-4789-94c8-8500a2bc6960";

    public AdventArmour(ArmorMaterial material, EquipmentSlot slot) {
        this(material, slot, Rarity.COMMON);
    }

    public AdventArmour(ArmorMaterial material, EquipmentSlot slot, Rarity rarity) {
        this(material, slot, new Item.Properties().tab(AoAItemGroups.ARMOUR).durability(material.getDurabilityForSlot(slot)).rarity(rarity));
    }

    public AdventArmour(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        super(material, slot, properties);
    }
}