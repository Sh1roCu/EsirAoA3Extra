package cn.sh1rocu.esiraoa3extra.item.weapon.thrown;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.thrown.HellfireEntity;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Hellfire extends BaseThrownWeapon {
    public Hellfire() {
        super(3.5f, 7);
    }

    @Override
    protected float getFiringSoundPitchAdjust() {
        return 0.5f;
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new HellfireEntity(shooter, this);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
