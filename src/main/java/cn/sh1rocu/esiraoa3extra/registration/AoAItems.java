package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.item.misc.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AoAItems {
    public static final DeferredRegister<Item> ITEMS;

    public static final RegistryObject<Item> AMPLIFIERSTONE_WEAPON;
    public static final RegistryObject<Item> AMPLIFIERSTONE_ARMOUR;
    public static final RegistryObject<Item> ATTRIBUTE_EXCHANGE_STONE;
    public static final RegistryObject<Item> RESURRECTION_STONE;
    public static final RegistryObject<Item> DIVINE_BLESSING_TALISMAN_WEAPON;
    public static final RegistryObject<Item> DIVINE_BLESSING_TALISMAN_ARMOUR;
    public static final RegistryObject<Item> STARUPGRADETICKET_WEAPON;
    public static final RegistryObject<Item> STARUPGRADETICKET_ARMOUR;


    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EsirAoA3Extra.MODID);
        AMPLIFIERSTONE_WEAPON = registerItem("amplifierstone_weapon", WeaponAmplifierStone::new);
        AMPLIFIERSTONE_ARMOUR = registerItem("amplifierstone_armour", ArmourAmplifierStone::new);
        RESURRECTION_STONE = registerItem("resurrection_stone", ResurrectionStone::new);
        ATTRIBUTE_EXCHANGE_STONE = registerItem("attribute_exchange_stone", AttributeExchangeStone::new);
        DIVINE_BLESSING_TALISMAN_WEAPON = registerItem("divine_blessing_talisman_weapon", WeaponDBTalisman::new);
        DIVINE_BLESSING_TALISMAN_ARMOUR = registerItem("divine_blessing_talisman_armour", ArmourDBTalisman::new);
        STARUPGRADETICKET_WEAPON = registerItem("starupgradeticket_weapon", WeaponStarUpgradeTicket::new);
        STARUPGRADETICKET_ARMOUR = registerItem("starupgradeticket_armour", ArmourStarUpgradeTicket::new);
    }

    private static <T extends Item> RegistryObject<T> registerItem(String registryId, Supplier<T> item) {
        return ITEMS.register(registryId, item);
    }
}
