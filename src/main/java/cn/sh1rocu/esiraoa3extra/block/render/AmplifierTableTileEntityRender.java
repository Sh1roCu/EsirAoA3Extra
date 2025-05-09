package cn.sh1rocu.esiraoa3extra.block.render;

import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.block.model.AmplifierTableGeoModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.aoa3.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;


public class AmplifierTableTileEntityRender extends GeoBlockRenderer<AmplifierTableTileEntity> {

    public AmplifierTableTileEntityRender(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AmplifierTableGeoModel());
    }

    @Override
    public RenderType getRenderType(AmplifierTableTileEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
