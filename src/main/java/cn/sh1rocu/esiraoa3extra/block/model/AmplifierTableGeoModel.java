package cn.sh1rocu.esiraoa3extra.block.model;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.aoa3.geckolib3.model.AnimatedGeoModel;

public class AmplifierTableGeoModel extends AnimatedGeoModel<AmplifierTableTileEntity> {

    @Override
    public ResourceLocation getModelLocation(AmplifierTableTileEntity amplifierTableTileEntity) {
        return new ResourceLocation(EsirAoA3Extra.MODID, "geo/amplifier_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AmplifierTableTileEntity amplifierTableTileEntity) {
        return new ResourceLocation(EsirAoA3Extra.MODID, "textures/block/amplifier_table.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AmplifierTableTileEntity amplifierTableTileEntity) {
        return new ResourceLocation(EsirAoA3Extra.MODID, "animations/amplifier_table.animation.json");
    }
}
