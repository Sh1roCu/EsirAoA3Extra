package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public class SpectralBow extends BaseBow {
    public SpectralBow(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);
    }

    @Override
    protected ItemStack findAmmo(PlayerEntity shooter, ItemStack bowStack, boolean infiniteAmmo) {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public CustomArrowEntity doArrowMods(CustomArrowEntity arrow, LivingEntity shooter, ItemStack ammoStack, int useTicksRemaining) {
        arrow.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;

        return arrow;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.arrows", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(Double.toString(getDamage()))));
        float result = 1;
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            result = (float) Math.max(1 + cdMod, 0);
        }
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.bow.drawSpeed", LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) ((int) (72000 / (getDrawSpeedMultiplier() * result)) / 720) / 100, 2))));
    }
}
