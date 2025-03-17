package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GravityBlaster extends BaseBlaster {
    public GravityBlaster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GRAVITY_BLASTER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        if (EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.BRACE.get(), blaster) == 0) {
            float[] attribute = EsirUtil.getAttribute(blaster);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        float extraDmgMod = (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));

        for (LivingEntity mob : shooter.level.getEntitiesOfClass(LivingEntity.class, shooter.getBoundingBox().inflate(2, 0, 2), EntityUtil.Predicates.HOSTILE_MOB)) {
            EntityUtil.pushEntityAway(shooter, mob, 0.5f);
            mob.hurt(new DamageSource("blaster").setMagic(), (float) getDamage() * extraDmgMod);
        }

        shooter.hurtMarked = true;
        shooter.push(0, 2f, 0);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
