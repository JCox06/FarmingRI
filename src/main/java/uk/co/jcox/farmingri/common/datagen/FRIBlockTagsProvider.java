package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.concurrent.CompletableFuture;

public class FRIBlockTagsProvider extends BlockTagsProvider {

    public FRIBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FarmingRI.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.CROPS).add(Registration.BLOCK_LONG_WHEAT.get());


        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.BLOCK_THRESHING_TABLE.get())
                .add(Registration.BLOCK_WOOD_POWER_GENERATOR.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(Registration.BLOCK_STRAW_BLOCK.get())
                .add(Registration.BLOCK_STRAW_SLAB.get())
                .add(Registration.BLOCK_STRAW_STAIRS.get())
                .add(Registration.BLOCK_HAY_BLOCK.get())
                .add(Registration.BLOCK_GRASS_BALE.get())
                .add(Registration.BLOCK_SILAGE_BALE.get());

            tag(BlockTags.NEEDS_IRON_TOOL)
                    .add(Registration.BLOCK_THRESHING_TABLE.get())
                    .add(Registration.BLOCK_WOOD_POWER_GENERATOR.get());
    }
}
