package cn.sh1rocu.esiraoa3extra.block;

import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
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
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AmplifierTableTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player instanceof ServerPlayerEntity) {
            AmplifierTableContainer.openContainer((ServerPlayerEntity) player, pos);
        }
        return ActionResultType.SUCCESS;
    }
}
