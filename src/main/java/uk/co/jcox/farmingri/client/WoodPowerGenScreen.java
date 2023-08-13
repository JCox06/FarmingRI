package uk.co.jcox.farmingri.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.container.WoodPowerGenContainer;

public class WoodPowerGenScreen extends AbstractContainerScreen<WoodPowerGenContainer> {


    private static final ResourceLocation GUI = new ResourceLocation(FarmingRI.MODID, "textures/gui/container/wood_power_gen.png");

    public static final String TOOLTIP = "tooltip." + FarmingRI.MODID + ".wood_power_gen_screen";


    public WoodPowerGenScreen(WoodPowerGenContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        this.renderBackground(graphics);

        //man ui Image
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
