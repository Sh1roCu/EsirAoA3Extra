package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.List;

public class HolySword extends BaseSword {
    public HolySword() {
        super(ItemUtil.customItemTier(1000, AttackSpeed.TRIPLE, 0.0f, 4, 10, null),
                new Item.Properties().durability(1000).tab(AoAItemGroups.SWORDS).addToolType(ToolType.get("sword"), 4).rarity(Rarity.RARE));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide)
            WorldUtil.spawnLightning((ServerLevel) world, (ServerPlayer) player, player.getX(), player.getY(), player.getZ(), false);

        player.getCooldowns().addCooldown(this, 100);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.UNIQUE, 1));
    }
}
