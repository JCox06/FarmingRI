package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.setup.Registration;

public class FRIItemModelProvider extends ItemModelProvider {

    public FRIItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FarmingRI.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        basicItem(Registration.ITEM_LONG_WHEAT_SEEDS.get());
        basicItem(Registration.ITEM_LONG_WHEAT_SHEAF.get());
        basicItem(Registration.ITEM_CHAFF.get());
        basicItem(Registration.ITEM_STRAW.get());
        basicItem(Registration.ITEM_TALL_FESCUE_SEEDS.get());
        basicItem(Registration.ITEM_TALL_FESCUE_SHEAF.get());
        basicItem(Registration.ITEM_HAY.get());
        basicItem(Registration.ITEM_LOOSE_GRASS.get());
        basicItem(Registration.ITEM_FLOUR.get());

        withExistingParent(Registration.ITEM_WOOD_POWER_GENERATOR.getId().getPath(), modLoc("block/" + Registration.BLOCK_WOOD_POWER_GENERATOR.getId().getPath() + "_gen"));

        blockParent(Registration.ITEM_THRESHING_TABLE);
        blockParent(Registration.ITEM_HAY_BLOCK);

        blockParent(Registration.ITEM_STRAW_BLOCK);
        blockParent(Registration.ITEM_STRAW_SLAB);
        blockParent(Registration.ITEM_STRAW_STAIRS);
        blockParent(Registration.ITEM_GRASS_BALE);
        blockParent(Registration.ITEM_SILAGE_BALE);
    }


    public void blockParent(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(), modLoc("block/" + item.getId().getPath()));
    }

}
