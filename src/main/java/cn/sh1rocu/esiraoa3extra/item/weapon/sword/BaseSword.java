package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolType;
import net.tslat.aoa3.common.registration.AoAItemGroups;

public class BaseSword extends net.tslat.aoa3.content.item.weapon.sword.BaseSword {
    public BaseSword(Tier itemStats) {
        this(itemStats, new Item.Properties().durability(itemStats.getUses()).tab(AoAItemGroups.SWORDS).addToolType(ToolType.get("sword"), itemStats.getLevel()));
    }

    public BaseSword(Tier itemStats, Item.Properties properties) {
        super(itemStats, properties);
    }

    @Override
    public double getDamageForAttack(LivingEntity target, LivingEntity attacker, ItemStack swordStack, double baseDamage) {
        float extraDmg = 0;
        int amplifierLevel = 0;
        int starLevel = 0;
        float[] attribute = EsirUtil.getAttribute(swordStack);
        if (attribute[0] != -1) {
            extraDmg = attribute[0];
            amplifierLevel = (int) attribute[1];
            starLevel = (int) attribute[2];
        }
        return baseDamage * (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));
    }
}

