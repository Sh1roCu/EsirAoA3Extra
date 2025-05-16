package cn.sh1rocu.esiraoa3extra.mixin.common;

import cn.sh1rocu.esiraoa3extra.item.misc.BaseMagazine;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.aoa3.common.registration.AoAItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    public float blitOffset;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Shadow
    public abstract BakedModel getModel(ItemStack arg, @Nullable Level arg2, @Nullable LivingEntity arg3);

    @Shadow
    public abstract void render(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model);

    @Inject(
            method = "tryRenderGuiItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderGuiItem(Lnet/minecraft/world/item/ItemStack;IILnet/minecraft/client/resources/model/BakedModel;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void esir$renderBulletIcon(LivingEntity entity, ItemStack itemStack, int x, int y, CallbackInfo ci) {
        if (itemStack.getItem() instanceof BaseMagazine) {
            BaseMagazine baseMagazine = (BaseMagazine) itemStack.getItem();
            Item bullet = esir$getBulletItem(baseMagazine);
            if (bullet != null) {
                ItemStack stack = new ItemStack(bullet);
                BakedModel bakedmodel = this.getModel(stack, null, null);
                RenderSystem.pushMatrix();
                this.textureManager.bind(TextureAtlas.LOCATION_BLOCKS);
                this.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
                RenderSystem.enableRescaleNormal();
                RenderSystem.enableAlphaTest();
                RenderSystem.defaultAlphaFunc();
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.translatef((float) x, (float) y, 100.0F + this.blitOffset);
                RenderSystem.translatef(8.0F, 8.0F, 0.0F);
                RenderSystem.scalef(1.0F, -1.0F, 1.0F);
                RenderSystem.scalef(16.0F, 16.0F, 16.0F);
                RenderSystem.scalef(0.5F, 0.5F, 0.5F);
                RenderSystem.translatef(-0.8F, -0.6F, 0.0F);
                PoseStack matrixstack = new PoseStack();
                MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                this.render(stack, ItemTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                irendertypebuffer$impl.endBatch();
                RenderSystem.enableDepthTest();
                if (flag) {
                    Lighting.setupFor3DItems();
                }

                RenderSystem.disableAlphaTest();
                RenderSystem.disableRescaleNormal();
                RenderSystem.popMatrix();
            }
        }
    }

    @Unique
    private Item esir$getBulletItem(BaseMagazine baseMagazine) {
        switch (baseMagazine.getType()) {
            case CANNONBALL:
                return AoAItems.CANNONBALL.get();
            case LIMONITE_BULLET:
                return AoAItems.LIMONITE_BULLET.get();
            case METAL_SLUG:
                return AoAItems.METAL_SLUG.get();
            case SPREADSHOT:
                return AoAItems.SPREADSHOT.get();
        }
        return null;
    }
}
