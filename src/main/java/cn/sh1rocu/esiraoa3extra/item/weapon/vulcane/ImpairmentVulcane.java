package cn.sh1rocu.esiraoa3extra.item.weapon.vulcane;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ImpairmentVulcane extends BaseVulcane {
    public ImpairmentVulcane(double dmg, int durability) {
        super(dmg, durability);
    }

    @Override
    public void doAdditionalEffect(LivingEntity target, PlayerEntity attacker, float damageDealt) {
        EntityUtil.applyPotions(target, new EffectBuilder(Effects.MOVEMENT_SLOWDOWN, 100).level(3));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.SLOWS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
