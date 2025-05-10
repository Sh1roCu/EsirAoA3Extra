package cn.sh1rocu.esiraoa3extra.item.weapon.vulcane;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.player.resource.AoAResource;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseVulcane extends net.tslat.aoa3.content.item.weapon.vulcane.BaseVulcane {
    protected double baseDmg;

    public BaseVulcane(double dmg, int durability) {
        super(dmg, durability);

        this.baseDmg = dmg;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public double getDamage() {
        return baseDmg;
    }

    @Override
    public InteractionResultHolder<ItemStack> activate(AoAResource.Instance rage, ItemStack vulcane, InteractionHand hand) {
        Player pl = rage.getPlayerDataManager().player();
        float extraDmg = 0;
        int amplifierLevel = 0;
        int starLevel = 0;
        float[] attribute = EsirUtil.getAttribute(vulcane);
        if (attribute[0] != -1) {
            extraDmg = attribute[0];
            amplifierLevel = (int) attribute[1];
            starLevel = (int) attribute[2];
        }
        float damage = (float) getDamage() * (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel)))) * (1 + ((rage.getCurrentValue() - 50) / 100));
        float targetHealth = pl.getLastHurtByMob().getHealth();

        if (DamageUtil.dealVulcaneDamage(pl.getLastHurtByMob(), pl, damage)) {
            doAdditionalEffect(pl.getLastHurtByMob(), pl, Math.min(damage, targetHealth));
            pl.level.playSound(null, pl.getX(), pl.getY(), pl.getZ(), AoASounds.ITEM_VULCANE_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            ItemUtil.damageItem(vulcane, pl, hand);
            rage.consume(rage.getCurrentValue(), true);

            return InteractionResultHolder.success(vulcane);
        }

        return InteractionResultHolder.fail(vulcane);
    }

    public void doAdditionalEffect(LivingEntity target, Player player, float damageDealt) {
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(1, LocaleUtil.getFormattedItemDescriptionText("items.description.damage.true", LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, LocaleUtil.numToComponent(baseDmg)));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.vulcane.use", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.vulcane.target", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
    }
}
