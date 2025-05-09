package cn.sh1rocu.esiraoa3extra.client.gui.container;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tslat.aoa3.util.RenderUtil;


public class AmplifierTableScreen extends AbstractContainerScreen<AmplifierTableContainer> {
    private static final ResourceLocation textures = new ResourceLocation(EsirAoA3Extra.MODID, "textures/gui/containers/amplifier_table.png");

    public AmplifierTableScreen(AmplifierTableContainer container, Inventory inv, Component guiTitle) {
        super(container, inv, guiTitle);
        this.imageWidth = 196;
        this.imageHeight = 200;

    }

    @Override
    public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        RenderSystem.blendColor(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bind(textures);
        blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
/*        AmplifierTableContainer container = this.getMenu();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bind(textures);
        RenderUtil.renderCustomSizedTexture(matrix, this.leftPos, this.topPos, 0.0F, 0.0F, 196, 200, 196, 200);
        if ((!container.inputs.getItem(0).isEmpty() || !container.inputs.getItem(1).isEmpty() || !container.inputs.getItem(2).isEmpty()) && container.leftNewEquip.getItem(0).isEmpty()) {
            RenderUtil.renderCustomSizedTexture(matrix, this.leftPos + 99, this.topPos + 21, (float) this.imageWidth, 0.0F, 28.0F, 21.0F, 256.0F, 256.0F);
        }*/
    }

    @Override
    protected void renderLabels(PoseStack matrix, int mouseX, int mouseY) {
        RenderUtil.drawCenteredScaledMessage(matrix, Minecraft.getInstance().font, this.getTitle(), 90, 21, 1.0F, 4210752, RenderUtil.StringRenderType.NORMAL);
    }
}
