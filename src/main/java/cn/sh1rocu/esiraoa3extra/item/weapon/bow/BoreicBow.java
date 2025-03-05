package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BoreicBow extends BaseBow {
    public BoreicBow(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);
    }

    @Override
    public void onArrowTick(CustomArrowEntity arrow, Entity shooter) {
        if (arrow.isInWater()) {
            if (!arrow.level.isClientSide)
                WorldUtil.createExplosion(shooter, arrow.level, arrow, arrow.isCritArrow() ? 2.0f : 1.0f);

            arrow.remove();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
