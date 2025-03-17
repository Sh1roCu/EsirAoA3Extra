package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.content.entity.projectile.misc.TidalWaveEntity;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class TidalGreatblade extends BaseGreatblade {
    public TidalGreatblade() {
        super(24.0f, AttackSpeed.GREATBLADE, 1750, Rarity.RARE);
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (attackCooldown < 0.95f)
            return;

        double xOffset = MathHelper.cos(attacker.yRot / 180.0F * (float) Math.PI) * 0.7F;
        double zOffset = MathHelper.sin(attacker.yRot / 180.0F * (float) Math.PI) * 0.7F;

        attacker.level.addFreshEntity(new TidalWaveEntity(attacker.level, attacker, xOffset, zOffset));
        attacker.level.addFreshEntity(new TidalWaveEntity(attacker.level, attacker, 0, 0));
        attacker.level.addFreshEntity(new TidalWaveEntity(attacker.level, attacker, -xOffset, -zOffset));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
