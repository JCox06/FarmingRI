package uk.co.jcox.farmingri.common.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.*;
import uk.co.jcox.farmingri.common.block.entity.MillGrinderBlockEntity;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;
import uk.co.jcox.farmingri.common.block.entity.WoodPowerGenBlockEntity;
import uk.co.jcox.farmingri.common.container.ThreshingTableContainer;
import uk.co.jcox.farmingri.common.container.WoodPowerGenContainer;
import uk.co.jcox.farmingri.common.datagen.DataGeneration;

public class Registration {

    private Registration() {

    }


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmingRI.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmingRI.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FarmingRI.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FarmingRI.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarmingRI.MODID);



    public static void registerAll(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        TILES.register(modEventBus);
        MENUS.register(modEventBus);

        modEventBus.register(DataGeneration.class);
        modEventBus.register(CommonSetup.class);
        modEventBus.register(ClientSetup.class);
    }


    //Power Generator
    public static final RegistryObject<Block> BLOCK_WOOD_POWER_GENERATOR = BLOCKS.register("wood_power_gen", () -> new WoodPowerGenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).lightLevel(state -> state.getValue(WoodPowerGenBlock.POWERED) == WoodPowerGenBlock.State.OFF ? 0 : 2).strength(1.0f, 4.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> ITEM_WOOD_POWER_GENERATOR = ITEMS.register("wood_power_gen", () -> new BlockItem(BLOCK_WOOD_POWER_GENERATOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<WoodPowerGenBlockEntity>> TILE_WOOD_POWER_GENERATOR = TILES.register("wood_power_gen", () ->
            BlockEntityType.Builder.of(WoodPowerGenBlockEntity::new, BLOCK_WOOD_POWER_GENERATOR.get()).build(null));
    public static final RegistryObject<MenuType<WoodPowerGenContainer>> CONTAINER_WOOD_POWER_GEN = MENUS.register("wood_power_gen",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new WoodPowerGenContainer(windowId, inv.player, data.readBlockPos()))));

    //Long Wheat, Grass (tall fescue), threshing tables, and straw
    public static final RegistryObject<Block> BLOCK_LONG_WHEAT = BLOCKS.register("long_wheat", () -> new LongWheatBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> BLOCK_LONG_WHEAT_UPPER = BLOCKS.register("long_wheat_upper", () -> new LongWheatBlockUpper(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> ITEM_LONG_WHEAT_SEEDS = ITEMS.register("long_wheat_seeds", () -> new ItemNameBlockItem(BLOCK_LONG_WHEAT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_LONG_WHEAT_SHEAF = ITEMS.register("long_wheat_sheaf", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Block> BLOCK_TALL_FESCUE = BLOCKS.register("tall_fescue", () -> new TallFescueBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistryObject<Block> BLOCK_TALL_FESCUE_UPPER = BLOCKS.register("tall_fescue_upper", () -> new TallFescueBlockUpper(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistryObject<Item> ITEM_TALL_FESCUE_SEEDS = ITEMS.register("tall_fescue_seeds", () -> new ItemNameBlockItem(BLOCK_TALL_FESCUE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_TALL_FESCUE_SHEAF = ITEMS.register("tall_fescue_sheaf", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ITEM_HAY = ITEMS.register("hay", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Block> BLOCK_HAY_BLOCK = BLOCKS.register("hay_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK).ignitedByLava()));
    public static final RegistryObject<Item> ITEM_HAY_BLOCK = ITEMS.register("hay_block", () -> new BlockItem(BLOCK_HAY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_LOOSE_GRASS = ITEMS.register("loose_grass", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Block> BLOCK_GRASS_BALE = BLOCKS.register("grass_bale", () -> new DryingGrassBaleBlock(BlockBehaviour.Properties.copy(BLOCK_HAY_BLOCK.get()).randomTicks()));
    public static final RegistryObject<Block> BLOCK_SILAGE_BALE = BLOCKS.register("silage_bale", () -> new Block(BlockBehaviour.Properties.copy(BLOCK_HAY_BLOCK.get())));

    public static final RegistryObject<Item> ITEM_SILAGE_BALE = ITEMS.register("silage_bale", () -> new BlockItem(BLOCK_SILAGE_BALE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_GRASS_BALE = ITEMS.register("grass_bale", () -> new BlockItem(BLOCK_GRASS_BALE.get(), new Item.Properties()));

    public static final RegistryObject<Block> BLOCK_STRAW_BLOCK = BLOCKS.register("straw_block", () -> new Block(BlockBehaviour.Properties.copy(BLOCK_HAY_BLOCK.get())));
    public static final RegistryObject<Block> BLOCK_STRAW_SLAB = BLOCKS.register("straw_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BLOCK_HAY_BLOCK.get())));
    public static final RegistryObject<Block> BLOCK_STRAW_STAIRS = BLOCKS.register("straw_stairs", () -> new StairBlock(BLOCK_STRAW_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(BLOCK_HAY_BLOCK.get())));
    public static final RegistryObject<Item> ITEM_STRAW_BLOCK = ITEMS.register("straw_block", () -> new BlockItem(BLOCK_STRAW_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_STRAW_SLAB = ITEMS.register("straw_slab", () -> new BlockItem(Registration.BLOCK_STRAW_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_STRAW_STAIRS = ITEMS.register("straw_stairs", () -> new BlockItem(Registration.BLOCK_STRAW_STAIRS.get(), new Item.Properties()));


    public static final RegistryObject<Block> BLOCK_THRESHING_TABLE = BLOCKS.register("threshing_table", () -> new ThreshingTableBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1.5f, 8.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<BlockEntityType<ThreshingTableBlockEntity>> TILE_THRESHING_TABLE = TILES.register("threshing_table", () -> BlockEntityType.Builder.of(
            ThreshingTableBlockEntity::new, BLOCK_THRESHING_TABLE.get()).build(null));
    public static final RegistryObject<Item> ITEM_THRESHING_TABLE = ITEMS.register("threshing_table", () -> new BlockItem(BLOCK_THRESHING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_CHAFF = ITEMS.register("chaff", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 25;
        }
    });
    public static final RegistryObject<MenuType<ThreshingTableContainer>> CONTAINER_THRESHING_TABLE = MENUS.register("threshing_container",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new ThreshingTableContainer(windowId, inv.player, data.readBlockPos()))));

    public static final RegistryObject<Item> ITEM_STRAW = ITEMS.register("straw", () -> new Item(new Item.Properties()));


    //Mill Grinding block
    public static final RegistryObject<Item> ITEM_FLOUR = ITEMS.register("flour", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Block> BLOCK_MILL_GRINDER_BLOCK = BLOCKS.register("mill_grinder", () -> new MillGrinderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0f, 8.0f)));
    public static final RegistryObject<Item> ITEM_MILL_GRINDER_BLOCK = ITEMS.register("mill_grinder", () -> new BlockItem(BLOCK_MILL_GRINDER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<MillGrinderBlockEntity>> TILE_MILL_GRINDER_BLOCK = TILES.register("mill_grinder",
            () -> BlockEntityType.Builder.of(MillGrinderBlockEntity::new, BLOCK_MILL_GRINDER_BLOCK.get()).build(null));

    //Creative Menu
    public static final String TITLE_CREATIVE_TAB = "inventory." + FarmingRI.MODID + ".creative_menu";
    public static final RegistryObject<CreativeModeTab> CREATIVE_MODE = CREATIVE_TABS.register("main_menu", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable(TITLE_CREATIVE_TAB))
                    .icon(() -> new ItemStack(ITEM_LONG_WHEAT_SEEDS.get()))
                    .displayItems((params, out) -> {
                        out.accept(new ItemStack(ITEM_LONG_WHEAT_SEEDS.get()));
                        out.accept(new ItemStack(ITEM_LONG_WHEAT_SHEAF.get()));
                        out.accept(new ItemStack(ITEM_TALL_FESCUE_SEEDS.get()));
                        out.accept(new ItemStack(ITEM_TALL_FESCUE_SHEAF.get()));
                        out.accept(new ItemStack(ITEM_LOOSE_GRASS.get()));
                        out.accept(new ItemStack(ITEM_SILAGE_BALE.get()));
                        out.accept(new ItemStack(ITEM_GRASS_BALE.get()));
                        out.accept(new ItemStack(ITEM_HAY.get()));
                        out.accept(new ItemStack(ITEM_HAY_BLOCK.get()));
                        out.accept(new ItemStack(ITEM_STRAW_BLOCK.get()));
                        out.accept(new ItemStack(ITEM_STRAW_SLAB.get()));
                        out.accept(new ItemStack(ITEM_STRAW_STAIRS.get()));
                        out.accept(new ItemStack(ITEM_THRESHING_TABLE.get()));
                        out.accept(new ItemStack(ITEM_CHAFF.get()));
                        out.accept(new ItemStack(ITEM_STRAW.get()));
                        out.accept(new ItemStack(ITEM_WOOD_POWER_GENERATOR.get()));
                    })
                    .build());

}
