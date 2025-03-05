package cn.sh1rocu.esiraoa3extra.block.render;

import cn.sh1rocu.esiraoa3extra.block.blockentity.AmplifierTableTileEntity;
import cn.sh1rocu.esiraoa3extra.block.model.AmplifierTableGeoModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.aoa3.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;


public class AmplifierTableTileEntityRender extends GeoBlockRenderer<AmplifierTableTileEntity> {

    public AmplifierTableTileEntityRender(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AmplifierTableGeoModel());
    }

    @Override
    public RenderType getRenderType(AmplifierTableTileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
