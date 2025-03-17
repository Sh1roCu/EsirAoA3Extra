package cn.sh1rocu.esiraoa3extra.util;

import com.google.gson.JsonParseException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;

import java.util.Random;

public class EsirUtil {
    private static final float BROKEN = 0.3f;
    private static final float SAME = 4.7f;
    private static final int DECREASE = 5;

    public static boolean isEsirArmourOrWeapon(ItemStack stack) {
        ResourceLocation registerName = stack.getItem().getRegistryName();
        if (registerName != null && registerName.getNamespace().equals("esiraoa3extra") && stack.hasTag() && stack.getTag().contains("display")) {
            CompoundNBT compoundNBT = stack.getTagElement("display");
            if (compoundNBT.contains("Lore")) {
                //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
                ListNBT loreList = compoundNBT.getList("Lore", 8);
                return loreList.getAsString().contains("魂缚");
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
            CompoundNBT compoundNBT = stack.getTagElement("display");
            if (compoundNBT.contains("Lore")) {
                //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
                ListNBT loreList = compoundNBT.getList("Lore", 8);
                if (loreList.getAsString().contains("损毁的魂缚")) isBroken = true;
                for (int i = 0; i < loreList.size(); ++i) {
                    String lore = loreList.getString(i);
                    try {
                        IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.fromJson(lore);
                        if (iFormattableTextComponent != null) {
                            String s = iFormattableTextComponent.getString();
                            if (s.contains("效果：伤害+")) {
                                extraDmg = Float.valueOf(s.substring(s.lastIndexOf("+") + 1).replace("%", "")) * (float) 0.01;
                            } else if (s.contains("增幅等级：+")) {
                                amplifierLevel = Integer.valueOf(s.substring(s.lastIndexOf("+") + 1));
                            } else if (s.contains("命星：۞")) {
                                starLevel = Integer.valueOf(s.split("：")[1].length());
                            }
                        }
                    } catch (JsonParseException ignored) {
                    }
                }
            }
        }
        return isBroken ? new float[]{-1, 0, starLevel} : new float[]{extraDmg, amplifierLevel, starLevel};
    }

    public static ItemStack amplifyEquip(PlayerEntity player, ItemStack stack, float[] attribute) {
        CompoundNBT parent = stack.getTag();
        CompoundNBT display = parent.getCompound("display");
        //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
        ListNBT loreList = display.getList("Lore", 8);
        int INCREASE = attribute[1] < 10 ? 5 + (int) (10 / (attribute[2] * attribute[2] + 1)) : 5;
        int newAmplifierLevel = (int) attribute[1];
        int randomNum = new Random(System.currentTimeMillis()).nextInt((int) (10 * (BROKEN + SAME + DECREASE + INCREASE))) + 1;
        if (randomNum <= 10 * BROKEN) {
            player.sendMessage(new StringTextComponent("增幅失败，该装备将变为损毁状态，增幅等级将清零").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
            if (stack.getTag().contains("amplifierProtection")) {
                player.sendMessage(new StringTextComponent("神恩符为你免除了此次的损毁惩罚，保护效果已消失").setStyle(Style.EMPTY.withColor(TextFormatting.GREEN)), Util.NIL_UUID);
                stack.removeTagKey("amplifierProtection");
                return stack;
            }
            for (int i = 0; i < loreList.size(); i++) {
                String c = loreList.getString(i);
                if (c.contains("魂缚")) {
                    loreList.set(i, StringNBT.valueOf(c.replace("魂缚", "损毁的魂缚")));
                } else if (c.contains("增幅等级：+") && attribute[1] > 0) {
                    String start = c.substring(c.indexOf("增幅等级：+") + 6);
                    String oldAmplifierLevel = start.substring(0, start.indexOf("\""));
                    loreList.set(i, StringNBT.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+0")));
                }
            }
        } else if (randomNum <= 10 * (BROKEN + SAME)) {
            player.sendMessage(new StringTextComponent("增幅失败，该装备增幅等级未发生变化").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
            return ItemStack.EMPTY;
        } else if (randomNum <= 10 * (BROKEN + SAME + DECREASE)) {
            player.sendMessage(new StringTextComponent("增幅失败，该装备增幅等级将-1").setStyle(Style.EMPTY.withColor(TextFormatting.RED)), Util.NIL_UUID);
            if (stack.getTag().contains("amplifierProtection")) {
                player.sendMessage(new StringTextComponent("神恩符为你免除了此次的降级惩罚，保护效果已消失").setStyle(Style.EMPTY.withColor(TextFormatting.GREEN)), Util.NIL_UUID);
                stack.removeTagKey("amplifierProtection");
                return stack;
            }
            if (--newAmplifierLevel == -1) {
                player.sendMessage(new StringTextComponent("发现该装备增幅等级为0，将保持不变").setStyle(Style.EMPTY.withColor(TextFormatting.GOLD)), Util.NIL_UUID);
                return ItemStack.EMPTY;
            }
            modifyAmplifierLevel(loreList, newAmplifierLevel);
        } else {
            player.sendMessage(new StringTextComponent("增幅成功，该装备增幅等级+1").setStyle(Style.EMPTY.withColor(TextFormatting.YELLOW)), Util.NIL_UUID);
            if (++newAmplifierLevel == 10)
                player.sendMessage(new StringTextComponent("该装备已增幅至10级，可选择升星或继续增幅").setStyle(Style.EMPTY.withColor(TextFormatting.GOLD)), Util.NIL_UUID);
            modifyAmplifierLevel(loreList, newAmplifierLevel);
        }
        boolean noBound = true;
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("待绑定")) {
                loreList.set(i, StringNBT.valueOf(c.replace("待绑定", "归属者: " + player.getName().getString())));
                noBound = false;
                break;
            } else if (c.contains("归属者: ")) {
                noBound = false;
                break;
            }
        }
        if (noBound)
            loreList.add(StringNBT.valueOf("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"red\",\"text\":\"归属者: $player\"}],\"text\":\"\"}".replace("$player", player.getName().getString())));
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    private static void modifyAmplifierLevel(ListNBT loreList, int newAmplifierLevel) {
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("增幅等级：+")) {
                String start = c.substring(c.indexOf("增幅等级：+") + 6);
                String oldAmplifierLevel = start.substring(0, start.indexOf("\""));
                loreList.set(i, StringNBT.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+" + newAmplifierLevel)));
                break;
            }
        }
    }

    public static ItemStack fixBrokenEquip(ItemStack stack) {
        CompoundNBT parent = stack.getTag();
        CompoundNBT display = parent.getCompound("display");
        //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
        ListNBT loreList = display.getList("Lore", 8);
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("损毁的魂缚")) {
                loreList.set(i, StringNBT.valueOf(c.replace("损毁的魂缚", "魂缚")));
                break;
            }
        }
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    public static ItemStack upgradeEquip(PlayerEntity player, ItemStack stack, int amplifierLevel, int starLevel) {
        CompoundNBT parent = stack.getTag();
        CompoundNBT display = parent.getCompound("display");
        //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
        ListNBT loreList = display.getList("Lore", 8);
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
                loreList.set(i, StringNBT.valueOf(c.replace("增幅等级：+" + oldAmplifierLevel, "增幅等级：+" + amplifierLevel)));
            } else if (c.contains("命星：")) {
                String start = c.substring(c.indexOf("命星：") + 3);
                String oldStarLevel = start.substring(0, start.indexOf("\""));
                loreList.set(i, StringNBT.valueOf(c.replace("命星：" + oldStarLevel, "命星：" + starLevelBuilder)));
            } else if (c.contains("待绑定")) {
                loreList.set(i, StringNBT.valueOf(c.replace("待绑定", "归属者: " + player.getName().getString())));
                noBound = false;
            } else if (c.contains("归属者: "))
                noBound = false;
        }
        if (noBound)
            loreList.add(StringNBT.valueOf("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"red\",\"text\":\"归属者: $player\"}],\"text\":\"\"}".replace("$player", player.getName().getString())));
        display.put("Lore", loreList);
        parent.put("display", display);
        stack.setTag(parent);
        return stack;
    }

    public static boolean canUpgrade(int starLevel, ItemStack ticket) {
        CompoundNBT parent = ticket.getTag();
        if (parent == null) return false;
        CompoundNBT display = parent.getCompound("display");
        //int参数表示ListNBT中的元素类型 8: String  9:List 10:Compound
        ListNBT loreList = display.getList("Lore", 8);
        for (int i = 0; i < loreList.size(); i++) {
            String c = loreList.getString(i);
            if (c.contains("۞")) {
                return starLevel + 1 == c.chars().filter(star -> star == '۞').count();
            }
        }
        return false;
    }
}
