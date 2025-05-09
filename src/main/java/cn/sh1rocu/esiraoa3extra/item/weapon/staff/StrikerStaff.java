package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.PlayerUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class StrikerStaff extends BaseStaff<BlockPos> {
    public StrikerStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.STRIKE_RUNE.get(), 2);
    }

    @Override
    public BlockPos checkPreconditions(LivingEntity caster, ItemStack staff) {
        BlockPos trace = null;

        if (caster instanceof Player)
            trace = PlayerUtil.getBlockAimingAt((Player) caster, 70);

        return trace;
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, BlockPos args) {
        if (world instanceof ServerLevel)
            WorldUtil.spawnLightning((ServerLevel) world, caster instanceof ServerPlayer ? (ServerPlayer) caster : null, args.getX(), args.getY(), args.getZ(), true);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
