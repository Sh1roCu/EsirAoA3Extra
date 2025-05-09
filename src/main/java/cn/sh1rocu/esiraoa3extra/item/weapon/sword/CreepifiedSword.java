package cn.sh1rocu.esiraoa3extra.item.weapon.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.List;

public class CreepifiedSword extends BaseSword {
    public CreepifiedSword() {
        super(ItemUtil.customItemTier(2000, AttackSpeed.NORMAL, 13.5f, 4, 10, null));
    }

    @Override
    protected void doMeleeEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, float attackCooldown) {
        if (!attacker.level.isClientSide && RandomUtil.oneInNChance(10) && (!(attacker instanceof Player) || attackCooldown > 0.75f)) {
			/*final FriendlyCreeperEntity creeper = new FriendlyCreeperEntity(AoAEntities.Minions.FRIENDLY_CREEPER.get(), target.level);

			creeper.moveTo(target.getX(), target.getY(), target.getZ(), random.nextFloat() * 360.0f, 0.0f);
			creeper.setTarget(target);
			creeper.setOwnerUUID(attacker.getUUID());
			target.level.addFreshEntity(creeper);*/ // TODO
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        //tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
    }
}
