package uk.co.jcox.farmingri.common.datagen;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import uk.co.jcox.farmingri.FarmingRI;

import java.util.Collections;
import java.util.List;


public class DataGeneration {

    @SubscribeEvent
    public static void gatherDataListener(GatherDataEvent event){
        final DataGenerator generator = event.getGenerator();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final PackOutput packOutput = event.getGenerator().getPackOutput();


        FarmingRI.LOGGER.info("Starting generators for CLIENT ASSETS");
        generator.addProvider(event.includeClient(), new FRILangProvider(packOutput));
        generator.addProvider(event.includeClient(), new FRIBlockStateProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeClient(), new FRIItemModelProvider(packOutput, existingFileHelper));


        FarmingRI.LOGGER.info("Starting generators for SERVER DATA");
        BlockTagsProvider blockTagsProvider = new FRIBlockTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new FRIItemTagsProvider(packOutput, event.getLookupProvider(), blockTagsProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(FRIBlockLoot::new, LootContextParamSets.BLOCK))));
        //Thanks to McJty for Loot table generation in 1.20
        generator.addProvider(event.includeServer(), new FRRecipeProvider(packOutput));

    }
}
