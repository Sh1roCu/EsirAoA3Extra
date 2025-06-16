package cn.sh1rocu.esiraoa3extra.util;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import com.google.gson.JsonParseException;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.tslat.aoa3.player.skill.*;
import net.tslat.aoa3.util.EntityUtil;
import shadows.apotheosis.ApotheosisObjects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EsirUtil {
    public static final List<String> ENCHANTMENTS_BLACKLIST = Arrays.asList(
            Enchantments.FROST_WALKER.getDescriptionId(),
            ApotheosisObjects.DEPTH_MINER.getDescriptionId(),
            ApotheosisObjects.SEA_INFUSION.getDescriptionId(),
            ApotheosisObjects.HELL_INFUSION.getDescriptionId(),
            ApotheosisObjects.NATURES_BLESSING.getDescriptionId(),
            ApotheosisObjects.TRUE_INFINITY.getDescriptionId(),
            ApotheosisObjects.REBOUNDING.getDescriptionId()
            //ApotheosisObjects.CAPTURING.getDescriptionId()
    );

    private static final String FARMING_MODIFIER_UUID = "ecdc4919-fbc0-fafd-4aca-c41aabdd8013";
    private static final String DEXTERITY_MODIFIER_UUID = "0b749ea5-c17b-2f76-d7e6-81bd006299f1";
    private static final String HAULING_MODIFIER_UUID = "bed50d2a-8e67-d4f3-1759-6e8938c1891e";
    private static final String EXTRACTION_MODIFIER_UUID = "0450e8e0-cb28-cc59-e8a9-43459a8e2ff7";

    private static final float BROKEN = 0.2f;
    private static final float SAME = 4.8f;
    private static final int DECREASE = 5;

    private static final float CRITICAL_HIT_RATE = 0.05f;

    public static void applyAoASkillBuff(AoASkill.Instance skill) {
        String uuid = "";
        String modifierName = "";
        Attribute attribute = null;
        double modifierValue = 0;
        int cycles = skill.getCycles();
        if (skill instanceof FarmingSkill) {
            uuid = FARMING_MODIFIER_UUID;
            modifierName = "farming_cycle_buff";
            attribute = Attributes.MAX_HEALTH;
            modifierValue = 5 * cycles;
        } else if (skill instanceof DexteritySkill) {
            uuid = DEXTERITY_MODIFIER_UUID;
            modifierName = "dexterity_cycle_buff";
            attribute = Attributes.MOVEMENT_SPEED;
            modifierValue = 0.003 * cycles;
        } else if (skill instanceof HaulingSkill) {
            uuid = HAULING_MODIFIER_UUID;
            modifierName = "hauling_cycle_buff";
            attribute = Attributes.LUCK;
            modifierValue = 0.5 * cycles;
        } else if (skill instanceof ExtractionSkill) {
            uuid = EXTRACTION_MODIFIER_UUID;
            modifierName = "extraction_cycle_buff";
            attribute = Attributes.KNOCKBACK_RESISTANCE;
            modifierValue = 0.5 * cycles;
        }
        if (!uuid.isEmpty()) {
            EntityUtil.reapplyAttributeModifier(skill.getPlayer(), attribute, new AttributeModifier(UUID.fromString(uuid), modifierName, modifierValue, AttributeModifier.Operation.ADDITION), true);
        }
    }

    public static boolean isEsirArmourOrWeapon(ItemStack stack) {
        ResourceLocation registerName = stack.getItem().getRegistryName();
        if (registerName != null && registerName.getNamespace().equals(EsirAoA3Extra.MODID) && stack.hasTag() && stack.getTag().contains("display")) {
            CompoundTag CompoundTag = stack.getTagElement("display");
            if (CompoundTag.contains("Lore")) {
                //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
                ListTag loreList = CompoundTag.getList("Lore", 8);
                return loreList.getAsString().contains("魂缚") && loreList.getAsString().contains("增幅等级");
            }
        }
        return false;
    }

    public static boolean isPlayerEnvironmentallyProtected(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("display")) {
            CompoundTag CompoundTag = stack.getTagElement("display");
            if (CompoundTag.contains("Lore")) {
                ListTag loreList = CompoundTag.getList("Lore", 8);
                return loreList.getAsString().contains("提供自身氧气");
            }
        }
        return false;
    }

    public static float[] getAttribute(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        float extraDmg = 0;
        int amplifierLevel = 0;
        int starLevel = 0;
        boolean isBroken = false;
        if (stack.hasTag() && stack.getTag().contains("display")) {
            CompoundTag CompoundTag = stack.getTagElement("display");
            if (CompoundTag.contains("Lore")) {
                //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
                ListTag loreList = CompoundTag.getList("Lore", 8);
                if (loreList.getAsString().contains("损毁的魂缚")) isBroken = true;
                for (int i = 0; i < loreList.size(); ++i) {
                    String lore = loreList.getString(i);
                    try {
                        FormattedText iFormattableTextComponent = TextComponent.Serializer.fromJson(lore);
                        if (iFormattableTextComponent != null) {
                            String s = iFormattableTextComponent.getString();
                            if (s.contains("效果：伤害+")) {
                                extraDmg = Float.parseFloat(s.substring(s.lastIndexOf("+") + 1).replace("%", "")) * (float) 0.01;
                            } else if (s.contains("增幅等级：+")) {
                                amplifierLevel = Integer.parseInt(s.substring(s.lastIndexOf("+") + 1));
                            } else if (s.contains("命星：۞")) {
                                starLevel = s.split("：")[1].length();
                            }
                        }
                    } catch (JsonParseException ignored) {
                    }
                }
            }
        }
        return isBroken ? new float[]{-1, 0, starLevel} : new float[]{extraDmg, amplifierLevel, starLevel};
    }

    public static ItemStack amplifyEquip(Player player, ItemStack stack, float[] attribute) {
        CompoundTag parent = stack.getTag();
        CompoundTag display = parent.getCompound("display");
        //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
        ListTag loreList = display.getList("Lore", 8);
        int INCREASE = attribute[1] < 10 ? 5 + (int) (10 / (attribute[2] * attribute[2] + 1)) : 5;
        int newAmplifierLevel = (int) attribute[1];
        int randomNum = new Random(System.currentTimeMillis()).nextInt((int) (10 * (BROKEN + SAME + DECREASE + INCREASE))) + 1;
        if (randomNum <= 10 * BROKEN) {
            player.sendMessage(new TextComponent("增幅失败，该装备将变为损毁状态，增幅等级将清零").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
            if (stack.getTag().contains("amplifierProtection")) {
                player.sendMessage(new TextComponent("神恩符为你免除了此次的损毁惩罚，保护效果已消失").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), Util.NIL_UUID);
                stack.removeTagKey("amplifierProtection");
                return stack;
            }
            for (int i = 0; i < loreList.size(); i++) {
                String c = loreList.getString(i);
                if (c.contains("魂缚")) {
                    loreList.set(i, StringTag.valueOf(c.replace("魂缚", "损毁的魂缚")));
                } else if (c.contains("增幅等级：+") && attribute[1] > 0) {
                    String start = c.substring(c.indexOf("增幅等级：+") + 6);
                    String oldAmplifierLevel = start.substring(0, start.indexOf("\""));
                    loreList.set(i, StringTag.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+0")));
                }
            }
        } else if (randomNum <= 10 * (BROKEN + SAME)) {
            player.sendMessage(new TextComponent("增幅失败，该装备增幅等级未发生变化").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
            return ItemStack.EMPTY;
        } else if (randomNum <= 10 * (BROKEN + SAME + DECREASE)) {
            player.sendMessage(new TextComponent("增幅失败，该装备增幅等级将-1").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
            if (stack.getTag().contains("amplifierProtection")) {
                player.sendMessage(new TextComponent("神恩符为你免除了此次的降级惩罚，保护效果已消失").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), Util.NIL_UUID);
                stack.removeTagKey("amplifierProtection");
                return stack;
            }
            if (--newAmplifierLevel == -1) {
                player.sendMessage(new TextComponent("发现该装备增幅等级为0，将保持不变").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), Util.NIL_UUID);
                return ItemStack.EMPTY;
            }
            modifyAmplifierLevel(loreList, newAmplifierLevel);
        } else {
            int addLevel = 1;
            double valueRandom = new Random(System.currentTimeMillis()).nextDouble();
            if (valueRandom < CRITICAL_HIT_RATE) {
                addLevel = 2;
            }
            player.sendMessage(new TextComponent("增幅成功，该装备增幅等级+" + addLevel).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)), Util.NIL_UUID);
            int oldAmplifierLevel = newAmplifierLevel;
            newAmplifierLevel += addLevel;
            if (oldAmplifierLevel < 10 && newAmplifierLevel >= 10)
                player.sendMessage(new TextComponent("该装备已增幅至10级，可选择升星或继续增幅").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), Util.NIL_UUID);
            modifyAmplifierLevel(loreList, newAmplifierLevel);
        }
        boolean noBound = true;
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("待绑定")) {
                loreList.set(i, StringTag.valueOf(c.replace("待绑定", "归属者: " + player.getName().getString())));
                noBound = false;
                break;
            } else if (c.contains("归属者: ")) {
                noBound = false;
                break;
            }
        }
        if (noBound)
            loreList.add(StringTag.valueOf("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"red\",\"text\":\"归属者: $player\"}],\"text\":\"\"}".replace("$player", player.getName().getString())));
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    private static void modifyAmplifierLevel(ListTag loreList, int newAmplifierLevel) {
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("增幅等级：+")) {
                String start = c.substring(c.indexOf("增幅等级：+") + 6);
                String oldAmplifierLevel = start.substring(0, start.indexOf("\""));
                loreList.set(i, StringTag.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+" + newAmplifierLevel)));
                break;
            }
        }
    }

    public static ItemStack fixBrokenEquip(ItemStack stack) {
        CompoundTag parent = stack.getTag();
        CompoundTag display = parent.getCompound("display");
        //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
        ListTag loreList = display.getList("Lore", 8);
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("损毁的魂缚")) {
                loreList.set(i, StringTag.valueOf(c.replace("损毁的魂缚", "魂缚")));
                break;
            }
        }
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    public static ItemStack upgradeEquip(Player player, ItemStack stack, int amplifierLevel, int starLevel) {
        CompoundTag parent = stack.getTag();
        CompoundTag display = parent.getCompound("display");
        //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
        ListTag loreList = display.getList("Lore", 8);
        StringBuilder starLevelBuilder = new StringBuilder();
        boolean noBound = true;
        if (starLevel == 0) {
            starLevelBuilder.append("无");
        } else {
            for (int i = 0; i < starLevel; i++) {
                starLevelBuilder.append("۞");
            }
        }
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("增幅等级：+")) {
                String start = c.substring(c.indexOf("增幅等级：+") + 6);
                String oldAmplifierLevel = start.substring(0, start.indexOf("\""));
                loreList.set(i, StringTag.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+" + amplifierLevel)));
            } else if (c.contains("命星：")) {
                String start = c.substring(c.indexOf("命星：") + 3);
                String oldStarLevel = start.substring(0, start.indexOf("\""));
                loreList.set(i, StringTag.valueOf(c.replace("命星：" + oldStarLevel, "命星：" + starLevelBuilder)));
            } else if (c.contains("待绑定")) {
                loreList.set(i, StringTag.valueOf(c.replace("待绑定", "归属者: " + player.getName().getString())));
                noBound = false;
            } else if (c.contains("归属者: "))
                noBound = false;
        }
        if (noBound)
            loreList.add(StringTag.valueOf("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"red\",\"text\":\"归属者: $player\"}],\"text\":\"\"}".replace("$player", player.getName().getString())));
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    public static boolean canUpgrade(int starLevel, ItemStack ticket) {
        CompoundTag parent = ticket.getTag();
        if (parent == null) return false;
        CompoundTag display = parent.getCompound("display");
        //int参数表示ListTag中的元素类型 8: String  9:List 10:Compound
        ListTag loreList = display.getList("Lore", 8);
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("۞")) {
                return starLevel + 1 == c.chars().filter(star -> star == '۞').count();
            }
        }
        return false;
    }
}