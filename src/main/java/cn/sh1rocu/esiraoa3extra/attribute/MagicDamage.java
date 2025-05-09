package cn.sh1rocu.esiraoa3extra.attribute;


import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class MagicDamage extends RangedAttribute {

    public MagicDamage(String descriptionId, double defaultValue) {
        super(descriptionId, defaultValue, -Double.MAX_VALUE, Double.MAX_VALUE);
    }
}