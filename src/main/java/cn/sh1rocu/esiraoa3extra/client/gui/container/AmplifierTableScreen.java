
package cn.sh1rocu.esiraoa3extra.client.gui.container;

import cn.sh1rocu.esiraoa3extra.container.AmplifierTableContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.tslat.aoa3.util.RenderUtil;

public class AmplifierTableScreen extends ContainerScreen<AmplifierTableContainer> {
    private static final ResourceLocation textures = new ResourceLocation("esiraoa3extra", "textures/gui/containers/amplifier_table.png");

    public AmplifierTableScreen(AmplifierTableContainer container, PlayerInventory inv, ITextComponent guiTitle) {
        super(container, inv, guiTitle);
        this.imageWidth = 196;
        this.imageHeight = 200;

    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        RenderSystem.blendColor(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bind(textures);
        this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
/*        AmplifierTableContainer container = this.getMenu();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bind(textures);
        RenderUtil.renderCustomSizedTexture(matrix, this.leftPos, this.topPos, 0.0F, 0.0F, 196, 200, 196, 200);
        if ((!container.inputs.getItem(0).isEmpty() || !container.inputs.getItem(1).isEmpty() || !container.inputs.getItem(2).isEmpty()) && container.leftNewEquip.getItem(0).isEmpty()) {
            RenderUtil.renderCustomSizedTexture(matrix, this.leftPos + 99, this.topPos + 21, (float) this.imageWidth, 0.0F, 28.0F, 21.0F, 256.0F, 256.0F);
        }*/
    }

    @Override
    protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY) {
        RenderUtil.drawCenteredScaledMessage(matrix, Minecraft.getInstance().font, this.getTitle(), 90, 21, 1.0F, 4210752, RenderUtil.StringRenderType.NORMAL);
    }
}
