package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.concurrent.CompletableFuture;

public class FRIItemTagsProvider extends ItemTagsProvider {

    public FRIItemTagsProvider(PackOutput packData, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(packData, lookupProvider, blockTags.contentsGetter(), FarmingRI.MODID, helper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.SEEDS)
                .add(Registration.ITEM_LONG_WHEAT_SEEDS.get())
                .add(Registration.ITEM_TALL_FESCUE_SEEDS.get());
    }
}
