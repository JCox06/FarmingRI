package uk.co.jcox.farmingri.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;
import uk.co.jcox.farmingri.common.container.ThreshingTableContainer;

public class ThreshingTableScreen extends AbstractContainerScreen<ThreshingTableContainer> {

    public static final int ARROW_TEXEL_WIDTH = 23;
    public static final int ARROW_TEXEL_X = 176;
    public static final int ARROW_TEXEL_Y = 14;
    public static final int ARROW_TEXEL_HEIGHT = 16;

    private static final ResourceLocation GUI = new ResourceLocation(FarmingRI.MODID, "textures/gui/container/threshing_table.png");

    public static final String TOOLTIP_WARNING = "tooltip." + FarmingRI.MODID + ".threshing_table_energy_warning";
    public static final String TOOLTIP_ENERGY = "tooltip." + FarmingRI.MODID + ".threshing_table_energy";

    public ThreshingTableScreen(ThreshingTableContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        this.renderBackground(graphics);

        //man ui Image
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);

        //Render the arrow:
        float completedPercentage = (float) getMenu().getCraftingData() / ThreshingTableBlockEntity.CRAFTING_TICKS;
        int texelCoverage = Math.min((int) (completedPercentage * ARROW_TEXEL_WIDTH), ARROW_TEXEL_WIDTH);
        graphics.blit(GUI, x+ 67, y + 16, ARROW_TEXEL_X, ARROW_TEXEL_Y, texelCoverage, ARROW_TEXEL_HEIGHT);
        graphics.blit(GUI, x+ 67, y + 39, ARROW_TEXEL_X, ARROW_TEXEL_Y, texelCoverage, ARROW_TEXEL_HEIGHT);
        graphics.blit(GUI, x+ 67, y + 62, ARROW_TEXEL_X, ARROW_TEXEL_Y, texelCoverage, ARROW_TEXEL_HEIGHT);

        graphics.drawString(Minecraft.getInstance().font, Component.translatable(TOOLTIP_ENERGY), x + 120, y + 20, 0xffffff);
        graphics.drawString(Minecraft.getInstance().font, Component.literal(Integer.toString( menu.getEnergyStored())), x + 120, y + 35, 0xFFA07A);

        if (menu.getEnergyStored() <= ThreshingTableBlockEntity.ENERGY_REQUIRED) {
            graphics.renderTooltip(Minecraft.getInstance().font, Component.translatable(TOOLTIP_WARNING), x, y - 5);
        }
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
