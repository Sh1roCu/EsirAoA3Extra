package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GoofyGreatblade extends BaseGreatblade {
    public GoofyGreatblade() {
        super(22.0f, AttackSpeed.GREATBLADE, 1300);
    }

    @Override
    public double getDamageForAttack(LivingEntity target, LivingEntity attacker, ItemStack swordStack, double baseDamage) {
        return super.getDamageForAttack(target, attacker, swordStack, baseDamage + (float) (Item.random.nextGaussian() * 5f));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.RANDOM_DAMAGE, LocaleUtil.ItemDescriptionType.BENEFICIAL, new StringTextComponent(String.valueOf(getDamage() - 5)), new StringTextComponent(String.valueOf(getDamage() + 5))));
    }
}
