package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.content.entity.projectile.staff.GhoulShotEntity;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class GhoulStaff extends BaseStaff<Object> {
    public GhoulStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.WIND_RUNE.get(), 2);
        runes.put(AoAItems.DISTORTION_RUNE.get(), 1);
        runes.put(AoAItems.LIFE_RUNE.get(), 2);
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, Object args) {
        world.addFreshEntity(new GhoulShotEntity(caster, this, 60));
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        return DamageUtil.dealMagicDamage(shot, shooter, target, RandomUtil.randomNumberBetween(16, 42), false);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.RANDOM_DAMAGE, LocaleUtil.ItemDescriptionType.ITEM_DAMAGE, new TextComponent("16"), new TextComponent("42")));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
