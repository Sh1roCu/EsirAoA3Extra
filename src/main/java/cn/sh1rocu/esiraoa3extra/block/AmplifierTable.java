package cn.sh1rocu.esiraoa3extra.block;

import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolType;

public class AmplifierTable extends Block {
    public AmplifierTable() {
        super(Properties.of(Material.STONE).strength(5).harvestTool(ToolType.PICKAXE).noOcclusion());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return new AmplifierTableTileEntity();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player instanceof ServerPlayer) {
            AmplifierTableContainer.openContainer((ServerPlayer) player, pos);
        }
        return InteractionResult.SUCCESS;
    }
}
