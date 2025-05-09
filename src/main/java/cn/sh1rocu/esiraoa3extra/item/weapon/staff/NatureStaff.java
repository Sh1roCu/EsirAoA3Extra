package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NatureStaff extends BaseStaff<ArrayList<BlockPos>> {
    public NatureStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_NATURE_STAFF_CAST.get();
    }

    @Override
    public ArrayList<BlockPos> checkPreconditions(LivingEntity caster, ItemStack staff) {
        ArrayList<BlockPos> blocks = WorldUtil.getBlocksWithinAABB(caster.level, caster.getBoundingBox().inflate(10), (state, pos) -> {
            if (!(state.getBlock() instanceof BonemealableBlock))
                return false;

            if (!((BonemealableBlock) state.getBlock()).isValidBonemealTarget(caster.level, pos.immutable(), state, false))
                return false;

            return WorldUtil.canModifyBlock(caster.level, pos, caster, staff);
        });

        return blocks.isEmpty() ? null : blocks;
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.LIFE_RUNE.get(), 4);
        runes.put(AoAItems.ENERGY_RUNE.get(), 2);
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, ArrayList<BlockPos> args) {
        for (BlockPos pos : args) {
            BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), caster.level, pos);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
