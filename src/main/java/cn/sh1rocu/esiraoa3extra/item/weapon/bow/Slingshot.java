package cn.sh1rocu.esiraoa3extra.item.weapon.bow;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.content.entity.projectile.arrow.PopShotEntity;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Slingshot extends BaseBow {
    public static final Predicate<ItemStack> AMMO_PREDICATE = stack -> stack.getItem() == AoAItems.POP_SHOT.get() || stack.getItem() == Items.FLINT;

    private final float drawSpeedMultiplier;
    private final double dmg;

    public Slingshot(double damage, float drawSpeedMultiplier, int durability) {
        super(damage, drawSpeedMultiplier, durability);

        this.drawSpeedMultiplier = drawSpeedMultiplier;
        this.dmg = damage;
    }

    @Override
    public void onEntityHit(CustomArrowEntity shot, Entity target, Entity shooter, double damage, float drawStrength) {
        if (shot instanceof PopShotEntity && ((PopShotEntity) shot).isExplosive)
            WorldUtil.createExplosion(shooter, shot.level, shot, 1.0f);

        shot.remove();
    }

    @Override
    public void onBlockHit(CustomArrowEntity shot, BlockRayTraceResult rayTrace, Entity shooter) {
        if (shot instanceof PopShotEntity && ((PopShotEntity) shot).isExplosive)
            WorldUtil.createExplosion(shooter, shot.level, shot, 1.0f);

        shot.remove();
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return AMMO_PREDICATE;
    }

    @Override
    public double getArrowDamage(CustomArrowEntity arrow, Entity target, double currentDamage, float drawStrength, boolean isCritical) {
        double damage = currentDamage * (drawStrength / 2f);

        if (isCritical)
            damage += damage + (damage * 0.35f * random.nextGaussian());

        return damage;
    }

    @Override
    public CustomArrowEntity doArrowMods(CustomArrowEntity arrow, LivingEntity shooter, ItemStack ammoStack, int useTicksRemaining) {
        PopShotEntity popShot = new PopShotEntity(arrow.level, this, shooter, dmg, ammoStack.getItem() instanceof ArrowItem);

        popShot.shootFromRotation(shooter, shooter.xRot, shooter.yRot, 0, BowItem.getPowerForTime((int) (72000 / drawSpeedMultiplier - useTicksRemaining)) * 2, 1);

        return popShot;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.arrows", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new StringTextComponent(Double.toString(dmg))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.bow.drawSpeed", LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(Double.toString(((72000 / drawSpeedMultiplier) / 720) / (double) 100))));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_ITEM, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, LocaleUtil.getLocaleMessage(AoAItems.POP_SHOT.get().getDescriptionId()).append(new StringTextComponent("/")).append(LocaleUtil.getLocaleMessage(Items.FLINT.getDescriptionId()))));
    }
}
