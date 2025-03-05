package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MillenniumGreatblade extends BaseGreatblade {
    private final double maxDmg = 39.75f;

    public MillenniumGreatblade() {
        super(13.25f, AttackSpeed.GREATBLADE, 2050);
    }

    @Override
    public double getDamageForAttack(LivingEntity target, LivingEntity attacker, ItemStack swordStack, double baseDamage) {
        return super.getDamageForAttack(target, attacker, swordStack, (float) (Item.random.nextFloat() * (maxDmg - baseDamage) + baseDamage) - 1);
    }

    @Override
    protected Lazy<ImmutableSetMultimap<Attribute, AttributeModifier>> buildDefaultAttributes() {
        return Lazy.of(() -> ImmutableSetMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0, AttributeModifier.Operation.ADDITION),
                Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION),
                ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("93bb7485-ce86-4e78-ab50-26f53d78ad9d"), "AoAGreatbladeReach", 1.5f, AttributeModifier.Operation.ADDITION)));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.RANDOM_DAMAGE, LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(Double.toString(getDamage())), new StringTextComponent(Double.toString(maxDmg))));
    }
}
