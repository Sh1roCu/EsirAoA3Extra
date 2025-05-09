package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.custom.AoAResources;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.List;

public class SoulfireBow extends BaseBow {
    public SoulfireBow(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);
    }

    @Override
    protected CustomArrowEntity makeArrow(LivingEntity shooter, ItemStack bowStack, ItemStack ammoStack, float velocity, boolean consumeAmmo) {
        CustomArrowEntity arrow = super.makeArrow(shooter, bowStack, ammoStack, velocity, consumeAmmo);

        if (arrow != null && shooter instanceof ServerPlayer && PlayerUtil.consumeResource((ServerPlayer) shooter, AoAResources.SPIRIT.get(), 200, false))
            arrow.setGlowing(true);

        return arrow;
    }

    @Override
    public void onEntityHit(CustomArrowEntity arrow, Entity target, Entity shooter, double damage, float drawStrength) {
        if (arrow.isGlowing() && shooter instanceof LivingEntity && shooter.isAlive())
            ((LivingEntity) shooter).heal(8);

        arrow.setGlowing(false);
    }

    @Override
    public void onBlockHit(CustomArrowEntity arrow, BlockHitResult rayTrace, Entity shooter) {
        arrow.setGlowing(false);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 2));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
