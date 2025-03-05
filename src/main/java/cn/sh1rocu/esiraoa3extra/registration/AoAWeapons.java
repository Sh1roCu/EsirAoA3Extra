package cn.sh1rocu.esiraoa3extra.registration;

import cn.sh1rocu.esiraoa3extra.item.weapon.blaster.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.bow.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.cannon.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.crossbow.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.greatblade.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.gun.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.maul.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.shotgun.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.sniper.Terminator;
import cn.sh1rocu.esiraoa3extra.item.weapon.sniper.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.staff.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.sword.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.thrown.*;
import cn.sh1rocu.esiraoa3extra.item.weapon.vulcane.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;

import java.util.function.Supplier;

public final class AoAWeapons {
    public static final DeferredRegister<Item> WEAPONS;
    public static final RegistryObject<Item> AMETHYST_SWORD;
    public static final RegistryObject<Item> BARON_SWORD;
    public static final RegistryObject<Item> BLOODFURY;
    public static final RegistryObject<Item> BLOODSTONE_SWORD;
    public static final RegistryObject<Item> CANDLEFIRE_SWORD;
    public static final RegistryObject<Item> CARAMEL_CARVER;
    public static final RegistryObject<Item> CORALSTORM_SWORD;
    public static final RegistryObject<Item> CREEPIFIED_SWORD;
    public static final RegistryObject<Item> CRYSTALLITE_SWORD;
    public static final RegistryObject<Item> EMBERSTONE_SWORD;
    public static final RegistryObject<Item> EXPLOCHRON_SWORD;
    public static final RegistryObject<Item> FIREBORNE_SWORD;
    public static final RegistryObject<Item> GUARDIANS_SWORD;
    public static final RegistryObject<Item> HARVESTER_SWORD;
    public static final RegistryObject<Item> HOLY_SWORD;
    public static final RegistryObject<Item> ILLUSION_SWORD;
    public static final RegistryObject<Item> JADE_SWORD;
    public static final RegistryObject<Item> LEGBONE_SWORD;
    public static final RegistryObject<Item> LIGHTS_WAY;
    public static final RegistryObject<Item> LIMONITE_SWORD;
    public static final RegistryObject<Item> NETHENGEIC_SWORD;
    public static final RegistryObject<Item> PRIMAL_SWORD;
    public static final RegistryObject<Item> ROCKBASHER_SWORD;
    public static final RegistryObject<Item> ROCK_PICK_SWORD;
    public static final RegistryObject<Item> ROSIDIAN_SWORD;
    public static final RegistryObject<Item> ROSITE_SWORD;
    public static final RegistryObject<Item> RUNIC_SWORD;
    public static final RegistryObject<Item> SAPPHIRE_SWORD;
    public static final RegistryObject<Item> SHADOW_SWORD;
    public static final RegistryObject<Item> SHROOMUS_SWORD;
    public static final RegistryObject<Item> SKELETAL_SWORD;
    public static final RegistryObject<Item> SUPREMACY_SWORD;
    public static final RegistryObject<Item> SWEET_SWORD;
    public static final RegistryObject<Item> TROLL_BASHER_AXE;
    public static final RegistryObject<Item> ULTRAFLAME;
    public static final RegistryObject<Item> VOID_SWORD;
    public static final RegistryObject<Item> BARON_GREATBLADE;
    public static final RegistryObject<Item> BLOODLURKER;
    public static final RegistryObject<Item> CANDY_BLADE;
    public static final RegistryObject<Item> CORAL_GREATBLADE;
    public static final RegistryObject<Item> COTTON_CRUSHER;
    public static final RegistryObject<Item> CREEPOID_GREATBLADE;
    public static final RegistryObject<Item> CRYSTAL_GREATBLADE;
    public static final RegistryObject<Item> EREBON_SCYTHE;
    public static final RegistryObject<Item> GODS_GREATBLADE;
    public static final RegistryObject<Item> GOOFY_GREATBLADE;
    public static final RegistryObject<Item> HAUNTED_GREATBLADE;
    public static final RegistryObject<Item> KNIGHTS_GUARD;
    public static final RegistryObject<Item> LELYETIAN_GREATBLADE;
    public static final RegistryObject<Item> LUNAR_GREATBLADE;
    public static final RegistryObject<Item> LUXON_SCYTHE;
    public static final RegistryObject<Item> LYONIC_GREATBLADE;
    public static final RegistryObject<Item> MILLENNIUM_GREATBLADE;
    public static final RegistryObject<Item> NOXIOUS_GREATBLADE;
    public static final RegistryObject<Item> PLUTON_SCYTHE;
    public static final RegistryObject<Item> PRIMORDIAL_GREATBLADE;
    public static final RegistryObject<Item> ROSIDIAN_GREATBLADE;
    public static final RegistryObject<Item> ROYAL_GREATBLADE;
    public static final RegistryObject<Item> RUNIC_GREATBLADE;
    public static final RegistryObject<Item> SELYAN_SCYTHE;
    public static final RegistryObject<Item> SHROOMIC_GREATBLADE;
    public static final RegistryObject<Item> SHYRE_SWORD;
    public static final RegistryObject<Item> SUBTERRANEAN_GREATBLADE;
    public static final RegistryObject<Item> TIDAL_GREATBLADE;
    public static final RegistryObject<Item> UNDERWORLD_GREATBLADE;
    public static final RegistryObject<Item> CORALSTONE_MAUL;
    public static final RegistryObject<Item> CRYSTAL_MAUL;
    public static final RegistryObject<Item> ELECTRON_MAUL;
    public static final RegistryObject<Item> HORIZON_MAUL;
    public static final RegistryObject<Item> VULCAMMER_MAUL;
    public static final RegistryObject<Item> ABOMINATOR;
    public static final RegistryObject<Item> APOCO_ASSAULT_RIFLE;
    public static final RegistryObject<Item> APOCO_RIFLE;
    public static final RegistryObject<Item> AQUA_MAGNUM;
    public static final RegistryObject<Item> ARTIFACT;
    public static final RegistryObject<Item> BARONATOR;
    public static final RegistryObject<Item> BAYONETTE_RIFLE;
    public static final RegistryObject<Item> BIG_TOP;
    public static final RegistryObject<Item> BLOOD_IRON;
    public static final RegistryObject<Item> CHAIN_WRECKER;
    public static final RegistryObject<Item> CHILLI_CHUGGER;
    public static final RegistryObject<Item> CLOWNERSHOT;
    public static final RegistryObject<Item> CONSTRUCT;
    public static final RegistryObject<Item> CORAL_CLOGGER;
    public static final RegistryObject<Item> CORE_RIFLE;
    public static final RegistryObject<Item> CRYSTAL_CARVER;
    public static final RegistryObject<Item> CYCLONE;
    public static final RegistryObject<Item> DARKENER;
    public static final RegistryObject<Item> DART_GUN;
    public static final RegistryObject<Item> DESTRUCTION_RIFLE;
    public static final RegistryObject<Item> DISCHARGE_RIFLE;
    public static final RegistryObject<Item> DRACO;
    public static final RegistryObject<Item> DRAGILATOR;
    public static final RegistryObject<Item> DUSTOMETER;
    public static final RegistryObject<Item> ECHO_GULL;
    public static final RegistryObject<Item> ELECTINATOR;
    public static final RegistryObject<Item> FLAME_WRECKER;
    public static final RegistryObject<Item> FLAMING_FURY;
    public static final RegistryObject<Item> FLORO_RIFLE;
    public static final RegistryObject<Item> FLOWERS_FURY;
    public static final RegistryObject<Item> FROSTICATOR;
    public static final RegistryObject<Item> GARDENER;
    public static final RegistryObject<Item> GAUGE_RIFLE;
    public static final RegistryObject<Item> GERMINATOR;
    public static final RegistryObject<Item> GOLDEN_FURY;
    public static final RegistryObject<Item> HAPPY_HAUNTER;
    public static final RegistryObject<Item> HAUNTER_RIFLE;
    public static final RegistryObject<Item> HEAT_WAVE;
    public static final RegistryObject<Item> HIVER;
    public static final RegistryObject<Item> HOT_SHOT;
    public static final RegistryObject<Item> HUNTERS_RIFLE;
    public static final RegistryObject<Item> IOMINATOR;
    public static final RegistryObject<Item> ION_REVOLVER;
    public static final RegistryObject<Item> IRO_RIFLE;
    public static final RegistryObject<Item> KRILINATOR;
    public static final RegistryObject<Item> LIGHT_IRON;
    public static final RegistryObject<Item> LUNAR_ASSAULT_RIFLE;
    public static final RegistryObject<Item> MECHANICAL_ASSAULT_RIFLE;
    public static final RegistryObject<Item> MEGAGUN;
    public static final RegistryObject<Item> MIASMA;
    public static final RegistryObject<Item> MINIGUN;
    public static final RegistryObject<Item> MINT_MAGNUM;
    public static final RegistryObject<Item> MK;
    public static final RegistryObject<Item> MK_FUNG;
    public static final RegistryObject<Item> NETHENETTE_RIFLE;
    public static final RegistryObject<Item> NETHENGEIC_SLUGGER;
    public static final RegistryObject<Item> OVERSHOT;
    public static final RegistryObject<Item> PRECASIAN_SLUGGER;
    public static final RegistryObject<Item> PREDATOR;
    public static final RegistryObject<Item> PREDIGUN;
    public static final RegistryObject<Item> PULSATOR;
    public static final RegistryObject<Item> PURITY_RIFLE;
    public static final RegistryObject<Item> ROCKER_RIFLE;
    public static final RegistryObject<Item> ROULETTE;
    public static final RegistryObject<Item> SHOE_FLINGER;
    public static final RegistryObject<Item> SKULLETTE;
    public static final RegistryObject<Item> SKULLIFACT;
    public static final RegistryObject<Item> SPECTACLE;
    public static final RegistryObject<Item> SPINE_GUN;
    public static final RegistryObject<Item> SQUAD_GUN;
    public static final RegistryObject<Item> STAMPEDE;
    public static final RegistryObject<Item> STORMER;
    public static final RegistryObject<Item> SUBLIMUS;
    public static final RegistryObject<Item> TIGER_TOMMY;
    public static final RegistryObject<Item> TOMMY;
    public static final RegistryObject<Item> VILE_VANQUISHER;
    public static final RegistryObject<Item> WART_GUN;
    public static final RegistryObject<Item> WRECKER;
    public static final RegistryObject<Item> ABYSSRO;
    public static final RegistryObject<Item> AMPLIFIER;
    public static final RegistryObject<Item> BLAST_BARREL;
    public static final RegistryObject<Item> BLUE_BARREL;
    public static final RegistryObject<Item> BOULDER;
    public static final RegistryObject<Item> BROWN_BLASTER;
    public static final RegistryObject<Item> DEMOLISHER;
    public static final RegistryObject<Item> DESTRUCTION_SHOTGUN;
    public static final RegistryObject<Item> DISCHARGE_SHOTGUN;
    public static final RegistryObject<Item> GIMMICK;
    public static final RegistryObject<Item> GINGER_BLASTER;
    public static final RegistryObject<Item> LONG_SHOT;
    public static final RegistryObject<Item> MECHYRO;
    public static final RegistryObject<Item> PURITY_SHOTGUN;
    public static final RegistryObject<Item> PURPLE_PUNISHER;
    public static final RegistryObject<Item> RED_ROCKET;
    public static final RegistryObject<Item> VIVO;
    public static final RegistryObject<Item> BARON_SSR;
    public static final RegistryObject<Item> BAYONETTE_SR;
    public static final RegistryObject<Item> BOLT_RIFLE;
    public static final RegistryObject<Item> CAMO_RIFLE;
    public static final RegistryObject<Item> CLOWN_CRACKER;
    public static final RegistryObject<Item> CLOWNIMATOR;
    public static final RegistryObject<Item> CRYSTANEER;
    public static final RegistryObject<Item> DARK_BEAST;
    public static final RegistryObject<Item> DEADLOCK;
    public static final RegistryObject<Item> DECIMATOR;
    public static final RegistryObject<Item> DISCHARGE_SNIPER;
    public static final RegistryObject<Item> DUAL_SIGHT;
    public static final RegistryObject<Item> DUSTER;
    public static final RegistryObject<Item> FLORO500;
    public static final RegistryObject<Item> HEAD_HUNTER;
    public static final RegistryObject<Item> HIVE_CRACKER;
    public static final RegistryObject<Item> KA_500;
    public static final RegistryObject<Item> MARK_MAKER;
    public static final RegistryObject<Item> MINERAL;
    public static final RegistryObject<Item> MONSTER;
    public static final RegistryObject<Item> MOON_MAKER;
    public static final RegistryObject<Item> ROSID_RIFLE;
    public static final RegistryObject<Item> SABBATH;
    public static final RegistryObject<Item> SLUDGE_SNIPER;
    public static final RegistryObject<Item> SWEET_TOOTH;
    public static final RegistryObject<Item> TERMINATOR;
    public static final RegistryObject<Item> VIPER_1;
    public static final RegistryObject<Item> ANCIENT_BOMBER;
    public static final RegistryObject<Item> ANCIENT_DISCHARGER;
    public static final RegistryObject<Item> AQUA_CANNON;
    public static final RegistryObject<Item> BALLOON_BOMBER;
    public static final RegistryObject<Item> BIG_BLAST;
    public static final RegistryObject<Item> BLAST_CANNON;
    public static final RegistryObject<Item> BLISSFUL_BLAST;
    public static final RegistryObject<Item> BOMB_LAUNCHER;
    public static final RegistryObject<Item> BOOM_BOOM;
    public static final RegistryObject<Item> BOOM_CANNON;
    public static final RegistryObject<Item> BOULDER_BOMBER;
    public static final RegistryObject<Item> BOZO_BLASTER;
    public static final RegistryObject<Item> BULB_CANNON;
    public static final RegistryObject<Item> CARROT_CANNON;
    public static final RegistryObject<Item> CLOWN_CANNON;
    public static final RegistryObject<Item> CLOWNO_PULSE;
    public static final RegistryObject<Item> CORAL_CANNON;
    public static final RegistryObject<Item> DISCHARGE_CANNON;
    public static final RegistryObject<Item> ENERGY_CANNON;
    public static final RegistryObject<Item> EREBON_STICKLER;
    public static final RegistryObject<Item> FLORO_RPG;
    public static final RegistryObject<Item> FLOWER_CANNON;
    public static final RegistryObject<Item> FUNGAL_CANNON;
    public static final RegistryObject<Item> GHAST_BLASTER;
    public static final RegistryObject<Item> GHOUL_CANNON;
    public static final RegistryObject<Item> GIGA_CANNON;
    public static final RegistryObject<Item> GOLDER_BOMBER;
    public static final RegistryObject<Item> HIVE_BLASTER;
    public static final RegistryObject<Item> HIVE_HOWITZER;
    public static final RegistryObject<Item> IRO_CANNON;
    public static final RegistryObject<Item> JACK_FUNGER;
    public static final RegistryObject<Item> JACK_ROCKER;
    public static final RegistryObject<Item> LUXON_STICKLER;
    public static final RegistryObject<Item> MECHA_CANNON;
    public static final RegistryObject<Item> MINI_CANNON;
    public static final RegistryObject<Item> MISSILE_MAKER;
    public static final RegistryObject<Item> MOON_CANNON;
    public static final RegistryObject<Item> PLUTON_STICKLER;
    public static final RegistryObject<Item> PREDATORIAN_BLASTER;
    public static final RegistryObject<Item> PULSE_CANNON;
    public static final RegistryObject<Item> RPG;
    public static final RegistryObject<Item> SELYAN_STICKLER;
    public static final RegistryObject<Item> SHADOW_BLASTER;
    public static final RegistryObject<Item> SHYRE_BLASTER;
    public static final RegistryObject<Item> SMILE_BLASTER;
    public static final RegistryObject<Item> SUPER_CANNON;
    public static final RegistryObject<Item> ULTRA_CANNON;
    public static final RegistryObject<Item> VOX_CANNON;
    public static final RegistryObject<Item> WATER_BALLOON_BOMBER;
    public static final RegistryObject<Item> WITHER_CANNON;
    public static final RegistryObject<Grenade> GRENADE;
    public static final RegistryObject<SliceStar> SLICE_STAR;
    public static final RegistryObject<Chakram> CHAKRAM;
    public static final RegistryObject<GooBall> GOO_BALL;
    public static final RegistryObject<Vulkram> VULKRAM;
    public static final RegistryObject<Hellfire> HELLFIRE;
    public static final RegistryObject<RunicBomb> RUNIC_BOMB;
    public static final RegistryObject<HardenedParapiranha> HARDENED_PARAPIRANHA;
    public static final RegistryObject<Item> VULCANE;
    public static final RegistryObject<Item> BATTLE_VULCANE;
    public static final RegistryObject<Item> EQUALITY_VULCANE;
    public static final RegistryObject<Item> FIRE_VULCANE;
    public static final RegistryObject<Item> IMPAIRMENT_VULCANE;
    public static final RegistryObject<Item> POISON_VULCANE;
    public static final RegistryObject<Item> POWER_VULCANE;
    public static final RegistryObject<Item> WITHER_VULCANE;
    public static final RegistryObject<Item> ALACRITY_BOW;
    public static final RegistryObject<Item> ANCIENT_BOW;
    public static final RegistryObject<Item> ATLANTIC_BOW;
    public static final RegistryObject<Item> BARON_BOW;
    public static final RegistryObject<Item> BOREIC_BOW;
    public static final RegistryObject<Item> DAYBREAKER_BOW;
    public static final RegistryObject<Item> DEEP_BOW;
    public static final RegistryObject<Item> EXPLOSIVE_BOW;
    public static final RegistryObject<Item> HAUNTED_BOW;
    public static final RegistryObject<Item> ICE_BOW;
    public static final RegistryObject<Item> INFERNAL_BOW;
    public static final RegistryObject<Item> JUSTICE_BOW;
    public static final RegistryObject<Item> LUNAR_BOW;
    public static final RegistryObject<Item> MECHA_BOW;
    public static final RegistryObject<Item> NIGHTMARE_BOW;
    public static final RegistryObject<Item> POISON_BOW;
    public static final RegistryObject<Item> PREDATIOUS_BOW;
    public static final RegistryObject<Item> PRIMORDIAL_BOW;
    public static final RegistryObject<Item> ROSIDIAN_BOW;
    public static final RegistryObject<Item> RUNIC_BOW;
    public static final RegistryObject<Item> SCREAMER_BOW;
    public static final RegistryObject<Item> SHYREGEM_BOW;
    public static final RegistryObject<Item> SKELETAL_BOW;
    public static final RegistryObject<Item> SKYDRIVER_BOW;
    public static final RegistryObject<Item> SLINGSHOT;
    public static final RegistryObject<Item> SOULFIRE_BOW;
    public static final RegistryObject<Item> SPECTRAL_BOW;
    public static final RegistryObject<Item> SPEED_BOW;
    public static final RegistryObject<Item> SUNSHINE_BOW;
    public static final RegistryObject<Item> TOXIN_BOW;
    public static final RegistryObject<Item> VOID_BOW;
    public static final RegistryObject<Item> WEAKEN_BOW;
    public static final RegistryObject<Item> WITHER_BOW;
    public static final RegistryObject<Item> CORAL_CROSSBOW;
    public static final RegistryObject<Item> LUNAR_CROSSBOW;
    public static final RegistryObject<Item> MECHA_CROSSBOW;
    public static final RegistryObject<Item> PYRO_CROSSBOW;
    public static final RegistryObject<Item> ROSIDIAN_CROSSBOW;
    public static final RegistryObject<Item> SKELETAL_CROSSBOW;
    public static final RegistryObject<Item> SPECTRAL_CROSSBOW;
    public static final RegistryObject<Item> TROLLS_CROSSBOW;
    public static final RegistryObject<Item> VIRAL_CROSSBOW;
    public static final RegistryObject<Item> AQUATIC_STAFF;
    public static final RegistryObject<Item> ATLANTIC_STAFF;
    public static final RegistryObject<Item> BARON_STAFF;
    public static final RegistryObject<Item> CANDY_STAFF;
    public static final RegistryObject<Item> CELESTIAL_STAFF;
    public static final RegistryObject<Item> CONCUSSION_STAFF;
    public static final RegistryObject<Item> CORAL_STAFF;
    public static final RegistryObject<Item> CRYSTAL_STAFF;
    public static final RegistryObject<Item> CRYSTIK_STAFF;
    public static final RegistryObject<Item> CRYSTON_STAFF;
    public static final RegistryObject<Item> DESTRUCTION_STAFF;
    public static final RegistryObject<Item> EMBER_STAFF;
    public static final RegistryObject<Item> EVERFIGHT_STAFF;
    public static final RegistryObject<Item> EVERMIGHT_STAFF;
    public static final RegistryObject<Item> FIRE_STAFF;
    public static final RegistryObject<Item> FIREFLY_STAFF;
    public static final RegistryObject<Item> FIRESTORM_STAFF;
    public static final RegistryObject<Item> FUNGAL_STAFF;
    public static final RegistryObject<Item> GHOUL_STAFF;
    public static final RegistryObject<Item> HAUNTERS_STAFF;
    public static final RegistryObject<Item> JOKER_STAFF;
    public static final RegistryObject<Item> KAIYU_STAFF;
    public static final RegistryObject<Item> LIGHTNING_STAFF;
    public static final RegistryObject<Item> LIGHTSHINE;
    public static final RegistryObject<Item> LUNAR_STAFF;
    public static final RegistryObject<Item> LYONIC_STAFF;
    public static final RegistryObject<Item> MECHA_STAFF;
    public static final RegistryObject<Item> METEOR_STAFF;
    public static final RegistryObject<Item> MOONLIGHT_STAFF;
    public static final RegistryObject<Item> NATURE_STAFF;
    public static final RegistryObject<Item> NIGHTMARE_STAFF;
    public static final RegistryObject<Item> NOXIOUS_STAFF;
    public static final RegistryObject<Item> PHANTOM_STAFF;
    public static final RegistryObject<Item> POISON_STAFF;
    public static final RegistryObject<Item> POWER_STAFF;
    public static final RegistryObject<Item> PRIMORDIAL_STAFF;
    public static final RegistryObject<Item> REEF_STAFF;
    public static final RegistryObject<Item> REJUVENATION_STAFF;
    public static final RegistryObject<Item> ROSIDIAN_STAFF;
    public static final RegistryObject<Item> RUNIC_STAFF;
    public static final RegistryObject<Item> SHOW_STAFF;
    public static final RegistryObject<Item> SHYRE_STAFF;
    public static final RegistryObject<Item> SKY_STAFF;
    public static final RegistryObject<Item> STRIKER_STAFF;
    public static final RegistryObject<Item> SUN_STAFF;
    public static final RegistryObject<Item> SURGE_STAFF;
    public static final RegistryObject<Item> TANGLE_STAFF;
    public static final RegistryObject<Item> ULTIMATUM_STAFF;
    public static final RegistryObject<Item> UNDERWORLD_STAFF;
    public static final RegistryObject<Item> WARLOCK_STAFF;
    public static final RegistryObject<Item> WATER_STAFF;
    public static final RegistryObject<Item> WEB_STAFF;
    public static final RegistryObject<Item> WIND_STAFF;
    public static final RegistryObject<Item> WITHER_STAFF;
    public static final RegistryObject<Item> WIZARDS_STAFF;
    public static final RegistryObject<Item> APOCO_SHOWER;
    public static final RegistryObject<Item> ATOMIZER;
    public static final RegistryObject<Item> BEAMER;
    public static final RegistryObject<Item> BLAST_CHILLER;
    public static final RegistryObject<Item> BLOOD_DRAINER;
    public static final RegistryObject<Item> BONE_BLASTER;
    public static final RegistryObject<Item> BUBBLE_HORN;
    public static final RegistryObject<Item> COLOUR_CANNON;
    public static final RegistryObject<Item> CONFETTI_CANNON;
    public static final RegistryObject<Item> CONFETTI_CLUSTER;
    public static final RegistryObject<Item> DARK_DESTROYER;
    public static final RegistryObject<Item> DARKLY_GUSTER;
    public static final RegistryObject<Item> DEATH_RAY;
    public static final RegistryObject<Item> DOOM_BRINGER;
    public static final RegistryObject<Item> ERADICATOR;
    public static final RegistryObject<Item> EXPERIMENT_W_801;
    public static final RegistryObject<Item> FLOWERCORNE;
    public static final RegistryObject<Item> FRAGMENT;
    public static final RegistryObject<Item> FROSTER;
    public static final RegistryObject<Item> GAS_BLASTER;
    public static final RegistryObject<Item> GHOUL_GASSER;
    public static final RegistryObject<Item> GOLD_BRINGER;
    public static final RegistryObject<Item> GRAVITY_BLASTER;
    public static final RegistryObject<Item> HELL_HORN;
    public static final RegistryObject<Item> ILLUSION_REVOLVER;
    public static final RegistryObject<Item> ILLUSION_SMG;
    public static final RegistryObject<Item> ION_BLASTER;
    public static final RegistryObject<Item> IRO_MINER;
    public static final RegistryObject<Item> LASER_BLASTER;
    public static final RegistryObject<Item> LIGHT_BLASTER;
    public static final RegistryObject<Item> LIGHT_SPARK;
    public static final RegistryObject<Item> LUNA_BLASTER;
    public static final RegistryObject<Item> MECHA_BLASTER;
    public static final RegistryObject<Item> MIND_BLASTER;
    public static final RegistryObject<Item> MOON_DESTROYER;
    public static final RegistryObject<Item> MOON_SHINER;
    public static final RegistryObject<Item> ODIOUS;
    public static final RegistryObject<Item> ORBOCRON;
    public static final RegistryObject<Item> PARALYZER;
    public static final RegistryObject<Item> PARTY_POPPER;
    public static final RegistryObject<Item> POISON_PLUNGER;
    public static final RegistryObject<Item> POWER_RAY;
    public static final RegistryObject<Item> PROTON;
    public static final RegistryObject<Item> REEFER;
    public static final RegistryObject<Item> REVOLUTION;
    public static final RegistryObject<Item> SEAOCRON;
    public static final RegistryObject<Item> SKULLO_BLASTER;
    public static final RegistryObject<Item> SOUL_DRAINER;
    public static final RegistryObject<Item> SOUL_SPARK;
    public static final RegistryObject<Item> SOUL_STORM;
    public static final RegistryObject<Item> SPIRIT_SHOWER;
    public static final RegistryObject<Item> SWARMOTRON;
    public static final RegistryObject<Item> TOXIC_TERRORIZER;
    public static final RegistryObject<Item> VORTEX_BLASTER;
    public static final RegistryObject<Item> WHIMSY_WINDER;
    public static final RegistryObject<Item> WITHERS_WRATH;

    public AoAWeapons() {
    }

    private static <T extends Item> RegistryObject<T> registerWeapon(String registryName, Supplier<T> item) {
        return WEAPONS.register(registryName, item);
    }

    static {
        WEAPONS = DeferredRegister.create(ForgeRegistries.ITEMS, "esiraoa3extra");
        AMETHYST_SWORD = registerWeapon("amethyst_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(1200, AttackSpeed.NORMAL, 7.5F, 4, 14, AoAItems.AMETHYST));
        });
        BARON_SWORD = registerWeapon("baron_sword", BaronSword::new);
        BLOODFURY = registerWeapon("bloodfury", () -> {
            return new BaseSword(ItemUtil.customItemTier(1770, -2.2F, 13.0F, 4, 10, (Supplier) null));
        });
        BLOODSTONE_SWORD = registerWeapon("bloodstone_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(1800, AttackSpeed.NORMAL, 14.0F, 4, 16, AoAItems.BLOODSTONE));
        });
        CANDLEFIRE_SWORD = registerWeapon("candlefire_sword", CandlefireSword::new);
        CARAMEL_CARVER = registerWeapon("caramel_carver", CaramelCarver::new);
        CORALSTORM_SWORD = registerWeapon("coralstorm_sword", CoralstormSword::new);
        CREEPIFIED_SWORD = registerWeapon("creepified_sword", CreepifiedSword::new);
        CRYSTALLITE_SWORD = registerWeapon("crystallite_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(1700, -2.3F, 14.0F, 4, 10, AoAItems.CRYSTALLITE));
        });
        EMBERSTONE_SWORD = registerWeapon("emberstone_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(1800, AttackSpeed.NORMAL, 11.0F, 4, 10, AoAItems.EMBERSTONE_INGOT));
        });
        EXPLOCHRON_SWORD = registerWeapon("explochron_sword", ExplochronSword::new);
        FIREBORNE_SWORD = registerWeapon("fireborne_sword", FireborneSword::new);
        GUARDIANS_SWORD = registerWeapon("guardians_sword", GuardiansSword::new);
        HARVESTER_SWORD = registerWeapon("harvester_sword", HarvesterSword::new);
        HOLY_SWORD = registerWeapon("holy_sword", HolySword::new);
        ILLUSION_SWORD = registerWeapon("illusion_sword", IllusionSword::new);
        JADE_SWORD = registerWeapon("jade_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(800, AttackSpeed.NORMAL, 7.0F, 3, 11, AoAItems.JADE));
        });
        LEGBONE_SWORD = registerWeapon("legbone_sword", LegboneSword::new);
        LIGHTS_WAY = registerWeapon("lights_way", () -> {
            return new BaseSword(ItemUtil.customItemTier(2700, AttackSpeed.DOUBLE, 7.5F, 4, 10, (Supplier) null));
        });
        LIMONITE_SWORD = registerWeapon("limonite_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(400, AttackSpeed.NORMAL, 5.0F, 2, 3, AoAItems.LIMONITE_INGOT));
        });
        NETHENGEIC_SWORD = registerWeapon("nethengeic_sword", NethengeicSword::new);
        PRIMAL_SWORD = registerWeapon("primal_sword", PrimalSword::new);
        ROCKBASHER_SWORD = registerWeapon("rockbasher_sword", RockbasherSword::new);
        ROCK_PICK_SWORD = registerWeapon("rock_pick_sword", RockPickSword::new);
        ROSIDIAN_SWORD = registerWeapon("rosidian_sword", RosidianSword::new);
        ROSITE_SWORD = registerWeapon("rosite_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(700, AttackSpeed.NORMAL, 6.5F, 3, 6, AoAItems.ROSITE_INGOT));
        });
        RUNIC_SWORD = registerWeapon("runic_sword", RunicSword::new);
        SAPPHIRE_SWORD = registerWeapon("sapphire_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(1750, AttackSpeed.NORMAL, 10.0F, 4, 19, AoAItems.SAPPHIRE));
        });
        SHADOW_SWORD = registerWeapon("shadow_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(2300, AttackSpeed.NORMAL, 17.0F, 4, 10, (Supplier) null));
        });
        SHROOMUS_SWORD = registerWeapon("shroomus_sword", ShroomusSword::new);
        SKELETAL_SWORD = registerWeapon("skeletal_sword", () -> {
            return new BaseSword(ItemUtil.customItemTier(2100, -2.0F, 10.5F, 4, 10, (Supplier) null));
        });
        SUPREMACY_SWORD = registerWeapon("supremacy_sword", SupremacySword::new);
        SWEET_SWORD = registerWeapon("sweet_sword", SweetSword::new);
        TROLL_BASHER_AXE = registerWeapon("troll_basher_axe", TrollBasherAxe::new);
        ULTRAFLAME = registerWeapon("ultraflame", Ultraflame::new);
        VOID_SWORD = registerWeapon("void_sword", VoidSword::new);
        BARON_GREATBLADE = registerWeapon("baron_greatblade", BaronGreatblade::new);
        BLOODLURKER = registerWeapon("bloodlurker", () -> {
            return new BaseGreatblade(21.0, -3.240000009536743, 1350);
        });
        CANDY_BLADE = registerWeapon("candy_blade", CandyBlade::new);
        CORAL_GREATBLADE = registerWeapon("coral_greatblade", () -> {
            return new BaseGreatblade(24.5, -3.240000009536743, 1800);
        });
        COTTON_CRUSHER = registerWeapon("cotton_crusher", CottonCrusher::new);
        CREEPOID_GREATBLADE = registerWeapon("creepoid_greatblade", CreepoidGreatblade::new);
        CRYSTAL_GREATBLADE = registerWeapon("crystal_greatblade", CrystalGreatblade::new);
        EREBON_SCYTHE = registerWeapon("erebon_scythe", ErebonScythe::new);
        GODS_GREATBLADE = registerWeapon("gods_greatblade", () -> {
            return new BaseGreatblade(29.5, -3.240000009536743, 2000);
        });
        GOOFY_GREATBLADE = registerWeapon("goofy_greatblade", GoofyGreatblade::new);
        HAUNTED_GREATBLADE = registerWeapon("haunted_greatblade", HauntedGreatblade::new);
        KNIGHTS_GUARD = registerWeapon("knights_guard", KnightsGuard::new);
        LELYETIAN_GREATBLADE = registerWeapon("lelyetian_greatblade", LelyetianGreatblade::new);
        LUNAR_GREATBLADE = registerWeapon("lunar_greatblade", LunarGreatblade::new);
        LUXON_SCYTHE = registerWeapon("luxon_scythe", LuxonScythe::new);
        LYONIC_GREATBLADE = registerWeapon("lyonic_greatblade", () -> {
            return new BaseGreatblade(19.0, -3.1, 1420);
        });
        MILLENNIUM_GREATBLADE = registerWeapon("millennium_greatblade", MillenniumGreatblade::new);
        NOXIOUS_GREATBLADE = registerWeapon("noxious_greatblade", NoxiousGreatblade::new);
        PLUTON_SCYTHE = registerWeapon("pluton_scythe", PlutonScythe::new);
        PRIMORDIAL_GREATBLADE = registerWeapon("primordial_greatblade", PrimordialGreatblade::new);
        ROSIDIAN_GREATBLADE = registerWeapon("rosidian_greatblade", RosidianGreatblade::new);
        ROYAL_GREATBLADE = registerWeapon("royal_greatblade", () -> {
            return new BaseGreatblade(19.0, -3.240000009536743, 1130);
        });
        RUNIC_GREATBLADE = registerWeapon("runic_greatblade", RunicGreatblade::new);
        SELYAN_SCYTHE = registerWeapon("selyan_scythe", SelyanScythe::new);
        SHROOMIC_GREATBLADE = registerWeapon("shroomic_greatblade", ShroomicGreatblade::new);
        SHYRE_SWORD = registerWeapon("shyre_sword", ShyreSword::new);
        SUBTERRANEAN_GREATBLADE = registerWeapon("subterranean_greatblade", SubterraneanGreatblade::new);
        TIDAL_GREATBLADE = registerWeapon("tidal_greatblade", TidalGreatblade::new);
        UNDERWORLD_GREATBLADE = registerWeapon("underworld_greatblade", UnderworldGreatblade::new);
        CORALSTONE_MAUL = registerWeapon("coralstone_maul", () -> {
            return new BaseMaul(26.5F, (double) AttackSpeed.THIRD, 8.5, 1600);
        });
        CRYSTAL_MAUL = registerWeapon("crystal_maul", CrystalMaul::new);
        ELECTRON_MAUL = registerWeapon("electron_maul", ElectronMaul::new);
        HORIZON_MAUL = registerWeapon("horizon_maul", HorizonMaul::new);
        VULCAMMER_MAUL = registerWeapon("vulcammer_maul", VulcammerMaul::new);
        ABOMINATOR = registerWeapon("abominator", () -> {
            return new Abominator(2.625, 4720, 3, 0.8F);
        });
        APOCO_ASSAULT_RIFLE = registerWeapon("apoco_assault_rifle", () -> {
            return new ApocoAssaultRifle(5.625, 5920, 6, 0.6F);
        });
        APOCO_RIFLE = registerWeapon("apoco_rifle", () -> {
            return new ApocoRifle(2.5, 5960, 6, 1.0F);
        });
        AQUA_MAGNUM = registerWeapon("aqua_magnum", () -> {
            return new AquaMagnum(2.0625, 6080, 2, 0.95F);
        });
        ARTIFACT = registerWeapon("artifact", () -> {
            return new Artifact(10.0, 6000, 5, 0.65F);
        });
        BARONATOR = registerWeapon("baronator", () -> {
            return new Baronator(3.0, 2480, 5, 0.95F);
        });
        BAYONETTE_RIFLE = registerWeapon("bayonette_rifle", () -> {
            return new BayonetteRifle(3.4375, 2520, 5, 0.85F);
        });
        BIG_TOP = registerWeapon("big_top", () -> {
            return new BigTop(3.125, 3680, 4, 0.9F);
        });
        BLOOD_IRON = registerWeapon("blood_iron", () -> {
            return new BloodIron(6.125, 3480, 8, 0.55F);
        });
        CHAIN_WRECKER = registerWeapon("chain_wrecker", () -> {
            return new ChainWrecker(1.5, 3840, 2, 1.2F);
        });
        CHILLI_CHUGGER = registerWeapon("chilli_chugger", () -> {
            return new ChilliChugger(3.0, 3560, 4, 0.5F);
        });
        CLOWNERSHOT = registerWeapon("clownershot", () -> {
            return new Clownershot(2.0, 5880, 4, 1.3F);
        });
        CONSTRUCT = registerWeapon("construct", () -> {
            return new Construct(2.375, 3640, 3, 0.9F);
        });
        CORAL_CLOGGER = registerWeapon("coral_clogger", () -> {
            return new CoralClogger(9.074999809265137, 2000, 10, 0.45F);
        });
        CORE_RIFLE = registerWeapon("core_rifle", () -> {
            return new CoreRifle(1.875, 2520, 3, 1.0F);
        });
        CRYSTAL_CARVER = registerWeapon("crystal_carver", () -> {
            return new CrystalCarver(3.0, 3480, 4, 0.85F);
        });
        CYCLONE = registerWeapon("cyclone", () -> {
            return new Cyclone(2.375, 4400, 3, 1.0F);
        });
        DARKENER = registerWeapon("darkener", () -> {
            return new Darkener(2.875, 6040, 3, 0.9F);
        });
        DART_GUN = registerWeapon("dart_gun", () -> {
            return new DartGun(4.675000190734863, 4520, 5, 0.2F);
        });
        DESTRUCTION_RIFLE = registerWeapon("destruction_rifle", () -> {
            return new DestructionRifle(3.125, 2440, 5, 1.0F);
        });
        DISCHARGE_RIFLE = registerWeapon("discharge_rifle", () -> {
            return new DischargeRifle(1.25, 2280, 6, 3.0F);
        });
        DRACO = registerWeapon("draco", () -> {
            return new Draco(3.75, 5560, 4, 0.75F);
        });
        DRAGILATOR = registerWeapon("dragilator", () -> {
            return new Dragilator(1.625, 4480, 2, 1.1F);
        });
        DUSTOMETER = registerWeapon("dustometer", () -> {
            return new Dustometer(5.25, 4720, 6, 0.65F);
        });
        ECHO_GULL = registerWeapon("echo_gull", () -> {
            return new EchoGull(4.375, 2320, 7, 0.85F);
        });
        ELECTINATOR = registerWeapon("electinator", () -> {
            return new Electinator(1.625, 4480, 2, 1.1F);
        });
        FLAME_WRECKER = registerWeapon("flame_wrecker", () -> {
            return new FlameWrecker(1.875, 6120, 2, 1.15F);
        });
        FLAMING_FURY = registerWeapon("flaming_fury", () -> {
            return new FlamingFury(5.125, 3440, 7, 0.6F);
        });
        FLORO_RIFLE = registerWeapon("floro_rifle", () -> {
            return new FloroRifle(3.75, 6000, 4, 0.75F);
        });
        FLOWERS_FURY = registerWeapon("flowers_fury", () -> {
            return new FlowersFury(3.25, 4400, 4, 0.9F);
        });
        FROSTICATOR = registerWeapon("frosticator", () -> {
            return new Frosticator(1.625, 4480, 2, 1.1F);
        });
        GARDENER = registerWeapon("gardener", () -> {
            return new Gardener(1.75, 2520, 3, 1.0F);
        });
        GAUGE_RIFLE = registerWeapon("gauge_rifle", () -> {
            return new GaugeRifle(5.625, 2360, 9, 0.85F);
        });
        GERMINATOR = registerWeapon("germinator", () -> {
            return new Germinator(1.625, 4480, 2, 1.1F);
        });
        GOLDEN_FURY = registerWeapon("golden_fury", () -> {
            return new GoldenFury(6.5, 6080, 7, 0.45F);
        });
        HAPPY_HAUNTER = registerWeapon("happy_haunter", () -> {
            return new HappyHaunter(2.875, 6040, 3, 0.85F);
        });
        HAUNTER_RIFLE = registerWeapon("haunter_rifle", () -> {
            return new HaunterRifle(2.75, 5680, 3, 0.9F);
        });
        HEAT_WAVE = registerWeapon("heat_wave", () -> {
            return new HeatWave(6.050000190734863, 5880, 6, 0.8F);
        });
        HIVER = registerWeapon("hiver", () -> {
            return new Hiver(2.875, 5960, 3, 1.0F);
        });
        HOT_SHOT = registerWeapon("hot_shot", () -> {
            return new HotShot(3.987499952316284, 2320, 6, 1.0F);
        });
        HUNTERS_RIFLE = registerWeapon("hunters_rifle", () -> {
            return new HuntersRifle(5.912499904632568, 3600, 7, 0.9F);
        });
        IOMINATOR = registerWeapon("iominator", () -> {
            return new Iominator(2.875, 6000, 3, 0.95F);
        });
        ION_REVOLVER = registerWeapon("ion_revolver", () -> {
            return new IonRevolver(2.612499952316284, 3720, 3, 1.65F);
        });
        IRO_RIFLE = registerWeapon("iro_rifle", () -> {
            return new IroRifle(3.125, 3640, 4, 0.95F);
        });
        KRILINATOR = registerWeapon("krilinator", () -> {
            return new Krilinator(3.75, 3600, 5, 1.1F);
        });
        LIGHT_IRON = registerWeapon("light_iron", () -> {
            return new LightIron(7.625, 5880, 8, 0.6F);
        });
        LUNAR_ASSAULT_RIFLE = registerWeapon("lunar_assault_rifle", () -> {
            return new LunarAssaultRifle(2.875, 5720, 3, 0.85F);
        });
        MECHANICAL_ASSAULT_RIFLE = registerWeapon("mechanical_assault_rifle", () -> {
            return new MechanicalAssaultRifle(4.625, 3760, 6, 0.85F);
        });
        MEGAGUN = registerWeapon("megagun", () -> {
            return new Megagun(1.0, 6120, 1, 1.1F);
        });
        MIASMA = registerWeapon("miasma", () -> {
            return new Miasma(2.25, 3760, 3, 1.0F);
        });
        MINIGUN = registerWeapon("minigun", () -> {
            return new Minigun(0.625, 2680, 1, 1.5F);
        });
        MINT_MAGNUM = registerWeapon("mint_magnum", () -> {
            return new MintMagnum(1.649999976158142, 3600, 2, 1.25F);
        });
        MK = registerWeapon("mk", () -> {
            return new MK(3.625, 4960, 4, 0.75F);
        });
        MK_FUNG = registerWeapon("mk_fung", () -> {
            return new MKFung(3.75, 5960, 4, 0.75F);
        });
        NETHENETTE_RIFLE = registerWeapon("nethenette_rifle", () -> {
            return new NethenetteRifle(5.087500095367432, 6000, 5, 0.85F);
        });
        NETHENGEIC_SLUGGER = registerWeapon("nethengeic_slugger", () -> {
            return new NethengeicSlugger(6.1875, 3120, 8, 0.75F);
        });
        OVERSHOT = registerWeapon("overshot", () -> {
            return new Overshot(1.625, 3960, 4, 2.1F);
        });
        PRECASIAN_SLUGGER = registerWeapon("precasian_slugger", () -> {
            return new PrecasianSlugger(8.112500190734863, 5960, 8, 0.5F);
        });
        PREDATOR = registerWeapon("predator", () -> {
            return new Predator(6.375, 5920, 7, 0.45F);
        });
        PREDIGUN = registerWeapon("predigun", () -> {
            return new Predigun(1.0, 6120, 1, 1.0F);
        });
        PULSATOR = registerWeapon("pulsator", () -> {
            return new Pulsator(6.25, 4760, 7, 0.5F);
        });
        PURITY_RIFLE = registerWeapon("purity_rifle", () -> {
            return new PurityRifle(4.5, 4800, 5, 0.75F);
        });
        ROCKER_RIFLE = registerWeapon("rocker_rifle", () -> {
            return new RockerRifle(2.5, 2520, 4, 1.0F);
        });
        ROULETTE = registerWeapon("roulette", () -> {
            return new Roulette(6.1875, 5880, 6, 0.85F);
        });
        SHOE_FLINGER = registerWeapon("shoe_flinger", () -> {
            return new ShoeFlinger(6.0, 1600, 6, 1.0F);
        });
        SKULLETTE = registerWeapon("skullette", () -> {
            return new Skullette(6.462500095367432, 6320, 6, 0.85F);
        });
        SKULLIFACT = registerWeapon("skullifact", () -> {
            return new Skullifact(10.0, 6280, 5, 0.45F);
        });
        SPECTACLE = registerWeapon("spectacle", () -> {
            return new Spectacle(2.75, 3520, 4, 0.85F);
        });
        SPINE_GUN = registerWeapon("spine_gun", () -> {
            return new SpineGun(2.875, 6040, 3, 0.85F);
        });
        SQUAD_GUN = registerWeapon("squad_gun", () -> {
            return new SquadGun(2.625, 4840, 3, 0.85F);
        });
        STAMPEDE = registerWeapon("stampede", () -> {
            return new Stampede(3.299999952316284, 1200, 6, 1.0F);
        });
        STORMER = registerWeapon("stormer", () -> {
            return new Stormer(8.0, 3600, 5, 0.45F);
        });
        SUBLIMUS = registerWeapon("sublimus", () -> {
            return new Sublimus(2.75, 6120, 3, 0.9F);
        });
        TIGER_TOMMY = registerWeapon("tiger_tommy", () -> {
            return new TigerTommy(2.875, 5960, 3, 0.7F);
        });
        TOMMY = registerWeapon("tommy", () -> {
            return new Tommy(1.5, 1320, 3, 1.3F);
        });
        VILE_VANQUISHER = registerWeapon("vile_vanquisher", () -> {
            return new VileVanquisher(2.75, 3320, 4, 1.0F);
        });
        WART_GUN = registerWeapon("wart_gun", () -> {
            return new WartGun(4.537499904632568, 2320, 7, 0.2F);
        });
        WRECKER = registerWeapon("wrecker", () -> {
            return new Wrecker(2.375, 3680, 3, 1.1F);
        });
        ABYSSRO = registerWeapon("abyssro", () -> {
            return new BaseShotgun(18.5, 2, 1100, 46, 0.3F, 0.2F);
        });
        AMPLIFIER = registerWeapon("amplifier", () -> {
            return new Amplifier(9.0, 5, 1100, 56, 0.2F, 0.7F);
        });
        BLAST_BARREL = registerWeapon("blast_barrel", () -> {
            return new BlastBarrel(13.0, 3, 740, 62, 0.4F, 0.4F);
        });
        BLUE_BARREL = registerWeapon("blue_barrel", () -> {
            return new BaseShotgun(14.5, 3, 1120, 54, 0.4F, 0.4F);
        });
        BOULDER = registerWeapon("boulder", () -> {
            return new BaseShotgun(11.5, 4, 1080, 58, 0.4F, 0.5F);
        });
        BROWN_BLASTER = registerWeapon("brown_blaster", () -> {
            return new BaseShotgun(6.0, 3, 300, 54, 0.4F, 0.9F);
        });
        DEMOLISHER = registerWeapon("demolisher", () -> {
            return new Demolisher(8.5, 4, 730, 58, 0.4F, 0.65F);
        });
        DESTRUCTION_SHOTGUN = registerWeapon("destruction_shotgun", () -> {
            return new DestructionShotgun(10.5, 3, 690, 56, 0.4F, 0.45F);
        });
        DISCHARGE_SHOTGUN = registerWeapon("discharge_shotgun", () -> {
            return new DischargeShotgun(0.0, 4, 160, 55, 0.0F, 13.0F);
        });
        GIMMICK = registerWeapon("gimmick", () -> {
            return new Gimmick(4.5, 10, 800, 68, 0.2F, 1.3F);
        });
        GINGER_BLASTER = registerWeapon("ginger_blaster", () -> {
            return new GingerBlaster(7.0, 5, 720, 60, 0.4F, 0.75F);
        });
        LONG_SHOT = registerWeapon("long_shot", () -> {
            return new LongShot(15.5, 2, 730, 54, 0.2F, 0.3F);
        });
        MECHYRO = registerWeapon("mechyro", () -> {
            return new Mechyro(13.0, 2, 720, 46, 0.3F, 0.3F);
        });
        PURITY_SHOTGUN = registerWeapon("purity_shotgun", () -> {
            return new PurityShotgun(14.0, 3, 1090, 56, 0.4F, 0.25F);
        });
        PURPLE_PUNISHER = registerWeapon("purple_punisher", () -> {
            return new BaseShotgun(20.5, 2, 1120, 52, 0.785F, 0.2F);
        });
        RED_ROCKET = registerWeapon("red_rocket", () -> {
            return new BaseShotgun(11.5, 2, 500, 52, 0.785F, 0.4F);
        });
        VIVO = registerWeapon("vivo", () -> {
            return new Vivo(7.0, 3, 710, 36, 0.3F, 0.8F);
        });
        BARON_SSR = registerWeapon("baron_ssr", () -> {
            return new BaronSSR(35.0, 240, 88, 0.05F);
        });
        BAYONETTE_SR = registerWeapon("bayonette_sr", () -> {
            return new BayonetteSR(33.0, 300, 86, 0.06F);
        });
        BOLT_RIFLE = registerWeapon("bolt_rifle", () -> {
            return new BoltRifle(30.0, 100, 100, 0.06F);
        });
        CAMO_RIFLE = registerWeapon("camo_rifle", () -> {
            return new CamoRifle(31.5, 150, 84, 0.06F);
        });
        CLOWN_CRACKER = registerWeapon("clown_cracker", () -> {
            return new ClownCracker(33.5, 305, 84, 0.05F);
        });
        CLOWNIMATOR = registerWeapon("clownimator", () -> {
            return new Clownimator(49.5, 545, 95, 0.035F);
        });
        CRYSTANEER = registerWeapon("crystaneer", () -> {
            return new Crystaneer(37.5, 345, 88, 0.042F);
        });
        DARK_BEAST = registerWeapon("dark_beast", () -> {
            return new DarkBeast(50.5, 560, 96, 0.035F);
        });
        DEADLOCK = registerWeapon("deadlock", () -> {
            return new Deadlock(25.5, 120, 90, 0.09F);
        });
        DECIMATOR = registerWeapon("decimator", () -> {
            return new Decimator(33.0, 180, 95, 0.05F);
        });
        DISCHARGE_SNIPER = registerWeapon("discharge_sniper", () -> {
            return new DischargeSniper(10.0, 540, 24, 0.4F);
        });
        DUAL_SIGHT = registerWeapon("dual_sight", () -> {
            return new DualSight(38.5, 350, 91, 0.042F);
        });
        DUSTER = registerWeapon("duster", () -> {
            return new Duster(21.0, 170, 60, 0.09F);
        });
        FLORO500 = registerWeapon("floro500", () -> {
            return new Floro500(37.0, 550, 70, 0.042F);
        });
        HEAD_HUNTER = registerWeapon("head_hunter", () -> {
            return new HeadHunter(41.5, 380, 92, 0.035F);
        });
        HIVE_CRACKER = registerWeapon("hive_cracker", () -> {
            return new HiveCracker(44.0, 555, 84, 0.035F);
        });
        KA_500 = registerWeapon("ka_500", () -> {
            return new Ka500(30.0, 360, 70, 0.06F);
        });
        MARK_MAKER = registerWeapon("mark_maker", () -> {
            return new MarkMaker(36.5, 530, 70, 0.05F);
        });
        MINERAL = registerWeapon("mineral", () -> {
            return new Mineral(58.5, 560, 112, 0.035F);
        });
        MONSTER = registerWeapon("monster", () -> {
            return new Monster(38.0, 250, 96, 0.05F);
        });
        MOON_MAKER = registerWeapon("moon_maker", () -> {
            return new MoonMaker(38.5, 570, 70, 0.05F);
        });
        ROSID_RIFLE = registerWeapon("rosid_rifle", () -> {
            return new RosidRifle(45.5, 405, 102, 0.035F);
        });
        SABBATH = registerWeapon("sabbath", () -> {
            return new Sabbath(44.0, 450, 97, 0.035F);
        });
        SLUDGE_SNIPER = registerWeapon("sludge_sniper", () -> {
            return new SludgeSniper(37.0, 350, 87, 0.05F);
        });
        SWEET_TOOTH = registerWeapon("sweet_tooth", () -> {
            return new SweetTooth(32.5, 460, 77, 0.05F);
        });
        TERMINATOR = registerWeapon("terminator", () -> {
            return new Terminator(53.0, 470, 112, 0.035F);
        });
        VIPER_1 = registerWeapon("viper_1", () -> {
            return new Viper1(30.0, 185, 89, 0.06F);
        });
        ANCIENT_BOMBER = registerWeapon("ancient_bomber", () -> {
            return new AncientBomber(23.5, 855, 28, 0.175F);
        });
        ANCIENT_DISCHARGER = registerWeapon("ancient_discharger", () -> {
            return new AncientDischarger(0.0, 850, 20, 13.0F);
        });
        AQUA_CANNON = registerWeapon("aqua_cannon", () -> {
            return new AquaCannon(14.0, 300, 35, 0.21F);
        });
        BALLOON_BOMBER = registerWeapon("balloon_bomber", () -> {
            return new BalloonBomber(12.5, 505, 24, 0.22F);
        });
        BIG_BLAST = registerWeapon("big_blast", () -> {
            return new BigBlast(28.0, 550, 60, 0.12F);
        });
        BLAST_CANNON = registerWeapon("blast_cannon", () -> {
            return new BlastCannon(17.0, 510, 27, 0.21F);
        });
        BLISSFUL_BLAST = registerWeapon("blissful_blast", () -> {
            return new BlissfulBlast(33.5, 835, 60, 0.09F);
        });
        BOMB_LAUNCHER = registerWeapon("bomb_launcher", () -> {
            return new BombLauncher(23.0, 840, 27, 0.175F);
        });
        BOOM_BOOM = registerWeapon("boom_boom", () -> {
            return new BoomBoom(18.5, 390, 36, 0.21F);
        });
        BOOM_CANNON = registerWeapon("boom_cannon", () -> {
            return new BoomCannon(17.5, 510, 28, 0.21F);
        });
        BOULDER_BOMBER = registerWeapon("boulder_bomber", () -> {
            return new BoulderBomber(18.5, 475, 62, 0.21F);
        });
        BOZO_BLASTER = registerWeapon("bozo_blaster", () -> {
            return new BozoBlaster(12.5, 505, 20, 0.21F);
        });
        BULB_CANNON = registerWeapon("bulb_cannon", () -> {
            return new BulbCannon(25.0, 860, 30, 0.15F);
        });
        CARROT_CANNON = registerWeapon("carrot_cannon", () -> {
            return new CarrotCannon(10.0, 520, 16, 0.4F);
        });
        CLOWN_CANNON = registerWeapon("clown_cannon", () -> {
            return new ClownCannon(22.5, 570, 32, 0.18F);
        });
        CLOWNO_PULSE = registerWeapon("clowno_pulse", () -> {
            return new ClownoPulse(35.5, 845, 42, 0.09F);
        });
        CORAL_CANNON = registerWeapon("coral_cannon", () -> {
            return new CoralCannon(12.0, 300, 15, 0.35F);
        });
        DISCHARGE_CANNON = registerWeapon("discharge_cannon", () -> {
            return new DischargeCannon(0.0, 400, 20, 6.0F);
        });
        ENERGY_CANNON = registerWeapon("energy_cannon", () -> {
            return new EnergyCannon(18.5, 610, 25, 0.21F);
        });
        EREBON_STICKLER = registerWeapon("erebon_stickler", () -> {
            return new ErebonStickler(35.0, 750, 44, 0.09F);
        });
        FLORO_RPG = registerWeapon("floro_rpg", () -> {
            return new FloroRPG(18.0, 830, 50, 0.21F);
        });
        FLOWER_CANNON = registerWeapon("flower_cannon", () -> {
            return new FlowerCannon(14.5, 510, 23, 0.21F);
        });
        FUNGAL_CANNON = registerWeapon("fungal_cannon", () -> {
            return new FungalCannon(19.5, 850, 23, 0.2F);
        });
        GHAST_BLASTER = registerWeapon("ghast_blaster", () -> {
            return new GhastBlaster(20.0, 600, 28, 0.2F);
        });
        GHOUL_CANNON = registerWeapon("ghoul_cannon", () -> {
            return new GhoulCannon(25.5, 590, 34, 0.15F);
        });
        GIGA_CANNON = registerWeapon("giga_cannon", () -> {
            return new GigaCannon(25.5, 700, 30, 0.15F);
        });
        GOLDER_BOMBER = registerWeapon("golder_bomber", () -> {
            return new GolderBomber(26.0, 840, 62, 0.15F);
        });
        HIVE_BLASTER = registerWeapon("hive_blaster", () -> {
            return new HiveBlaster(20.5, 850, 24, 0.2F);
        });
        HIVE_HOWITZER = registerWeapon("hive_howitzer", () -> {
            return new HiveHowitzer(13.0, 375, 24, 0.35F);
        });
        IRO_CANNON = registerWeapon("iro_cannon", () -> {
            return new IroCannon(25.5, 580, 36, 0.15F);
        });
        JACK_FUNGER = registerWeapon("jack_funger", () -> {
            return new JackFunger(17.0, 860, 20, 0.21F);
        });
        JACK_ROCKER = registerWeapon("jack_rocker", () -> {
            return new JackRocker(10.5, 400, 20, 0.4F);
        });
        LUXON_STICKLER = registerWeapon("luxon_stickler", () -> {
            return new LuxonStickler(35.0, 750, 44, 0.09F);
        });
        MECHA_CANNON = registerWeapon("mecha_cannon", () -> {
            return new MechaCannon(26.5, 525, 42, 0.15F);
        });
        MINI_CANNON = registerWeapon("mini_cannon", () -> {
            return new MiniCannon(15.0, 415, 30, 0.21F);
        });
        MISSILE_MAKER = registerWeapon("missile_maker", () -> {
            return new MissileMaker(0.0, 495, 60, 7.0F);
        });
        MOON_CANNON = registerWeapon("moon_cannon", () -> {
            return new MoonCannon(21.0, 855, 25, 0.18F);
        });
        PLUTON_STICKLER = registerWeapon("pluton_stickler", () -> {
            return new PlutonStickler(35.0, 750, 44, 0.09F);
        });
        PREDATORIAN_BLASTER = registerWeapon("predatorian_blaster", () -> {
            return new PredatorianBlaster(23.5, 845, 28, 0.175F);
        });
        PULSE_CANNON = registerWeapon("pulse_cannon", () -> {
            return new PulseCannon(26.0, 510, 42, 0.15F);
        });
        RPG = registerWeapon("rpg", () -> {
            return new RPG(14.0, 320, 50, 0.35F);
        });
        SELYAN_STICKLER = registerWeapon("selyan_stickler", () -> {
            return new SelyanStickler(35.0, 750, 44, 0.09F);
        });
        SHADOW_BLASTER = registerWeapon("shadow_blaster", () -> {
            return new ShadowBlaster(15.0, 515, 24, 0.21F);
        });
        SHYRE_BLASTER = registerWeapon("shyre_blaster", () -> {
            return new ShyreBlaster(25.5, 850, 30, 0.15F);
        });
        SMILE_BLASTER = registerWeapon("smile_blaster", () -> {
            return new SmileBlaster(20.5, 840, 24, 0.2F);
        });
        SUPER_CANNON = registerWeapon("super_cannon", () -> {
            return new SuperCannon(18.5, 510, 30, 0.21F);
        });
        ULTRA_CANNON = registerWeapon("ultra_cannon", () -> {
            return new UltraCannon(22.5, 605, 30, 0.18F);
        });
        VOX_CANNON = registerWeapon("vox_cannon", () -> {
            return new VoxCannon(15.0, 440, 26, 0.21F);
        });
        WATER_BALLOON_BOMBER = registerWeapon("water_balloon_bomber", () -> {
            return new WaterBalloonBomber(20.5, 855, 24, 0.2F);
        });
        WITHER_CANNON = registerWeapon("wither_cannon", () -> {
            return new WitherCannon(17.0, 460, 30, 0.21F);
        });
        GRENADE = registerWeapon("grenade", Grenade::new);
        SLICE_STAR = registerWeapon("slice_star", SliceStar::new);
        CHAKRAM = registerWeapon("chakram", Chakram::new);
        GOO_BALL = registerWeapon("goo_ball", GooBall::new);
        VULKRAM = registerWeapon("vulkram", Vulkram::new);
        HELLFIRE = registerWeapon("hellfire", Hellfire::new);
        RUNIC_BOMB = registerWeapon("runic_bomb", RunicBomb::new);
        HARDENED_PARAPIRANHA = registerWeapon("hardened_parapiranha", HardenedParapiranha::new);
        VULCANE = registerWeapon("vulcane", () -> {
            return new Vulcane(10.0, 50);
        });
        BATTLE_VULCANE = registerWeapon("battle_vulcane", () -> {
            return new BattleVulcane(10.0, 75);
        });
        EQUALITY_VULCANE = registerWeapon("equality_vulcane", () -> {
            return new EqualityVulcane(10.0, 75);
        });
        FIRE_VULCANE = registerWeapon("fire_vulcane", () -> {
            return new FireVulcane(10.0, 75);
        });
        IMPAIRMENT_VULCANE = registerWeapon("impairment_vulcane", () -> {
            return new ImpairmentVulcane(10.0, 75);
        });
        POISON_VULCANE = registerWeapon("poison_vulcane", () -> {
            return new PoisonVulcane(10.0, 75);
        });
        POWER_VULCANE = registerWeapon("power_vulcane", () -> {
            return new PowerVulcane(10.0, 75);
        });
        WITHER_VULCANE = registerWeapon("wither_vulcane", () -> {
            return new WitherVulcane(10.0, 75);
        });
        ALACRITY_BOW = registerWeapon("alacrity_bow", () -> {
            return new BaseBow(9.5, 1.0F, 600);
        });
        ANCIENT_BOW = registerWeapon("ancient_bow", () -> {
            return new BaseBow(22.0, 1.0F, 1510);
        });
        ATLANTIC_BOW = registerWeapon("atlantic_bow", () -> {
            return new BaseBow(24.5, 0.9F, 1480);
        });
        BARON_BOW = registerWeapon("baron_bow", () -> {
            return new BaronBow(12.0, 1.1F, 3000);
        });
        BOREIC_BOW = registerWeapon("boreic_bow", () -> {
            return new BoreicBow(14.0, 1.3F, 1190);
        });
        DAYBREAKER_BOW = registerWeapon("daybreaker_bow", () -> {
            return new DaybreakerBow(17.5, 1.0F, 1180);
        });
        DEEP_BOW = registerWeapon("deep_bow", () -> {
            return new DeepBow(15.5, 0.85F, 700);
        });
        EXPLOSIVE_BOW = registerWeapon("explosive_bow", () -> {
            return new ExplosiveBow(17.0, 0.8F, 900);
        });
        HAUNTED_BOW = registerWeapon("haunted_bow", () -> {
            return new HauntedBow(17.0, 1.0F, 920);
        });
        ICE_BOW = registerWeapon("ice_bow", () -> {
            return new IceBow(8.5, 1.0F, 580);
        });
        INFERNAL_BOW = registerWeapon("infernal_bow", () -> {
            return new InfernalBow(11.5, 1.0F, 710);
        });
        JUSTICE_BOW = registerWeapon("justice_bow", () -> {
            return new JusticeBow(14.5, 1.0F, 920);
        });
        LUNAR_BOW = registerWeapon("lunar_bow", () -> {
            return new LunarBow(17.5, 1.0F, 900);
        });
        MECHA_BOW = registerWeapon("mecha_bow", () -> {
            return new BaseBow(20.5, 0.75F, 930);
        });
        NIGHTMARE_BOW = registerWeapon("nightmare_bow", () -> {
            return new NightmareBow(13.5, 1.0F, 890);
        });
        POISON_BOW = registerWeapon("poison_bow", () -> {
            return new PoisonBow(15.0, 1.0F, 950);
        });
        PREDATIOUS_BOW = registerWeapon("predatious_bow", () -> {
            return new PredatiousBow(14.0, 0.9F, 690);
        });
        PRIMORDIAL_BOW = registerWeapon("primordial_bow", () -> {
            return new PrimordialBow(18.5, 1.0F, 1350);
        });
        ROSIDIAN_BOW = registerWeapon("rosidian_bow", () -> {
            return new RosidianBow(15.5, 1.0F, 900);
        });
        RUNIC_BOW = registerWeapon("runic_bow", () -> {
            return new RunicBow(20.5, 1.0F, 1320);
        });
        SCREAMER_BOW = registerWeapon("screamer_bow", () -> {
            return new ScreamerBow(12.0, 1.0F, 665);
        });
        SHYREGEM_BOW = registerWeapon("shyregem_bow", () -> {
            return new ShyregemBow(7.5, 3.0F, 1500);
        });
        SKELETAL_BOW = registerWeapon("skeletal_bow", () -> {
            return new BaseBow(8.5, 1.5F, 720);
        });
        SKYDRIVER_BOW = registerWeapon("skydriver_bow", () -> {
            return new SkydriverBow(14.5, 1.0F, 850);
        });
        SLINGSHOT = registerWeapon("slingshot", () -> {
            return new Slingshot(17.0, 1.0F, 1200);
        });
        SOULFIRE_BOW = registerWeapon("soulfire_bow", () -> {
            return new SoulfireBow(23.5, 0.75F, 1100);
        });
        SPECTRAL_BOW = registerWeapon("spectral_bow", () -> {
            return new SpectralBow(12.0, 1.3F, 900);
        });
        SPEED_BOW = registerWeapon("speed_bow", () -> {
            return new BaseBow(8.0, 2.0F, 930);
        });
        SUNSHINE_BOW = registerWeapon("sunshine_bow", () -> {
            return new SunshineBow(27.0, 0.8F, 1530);
        });
        TOXIN_BOW = registerWeapon("toxin_bow", () -> {
            return new ToxinBow(14.0, 0.85F, 900);
        });
        VOID_BOW = registerWeapon("void_bow", () -> {
            return new BaseBow(9.0, 1.2F, 600);
        });
        WEAKEN_BOW = registerWeapon("weaken_bow", () -> {
            return new WeakenBow(12.5, 1.0F, 700);
        });
        WITHER_BOW = registerWeapon("wither_bow", () -> {
            return new WitherBow(13.5, 1.0F, 835);
        });
        CORAL_CROSSBOW = registerWeapon("coral_crossbow", () -> {
            return new CoralCrossbow(17.0, 1080);
        });
        LUNAR_CROSSBOW = registerWeapon("lunar_crossbow", () -> {
            return new LunarCrossbow(16.5, 1100);
        });
        MECHA_CROSSBOW = registerWeapon("mecha_crossbow", () -> {
            return new BaseCrossbow(15.0, 930);
        });
        PYRO_CROSSBOW = registerWeapon("pyro_crossbow", () -> {
            return new PyroCrossbow(12.0, 800);
        });
        ROSIDIAN_CROSSBOW = registerWeapon("rosidian_crossbow", () -> {
            return new RosidianCrossbow(14.5, 900);
        });
        SKELETAL_CROSSBOW = registerWeapon("skeletal_crossbow", () -> {
            return new BaseCrossbow(12.5, 860);
        });
        SPECTRAL_CROSSBOW = registerWeapon("spectral_crossbow", () -> {
            return new SpectralCrossbow(15.0, 1030);
        });
        TROLLS_CROSSBOW = registerWeapon("trolls_crossbow", () -> {
            return new BaseCrossbow(10.5, 700);
        });
        VIRAL_CROSSBOW = registerWeapon("viral_crossbow", () -> {
            return new ViralCrossbow(11.0, 780);
        });
        AQUATIC_STAFF = registerWeapon("aquatic_staff", () -> {
            return new AquaticStaff(1220);
        });
        ATLANTIC_STAFF = registerWeapon("atlantic_staff", () -> {
            return new AtlanticStaff(1250);
        });
        BARON_STAFF = registerWeapon("baron_staff", () -> {
            return new BaronStaff(1200);
        });
        CANDY_STAFF = registerWeapon("candy_staff", () -> {
            return new CandyStaff(950);
        });
        CELESTIAL_STAFF = registerWeapon("celestial_staff", () -> {
            return new CelestialStaff(1150);
        });
        CONCUSSION_STAFF = registerWeapon("concussion_staff", () -> {
            return new ConcussionStaff(1150);
        });
        CORAL_STAFF = registerWeapon("coral_staff", () -> {
            return new CoralStaff(950);
        });
        CRYSTAL_STAFF = registerWeapon("crystal_staff", () -> {
            return new CrystalStaff(1230);
        });
        CRYSTIK_STAFF = registerWeapon("crystik_staff", () -> {
            return new CrystikStaff(1140);
        });
        CRYSTON_STAFF = registerWeapon("cryston_staff", () -> {
            return new CrystonStaff(1240);
        });
        DESTRUCTION_STAFF = registerWeapon("destruction_staff", () -> {
            return new DestructionStaff(1170);
        });
        EMBER_STAFF = registerWeapon("ember_staff", () -> {
            return new EmberStaff(1400);
        });
        EVERFIGHT_STAFF = registerWeapon("everfight_staff", () -> {
            return new EverfightStaff(1050);
        });
        EVERMIGHT_STAFF = registerWeapon("evermight_staff", () -> {
            return new EvermightStaff(1050);
        });
        FIRE_STAFF = registerWeapon("fire_staff", () -> {
            return new FireStaff(850);
        });
        FIREFLY_STAFF = registerWeapon("firefly_staff", () -> {
            return new FireflyStaff(1230);
        });
        FIRESTORM_STAFF = registerWeapon("firestorm_staff", () -> {
            return new FirestormStaff(990);
        });
        FUNGAL_STAFF = registerWeapon("fungal_staff", () -> {
            return new FungalStaff(1130);
        });
        GHOUL_STAFF = registerWeapon("ghoul_staff", () -> {
            return new GhoulStaff(400);
        });
        HAUNTERS_STAFF = registerWeapon("haunters_staff", () -> {
            return new HauntersStaff(1225);
        });
        JOKER_STAFF = registerWeapon("joker_staff", () -> {
            return new JokerStaff(300);
        });
        KAIYU_STAFF = registerWeapon("kaiyu_staff", () -> {
            return new KaiyuStaff(900);
        });
        LIGHTNING_STAFF = registerWeapon("lightning_staff", () -> {
            return new LightningStaff(1070);
        });
        LIGHTSHINE = registerWeapon("lightshine", () -> {
            return new Lightshine(700);
        });
        LUNAR_STAFF = registerWeapon("lunar_staff", () -> {
            return new LunarStaff(1250);
        });
        LYONIC_STAFF = registerWeapon("lyonic_staff", () -> {
            return new LyonicStaff(1100);
        });
        MECHA_STAFF = registerWeapon("mecha_staff", () -> {
            return new MechaStaff(1110);
        });
        METEOR_STAFF = registerWeapon("meteor_staff", () -> {
            return new MeteorStaff(1190);
        });
        MOONLIGHT_STAFF = registerWeapon("moonlight_staff", () -> {
            return new MoonlightStaff(1130);
        });
        NATURE_STAFF = registerWeapon("nature_staff", () -> {
            return new NatureStaff(1450);
        });
        NIGHTMARE_STAFF = registerWeapon("nightmare_staff", () -> {
            return new NightmareStaff(1200);
        });
        NOXIOUS_STAFF = registerWeapon("noxious_staff", () -> {
            return new NoxiousStaff(1210);
        });
        PHANTOM_STAFF = registerWeapon("phantom_staff", () -> {
            return new PhantomStaff(1060);
        });
        POISON_STAFF = registerWeapon("poison_staff", () -> {
            return new PoisonStaff(850);
        });
        POWER_STAFF = registerWeapon("power_staff", () -> {
            return new PowerStaff(1010);
        });
        PRIMORDIAL_STAFF = registerWeapon("primordial_staff", () -> {
            return new PrimordialStaff(1230);
        });
        REEF_STAFF = registerWeapon("reef_staff", () -> {
            return new ReefStaff(1230);
        });
        REJUVENATION_STAFF = registerWeapon("rejuvenation_staff", () -> {
            return new RejuvenationStaff(870);
        });
        ROSIDIAN_STAFF = registerWeapon("rosidian_staff", () -> {
            return new RosidianStaff(1180);
        });
        RUNIC_STAFF = registerWeapon("runic_staff", () -> {
            return new RunicStaff(1000);
        });
        SHOW_STAFF = registerWeapon("show_staff", () -> {
            return new ShowStaff(900);
        });
        SHYRE_STAFF = registerWeapon("shyre_staff", () -> {
            return new ShyreStaff(1380);
        });
        SKY_STAFF = registerWeapon("sky_staff", () -> {
            return new SkyStaff(1480);
        });
        STRIKER_STAFF = registerWeapon("striker_staff", () -> {
            return new StrikerStaff(850);
        });
        SUN_STAFF = registerWeapon("sun_staff", () -> {
            return new SunStaff(960);
        });
        SURGE_STAFF = registerWeapon("surge_staff", () -> {
            return new SurgeStaff(1270);
        });
        TANGLE_STAFF = registerWeapon("tangle_staff", () -> {
            return new TangleStaff(1140);
        });
        ULTIMATUM_STAFF = registerWeapon("ultimatum_staff", () -> {
            return new UltimatumStaff(750);
        });
        UNDERWORLD_STAFF = registerWeapon("underworld_staff", () -> {
            return new UnderworldStaff(1140);
        });
        WARLOCK_STAFF = registerWeapon("warlock_staff", () -> {
            return new WarlockStaff(1050);
        });
        WATER_STAFF = registerWeapon("water_staff", () -> {
            return new WaterStaff(850);
        });
        WEB_STAFF = registerWeapon("web_staff", () -> {
            return new WebStaff(1420);
        });
        WIND_STAFF = registerWeapon("wind_staff", () -> {
            return new WindStaff(850);
        });
        WITHER_STAFF = registerWeapon("wither_staff", () -> {
            return new WitherStaff(850);
        });
        WIZARDS_STAFF = registerWeapon("wizards_staff", () -> {
            return new WizardsStaff(800);
        });
        APOCO_SHOWER = registerWeapon("apoco_shower", () -> {
            return new ApocoShower(0.0, 3760, 10, 80.0F);
        });
        ATOMIZER = registerWeapon("atomizer", () -> {
            return new Atomizer(11.0, 3150, 8, 15.0F);
        });
        BEAMER = registerWeapon("beamer", () -> {
            return new Beamer(1.0, 3240, 1, 3.0F);
        });
        BLAST_CHILLER = registerWeapon("blast_chiller", () -> {
            return new BlastChiller(4.0, 1750, 6, 20.0F);
        });
        BLOOD_DRAINER = registerWeapon("blood_drainer", () -> {
            return new BloodDrainer(0.10000000149011612, 2750, 1, 2.5F);
        });
        BONE_BLASTER = registerWeapon("bone_blaster", () -> {
            return new BoneBlaster(6.0, 2430, 6, 20.0F);
        });
        BUBBLE_HORN = registerWeapon("bubble_horn", () -> {
            return new BubbleHorn(11.0, 3200, 8, 15.5F);
        });
        COLOUR_CANNON = registerWeapon("colour_cannon", () -> {
            return new ColourCannon(45.0, 1000, 40, 110.0F);
        });
        CONFETTI_CANNON = registerWeapon("confetti_cannon", () -> {
            return new ConfettiCannon(0.0, 1000, 10, 0.0F);
        });
        CONFETTI_CLUSTER = registerWeapon("confetti_cluster", () -> {
            return new ConfettiCluster(0.0, 1500, 5, 0.0F);
        });
        DARK_DESTROYER = registerWeapon("dark_destroyer", () -> {
            return new DarkDestroyer(0.0, 3180, 100, 80.0F);
        });
        DARKLY_GUSTER = registerWeapon("darkly_guster", () -> {
            return new DarklyGuster(8.0, 3790, 5, 7.0F);
        });
        DEATH_RAY = registerWeapon("death_ray", () -> {
            return new DeathRay(19.0, 3840, 12, 17.0F);
        });
        DOOM_BRINGER = registerWeapon("doom_bringer", () -> {
            return new DoomBringer(0.0, 2820, 3, 15.5F);
        });
        ERADICATOR = registerWeapon("eradicator", () -> {
            return new Eradicator(1.0, 2790, 1, 2.0F);
        });
        EXPERIMENT_W_801 = registerWeapon("experiment_w_801", () -> {
            return new ExperimentW801(37.0, 5000, 50, 90.0F);
        });
        FLOWERCORNE = registerWeapon("flowercorne", () -> {
            return new Flowercorne(2.5, 2910, 4, 20.0F);
        });
        FRAGMENT = registerWeapon("fragment", () -> {
            return new Fragment(3.0, 3830, 2, 2.5F);
        });
        FROSTER = registerWeapon("froster", () -> {
            return new Froster(3.5, 2800, 3, 7.5F);
        });
        GAS_BLASTER = registerWeapon("gas_blaster", () -> {
            return new GasBlaster(0.0, 2860, 1, 0.5F);
        });
        GHOUL_GASSER = registerWeapon("ghoul_gasser", () -> {
            return new GhoulGasser(0.0, 3210, 1, 1.0F);
        });
        GOLD_BRINGER = registerWeapon("gold_bringer", () -> {
            return new GoldBringer(0.0, 3830, 3, 12.5F);
        });
        GRAVITY_BLASTER = registerWeapon("gravity_blaster", () -> {
            return new GravityBlaster(4.0, 600, 1, 120.0F);
        });
        HELL_HORN = registerWeapon("hell_horn", () -> {
            return new HellHorn(12.5, 3800, 8, 11.5F);
        });
        ILLUSION_REVOLVER = registerWeapon("illusion_revolver", () -> {
            return new IllusionRevolver(14.0, 3200, 10, 20.0F);
        });
        ILLUSION_SMG = registerWeapon("illusion_smg", () -> {
            return new IllusionSMG(4.0, 3240, 3, 7.0F);
        });
        ION_BLASTER = registerWeapon("ion_blaster", () -> {
            return new IonBlaster(13.0, 2810, 11, 28.5F);
        });
        IRO_MINER = registerWeapon("iro_miner", () -> {
            return new IroMiner(4.0, 3060, 4, 7.0F);
        });
        LASER_BLASTER = registerWeapon("laser_blaster", () -> {
            return new LaserBlaster(1.2000000476837158, 2840, 1, 2.5F);
        });
        LIGHT_BLASTER = registerWeapon("light_blaster", () -> {
            return new LightBlaster(22.5, 3810, 14, 20.5F);
        });
        LIGHT_SPARK = registerWeapon("light_spark", () -> {
            return new LightSpark(0.0, 7, 1, 0.0F);
        });
        LUNA_BLASTER = registerWeapon("luna_blaster", () -> {
            return new LunaBlaster(3.0, 3200, 2, 4.0F);
        });
        MECHA_BLASTER = registerWeapon("mecha_blaster", () -> {
            return new MechaBlaster(36.0, 2770, 30, 80.0F);
        });
        MIND_BLASTER = registerWeapon("mind_blaster", () -> {
            return new MindBlaster(19.5, 3180, 14, 27.5F);
        });
        MOON_DESTROYER = registerWeapon("moon_destroyer", () -> {
            return new MoonDestroyer(0.0, 3750, 100, 60.0F);
        });
        MOON_SHINER = registerWeapon("moon_shiner", () -> {
            return new MoonShiner(3.5, 1810, 5, 17.5F);
        });
        ODIOUS = registerWeapon("odious", () -> {
            return new Odious(1.0, 3190, 1, 1.5F);
        });
        ORBOCRON = registerWeapon("orbocron", () -> {
            return new Orbocron(18.5, 3230, 13, 26.5F);
        });
        PARALYZER = registerWeapon("paralyzer", () -> {
            return new Paralyzer(1.5, 3900, 1, 1.5F);
        });
        PARTY_POPPER = registerWeapon("party_popper", () -> {
            return new PartyPopper(1.5, 2810, 1, 3.0F);
        });
        POISON_PLUNGER = registerWeapon("poison_plunger", () -> {
            return new PoisonPlunger(0.0, 2600, 20, 50.0F);
        });
        POWER_RAY = registerWeapon("power_ray", () -> {
            return new PowerRay(12.5, 2490, 12, 38.5F);
        });
        PROTON = registerWeapon("proton", () -> {
            return new Proton(2.5, 3000, 2, 4.5F);
        });
        REEFER = registerWeapon("reefer", () -> {
            return new Reefer(5.0, 3600, 4, 7.0F);
        });
        REVOLUTION = registerWeapon("revolution", () -> {
            return new Revolution(0.0, 2800, 20, 66.0F);
        });
        SEAOCRON = registerWeapon("seaocron", () -> {
            return new Seaocron(20.5, 3800, 13, 18.5F);
        });
        SKULLO_BLASTER = registerWeapon("skullo_blaster", () -> {
            return new SkulloBlaster(3.5, 3780, 2, 3.0F);
        });
        SOUL_DRAINER = registerWeapon("soul_drainer", () -> {
            return new SoulDrainer(0.20000000298023224, 3770, 1, 2.0F);
        });
        SOUL_SPARK = registerWeapon("soul_spark", () -> {
            return new SoulSpark(0.0, 5, 1, 0.0F);
        });
        SOUL_STORM = registerWeapon("soul_storm", () -> {
            return new SoulStorm(1.5, 3190, 1, 2.0F);
        });
        SPIRIT_SHOWER = registerWeapon("spirit_shower", () -> {
            return new SpiritShower(0.0, 2870, 10, 100.0F);
        });
        SWARMOTRON = registerWeapon("swarmotron", () -> {
            return new Swarmotron(2.0, 2400, 12, 33.0F);
        });
        TOXIC_TERRORIZER = registerWeapon("toxic_terrorizer", () -> {
            return new ToxicTerrorizer(0.0, 2760, 7, 22.0F);
        });
        VORTEX_BLASTER = registerWeapon("vortex_blaster", () -> {
            return new VortexBlaster(0.0, 2870, 1, 130.0F);
        });
        WHIMSY_WINDER = registerWeapon("whimsy_winder", () -> {
            return new WhimsyWinder(6.0, 2830, 5, 13.0F);
        });
        WITHERS_WRATH = registerWeapon("withers_wrath", () -> {
            return new WithersWrath(7.5, 800, 7, 21.0F);
        });
    }
}
