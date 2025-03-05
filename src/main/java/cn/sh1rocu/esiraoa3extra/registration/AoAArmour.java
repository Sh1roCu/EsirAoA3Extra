package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.item.armour.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tslat.aoa3.advent.Logging;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public final class AoAArmour {
    public static final DeferredRegister<Item> ARMOUR;
    public static final RegistryObject<Item> ACHELOS_HELMET;
    public static final RegistryObject<Item> OCEANUS_HELMET;
    public static final RegistryObject<Item> SEALORD_HELMET;
    public static final RegistryObject<Item> FACE_MASK;
    public static final RegistryObject<Item> NIGHT_VISION_GOGGLES;
    public static final ArmourSet ALACRITY_ARMOUR;
    public static final ArmourSet AMETHIND_ARMOUR;
    public static final ArmourSet ARCHAIC_ARMOUR;
    public static final ArmourSet BARON_ARMOUR;
    public static final ArmourSet BATTLEBORN_ARMOUR;
    public static final ArmourSet BIOGENIC_ARMOUR;
    public static final ArmourSet BOREIC_ARMOUR;
    public static final ArmourSet CANDY_ARMOUR;
    public static final ArmourSet COMMANDER_ARMOUR;
    public static final ArmourSet CRYSTALLIS_ARMOUR;
    public static final ArmourSet ELECANYTE_ARMOUR;
    public static final ArmourSet EMBRODIUM_ARMOUR;
    public static final ArmourSet EXOPLATE_ARMOUR;
    public static final ArmourSet EXPLOSIVE_ARMOUR;
    public static final ArmourSet FUNGAL_ARMOUR;
    public static final ArmourSet GHASTLY_ARMOUR;
    public static final ArmourSet GHOULISH_ARMOUR;
    public static final ArmourSet HAZMAT_ARMOUR;
    public static final ArmourSet HYDRANGIC_ARMOUR;
    public static final ArmourSet HYDROPLATE_ARMOUR;
    public static final ArmourSet ICE_ARMOUR;
    public static final ArmourSet INFERNAL_ARMOUR;
    public static final ArmourSet KNIGHT_ARMOUR;
    public static final ArmourSet LUNAR_ARMOUR;
    public static final ArmourSet LYNDAMYTE_ARMOUR;
    public static final ArmourSet LYONIC_ARMOUR;
    public static final ArmourSet MERCURIAL_ARMOUR;
    public static final ArmourSet NECRO_ARMOUR;
    public static final ArmourSet NETHENGEIC_ARMOUR;
    public static final ArmourSet NIGHTMARE_ARMOUR;
    public static final ArmourSet OMNI_ARMOUR;
    public static final ArmourSet PHANTASM_ARMOUR;
    public static final ArmourSet POISON_ARMOUR;
    public static final ArmourSet PREDATIOUS_ARMOUR;
    public static final ArmourSet PRIMORDIAL_ARMOUR;
    public static final ArmourSet PURITY_ARMOUR;
    public static final ArmourSet ROCKBONE_ARMOUR;
    public static final ArmourSet ROSIDIAN_ARMOUR;
    public static final ArmourSet RUNIC_ARMOUR;
    public static final ArmourSet SHARPSHOT_ARMOUR;
    public static final ArmourSet SKELETAL_ARMOUR;
    public static final ArmourSet SPACEKING_ARMOUR;
    public static final ArmourSet SPEED_ARMOUR;
    public static final ArmourSet SUBTERRANEAN_ARMOUR;
    public static final ArmourSet UTOPIAN_ARMOUR;
    public static final ArmourSet VOID_ARMOUR;
    public static final ArmourSet WEAKEN_ARMOUR;
    public static final ArmourSet WITHER_ARMOUR;
    public static final ArmourSet ZARGONITE_ARMOUR;

    public AoAArmour() {
    }

    private static <T extends Item> RegistryObject<T> registerArmour(String registryName, Supplier<T> item) {
        return ARMOUR.register(registryName, item);
    }

    private static ArmourSet registerArmourSet(String registryPrefix, Class<? extends AdventArmour> armourClass) {
        return new ArmourSet(registryPrefix, armourClass);
    }

    static {
        ARMOUR = DeferredRegister.create(ForgeRegistries.ITEMS, "esiraoa3extra");
        ACHELOS_HELMET = registerArmour("achelos_helmet", AchelosHelmet::new);
        OCEANUS_HELMET = registerArmour("oceanus_helmet", OceanusHelmet::new);
        SEALORD_HELMET = registerArmour("sealord_helmet", SealordHelmet::new);
        FACE_MASK = registerArmour("face_mask", FaceMask::new);
        NIGHT_VISION_GOGGLES = registerArmour("night_vision_goggles", NightVisionGoggles::new);
        ALACRITY_ARMOUR = registerArmourSet("alacrity", AlacrityArmour.class);
        AMETHIND_ARMOUR = registerArmourSet("amethind", AmethindArmour.class);
        ARCHAIC_ARMOUR = registerArmourSet("archaic", ArchaicArmour.class);
        BARON_ARMOUR = registerArmourSet("baron", BaronArmour.class);
        BATTLEBORN_ARMOUR = registerArmourSet("battleborn", BattlebornArmour.class);
        BIOGENIC_ARMOUR = registerArmourSet("biogenic", BiogenicArmour.class);
        BOREIC_ARMOUR = registerArmourSet("boreic", BoreicArmour.class);
        CANDY_ARMOUR = registerArmourSet("candy", CandyArmour.class);
        COMMANDER_ARMOUR = registerArmourSet("commander", CommanderArmour.class);
        CRYSTALLIS_ARMOUR = registerArmourSet("crystallis", CrystallisArmour.class);
        ELECANYTE_ARMOUR = registerArmourSet("elecanyte", ElecanyteArmour.class);
        EMBRODIUM_ARMOUR = registerArmourSet("embrodium", EmbrodiumArmour.class);
        EXOPLATE_ARMOUR = registerArmourSet("exoplate", ExoplateArmour.class);
        EXPLOSIVE_ARMOUR = registerArmourSet("explosive", ExplosiveArmour.class);
        FUNGAL_ARMOUR = registerArmourSet("fungal", FungalArmour.class);
        GHASTLY_ARMOUR = registerArmourSet("ghastly", GhastlyArmour.class);
        GHOULISH_ARMOUR = registerArmourSet("ghoulish", GhoulishArmour.class);
        HAZMAT_ARMOUR = registerArmourSet("hazmat", HazmatArmour.class);
        HYDRANGIC_ARMOUR = registerArmourSet("hydrangic", HydrangicArmour.class);
        HYDROPLATE_ARMOUR = registerArmourSet("hydroplate", HydroplateArmour.class);
        ICE_ARMOUR = registerArmourSet("ice", IceArmour.class);
        INFERNAL_ARMOUR = registerArmourSet("infernal", InfernalArmour.class);
        KNIGHT_ARMOUR = registerArmourSet("knight", KnightArmour.class);
        LUNAR_ARMOUR = registerArmourSet("lunar", LunarArmour.class);
        LYNDAMYTE_ARMOUR = registerArmourSet("lyndamyte", LyndamyteArmour.class);
        LYONIC_ARMOUR = registerArmourSet("lyonic", LyonicArmour.class);
        MERCURIAL_ARMOUR = registerArmourSet("mercurial", MercurialArmour.class);
        NECRO_ARMOUR = registerArmourSet("necro", NecroArmour.class);
        NETHENGEIC_ARMOUR = registerArmourSet("nethengeic", NethengeicArmour.class);
        NIGHTMARE_ARMOUR = registerArmourSet("nightmare", NightmareArmour.class);
        OMNI_ARMOUR = registerArmourSet("omni", OmniArmour.class);
        PHANTASM_ARMOUR = registerArmourSet("phantasm", PhantasmArmour.class);
        POISON_ARMOUR = registerArmourSet("poison", PoisonArmour.class);
        PREDATIOUS_ARMOUR = registerArmourSet("predatious", PredatiousArmour.class);
        PRIMORDIAL_ARMOUR = registerArmourSet("primordial", PrimordialArmour.class);
        PURITY_ARMOUR = registerArmourSet("purity", PurityArmour.class);
        ROCKBONE_ARMOUR = registerArmourSet("rockbone", RockboneArmour.class);
        ROSIDIAN_ARMOUR = registerArmourSet("rosidian", RosidianArmour.class);
        RUNIC_ARMOUR = registerArmourSet("runic", RunicArmour.class);
        SHARPSHOT_ARMOUR = registerArmourSet("sharpshot", SharpshotArmour.class);
        SKELETAL_ARMOUR = registerArmourSet("skeletal", SkeletalArmour.class);
        SPACEKING_ARMOUR = registerArmourSet("spaceking", SpacekingArmour.class);
        SPEED_ARMOUR = registerArmourSet("speed", SpeedArmour.class);
        SUBTERRANEAN_ARMOUR = registerArmourSet("subterranean", SubterraneanArmour.class);
        UTOPIAN_ARMOUR = registerArmourSet("utopian", UtopianArmour.class);
        VOID_ARMOUR = registerArmourSet("void", VoidArmour.class);
        WEAKEN_ARMOUR = registerArmourSet("weaken", WeakenArmour.class);
        WITHER_ARMOUR = registerArmourSet("wither", WitherArmour.class);
        ZARGONITE_ARMOUR = registerArmourSet("zargonite", ZargoniteArmour.class);
    }

    public static class ArmourSet {
        public final RegistryObject<Item> helmet;
        public final RegistryObject<Item> chestplate;
        public final RegistryObject<Item> leggings;
        public final RegistryObject<Item> boots;

        private ArmourSet(String registryPrefix, Class<? extends AdventArmour> armourClass) {
            RegistryObject<Item> helm = null;
            RegistryObject<Item> chest = null;
            RegistryObject<Item> legs = null;
            RegistryObject<Item> boot = null;

            try {
                Constructor<? extends AdventArmour> constructor = armourClass.getConstructor(EquipmentSlotType.class);
                helm = AoAArmour.ARMOUR.register(registryPrefix + "_helmet", () -> {
                    return this.construct(constructor, EquipmentSlotType.HEAD);
                });
                chest = AoAArmour.ARMOUR.register(registryPrefix + "_chestplate", () -> {
                    return this.construct(constructor, EquipmentSlotType.CHEST);
                });
                legs = AoAArmour.ARMOUR.register(registryPrefix + "_legs", () -> {
                    return this.construct(constructor, EquipmentSlotType.LEGS);
                });
                boot = AoAArmour.ARMOUR.register(registryPrefix + "_boots", () -> {
                    return this.construct(constructor, EquipmentSlotType.FEET);
                });
            } catch (NoSuchMethodException var11) {
                Logging.logMessage(Level.ERROR, "Somehow we've managed to throw an error while registering armours. I'm really not even sure how this is possible.", var11);
            } finally {
                this.helmet = helm;
                this.chestplate = chest;
                this.leggings = legs;
                this.boots = boot;
            }

        }

        private AdventArmour construct(Constructor<? extends AdventArmour> constructor, EquipmentSlotType slot) {
            try {
                return (AdventArmour) constructor.newInstance(slot);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException var4) {
                Logging.logMessage(Level.ERROR, "Somehow we've managed to throw an error while registering armours. I'm really not even sure how this is possible.", var4);
                return null;
            }
        }
    }
}
