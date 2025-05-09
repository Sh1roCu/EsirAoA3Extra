package cn.sh1rocu.esiraoa3extra.item.weapon.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.advent.AdventOfAscension;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.LootUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Crystaneer extends BaseSniper {
    public Crystaneer(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_SNIPER_HEAVY_FIRE_LONG.get();
    }

    @Override
    public ResourceLocation getScopeTexture(ItemStack stack) {
        return SCOPE_4;
    }

    @Override
    protected void doImpactEffect(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (!shooter.level.isClientSide() && target instanceof LivingEntity && ((LivingEntity) target).getHealth() <= 0) {
            for (ItemStack drop : LootUtil.generateLoot((ServerLevel) shooter.level, AdventOfAscension.id("items/crystaneer"), LootUtil.getGiftContext((ServerLevel) shooter.level, target.position(), (shooter instanceof Player ? ((Player) shooter).getLuck() : 0), shooter))) {
                target.spawnAtLocation(drop, 0f);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.UNIQUE, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
