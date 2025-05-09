package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IForgeShearable;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class RosidianGreatblade extends BaseGreatblade {
    public RosidianGreatblade() {
        super(22.5f, AttackSpeed.GREATBLADE, 1470);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (player.level.isClientSide || player.isCreative())
            return false;

        Block block = player.level.getBlockState(pos).getBlock();

        if (block instanceof IForgeShearable) {
            if (((IForgeShearable) block).isShearable(stack, player.level, pos)) {
                List<ItemStack> drops;

                for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
                    for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                        for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                            BlockPos newPos = new BlockPos(x, y, z);
                            Block newBlock = player.level.getBlockState(newPos).getBlock();

                            if (!(newBlock instanceof IForgeShearable) || !((IForgeShearable) newBlock).isShearable(stack, player.level, newPos))
                                continue;

                            drops = ((IForgeShearable) block).onSheared(player, stack, player.level, newPos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack));

                            for (ItemStack drop : drops) {
                                double xMod = random.nextFloat() * 0.7f + 0.15f;
                                double yMod = random.nextFloat() * 0.7f + 0.15f;
                                double zMod = random.nextFloat() * 0.7f + 0.15f;
                                ItemEntity item = new ItemEntity(player.level, x + xMod, y + yMod, z + zMod, drop);

                                item.setDefaultPickUpDelay();
                                player.level.addFreshEntity(item);
                            }

                            ItemUtil.damageItem(stack, player, 1, EquipmentSlot.MAINHAND);
                            player.awardStat(Stats.BLOCK_MINED.get(block));
                            player.level.setBlock(newPos, Blocks.AIR.defaultBlockState(), 11);
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return super.canAttackBlock(state, world, pos, player) || world.getBlockState(pos) instanceof IForgeShearable;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
