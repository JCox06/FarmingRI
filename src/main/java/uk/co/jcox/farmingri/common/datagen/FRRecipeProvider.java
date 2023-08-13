package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.function.Consumer;

public class FRRecipeProvider extends RecipeProvider {


    public FRRecipeProvider(PackOutput packedOutput ) {
        super(packedOutput );
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {


        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.BLOCK_STRAW_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Registration.ITEM_STRAW.get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ITEM_STRAW.get()));

        builder.save(writer);


        builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.BLOCK_STRAW_STAIRS.get())
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', Registration.BLOCK_STRAW_BLOCK.get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.BLOCK_STRAW_BLOCK.get()));

        builder.save(writer);


        builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.BLOCK_STRAW_SLAB.get())
                .pattern("###")
                .define('#', Registration.BLOCK_STRAW_BLOCK.get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.BLOCK_STRAW_BLOCK.get()));
        builder.save(writer);


        builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.ITEM_GRASS_BALE.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Registration.ITEM_LOOSE_GRASS.get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ITEM_LOOSE_GRASS.get()));


        builder.save(writer);


        builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.ITEM_SILAGE_BALE.get())
                .pattern("###")
                .pattern("#a#")
                .pattern("###")
                .define('#', Items.PAPER)
                .define('a', Registration.ITEM_GRASS_BALE.get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.BLOCK_GRASS_BALE.get()));

        builder.save(writer);
    }
}
