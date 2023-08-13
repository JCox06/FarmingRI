package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.LongWheatBlock;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.Map;
import java.util.stream.Collectors;

public class FRIBlockLoot extends VanillaBlockLoot {


    @Override
    protected void generate() {
        LootItemCondition.Builder longWheatCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(Registration.BLOCK_LONG_WHEAT.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(LongWheatBlock.AGE, LongWheatBlock.MAX_AGE));


        LootItemCondition.Builder longWheatUpperCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(Registration.BLOCK_LONG_WHEAT_UPPER.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(LongWheatBlock.AGE, LongWheatBlock.MAX_AGE));

        this.add(Registration.BLOCK_LONG_WHEAT.get(), this.applyExplosionDecay(Registration.BLOCK_LONG_WHEAT.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registration.ITEM_LONG_WHEAT_SHEAF.get())))
                        .withPool(LootPool.lootPool().when(longWheatCondition)
                                .add(LootItem.lootTableItem(Registration.ITEM_LONG_WHEAT_SHEAF.get())
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,0.5f, 4 ))))));

        this.add(Registration.BLOCK_LONG_WHEAT_UPPER.get(), this.applyExplosionDecay(Registration.BLOCK_LONG_WHEAT_UPPER.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registration.ITEM_LONG_WHEAT_SHEAF.get())))
                        .withPool(LootPool.lootPool().when(longWheatUpperCondition)
                                .add(LootItem.lootTableItem(Registration.ITEM_LONG_WHEAT_SHEAF.get())
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,0.5f, 1 ))))));


        this.add(Registration.BLOCK_TALL_FESCUE_UPPER.get(), this.applyExplosionDecay(Registration.BLOCK_TALL_FESCUE_UPPER.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registration.ITEM_TALL_FESCUE_SHEAF.get())))
                        .withPool(LootPool.lootPool().when(longWheatUpperCondition)
                                .add(LootItem.lootTableItem(Registration.ITEM_TALL_FESCUE_SHEAF.get())
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,0.5f, 1 ))))));


        //Drop Nothing, only the top part contains seeds
        this.dropOther(Registration.BLOCK_TALL_FESCUE.get(), Items.AIR);


        this.dropSelf(Registration.BLOCK_THRESHING_TABLE.get());


        this.dropSelf(Registration.BLOCK_STRAW_BLOCK.get());
        this.dropSelf(Registration.BLOCK_STRAW_STAIRS.get());
        this.dropSelf(Registration.BLOCK_STRAW_SLAB.get());

        this.dropSelf(Registration.BLOCK_WOOD_POWER_GENERATOR.get());

        this.dropSelf(Registration.BLOCK_HAY_BLOCK.get());

        this.dropSelf(Registration.BLOCK_SILAGE_BALE.get());
        this.dropSelf(Registration.BLOCK_GRASS_BALE.get());

        this.dropSelf(Registration.BLOCK_MILL_GRINDER_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(FarmingRI.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
