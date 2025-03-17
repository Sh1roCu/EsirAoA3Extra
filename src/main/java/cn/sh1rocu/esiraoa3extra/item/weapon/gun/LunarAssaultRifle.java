package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.List;

public class LunarAssaultRifle extends BaseGun {
    private final double baseDmg;
    private final double maxDmg;

    public LunarAssaultRifle(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);

        this.baseDmg = dmg - (dmg / 2d);
        this.maxDmg = dmg + (dmg / 2d);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_GENERIC_FIRE_3.get();
    }

    @Override
    public double getDamage() {
        return RandomUtil.randomValueBetween(baseDmg, maxDmg);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        tooltip.set(1, LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.RANDOM_DAMAGE, LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(baseDmg), LocaleUtil.numToComponent(maxDmg)));
    }
}
