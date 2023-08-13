package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.client.ThreshingTableScreen;
import uk.co.jcox.farmingri.client.WoodPowerGenScreen;
import uk.co.jcox.farmingri.common.block.*;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;
import uk.co.jcox.farmingri.common.block.entity.WoodPowerGenBlockEntity;
import uk.co.jcox.farmingri.common.setup.Registration;

public class FRILangProvider extends LanguageProvider {


    public FRILangProvider(PackOutput output) {
        super(output, FarmingRI.MODID, "en_us");
    }


    @Override
    protected void addTranslations() {
        add(Registration.TITLE_CREATIVE_TAB, "Farming Ri");
        add(Registration.BLOCK_LONG_WHEAT.get(), "Long Wheat");
        add(Registration.BLOCK_LONG_WHEAT_UPPER.get(), "Long Wheat Upper");
        add(Registration.ITEM_LONG_WHEAT_SEEDS.get(), "Long Wheat Seeds");
        add(Registration.ITEM_LONG_WHEAT_SHEAF.get(), "Bundle of Wheat");
        add(Registration.BLOCK_THRESHING_TABLE.get(), "Threshing Machine");
        add(Registration.ITEM_CHAFF.get(), "Chaff");
        add(Registration.ITEM_STRAW.get(), "Straw");
        add(Registration.BLOCK_STRAW_STAIRS.get(), "Straw Bale Stairs");
        add(Registration.BLOCK_STRAW_BLOCK.get(), "Straw Bale Block");
        add(Registration.BLOCK_STRAW_SLAB.get(), "Straw Bale Slab");
        add(Registration.ITEM_TALL_FESCUE_SEEDS.get(), "Tall Fescue Seeds");
        add(Registration.ITEM_TALL_FESCUE_SHEAF.get(), "Bundle of Fescue");
        add(Registration.ITEM_HAY.get(), "Hay");
        add(Registration.BLOCK_SILAGE_BALE.get(), "Silage Bale");
        add(Registration.BLOCK_GRASS_BALE.get(), "Grass Bale");
        add(Registration.BLOCK_HAY_BLOCK.get(), "Hay Bale");
        add(DryingGrassBaleBlock.TOOLTIP, "Keep this out of the rain so it can dry");
        add(Registration.ITEM_LOOSE_GRASS.get(), "Loose Grass");

        add(Registration.BLOCK_WOOD_POWER_GENERATOR.get(), "Wood PowerGen");
        add(WoodPowerGenBlock.TOOLTIP, "CAP %1$s, GEN %2$s/t, SEND %3$s/t");
        add(WoodPowerGenScreen.TOOLTIP, "Place wood logs here to quickly combust them to produce cheap energy");
        add(WoodPowerGenBlockEntity.MENU_NAME, "Wood Power Generator");

        add(ThreshingTableBlock.TOOLTIP, "Separates plant stalks from the grain");
        add(ThreshingTableBlock.TOOLTIP_INFO, "CAP %1$s FE, REC %2$s FE/t");
        add(ThreshingTableBlockEntity.MENU_TITLE, "Threshing Machine");
        add(ThreshingTableScreen.TOOLTIP_ENERGY, "Energy: ");
        add(ThreshingTableScreen.TOOLTIP_WARNING, "WARNING: Low Power Supply!");


        add(MillGrinderBlock.TOOLTIP, "Turn seeds into flour");
        add(Registration.BLOCK_MILL_GRINDER_BLOCK.get(), "Mill Grinder");
        add(Registration.ITEM_FLOUR.get(), "Flour");
    }
}
