package uk.co.jcox.farmingri.common.block.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.capabilities.EasyEnergyStore;
import uk.co.jcox.farmingri.common.capabilities.EasyItemStore;
import uk.co.jcox.farmingri.common.capabilities.ExtractOnlyItemHandler;
import uk.co.jcox.farmingri.common.container.ThreshingTableContainer;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreshingTableBlockEntity extends BlockEntity implements MenuProvider {

    private static final Logger logger = LogUtils.getLogger();

    private static final Map<Item, List<ItemStack>> recipes = new HashMap<>();

    public static final String MENU_TITLE = "menu." + FarmingRI.MODID + ".threshing_table";

    public static final String NBT_INPUT_INV = "InputItems";
    public static final String NBT_OUTPUT_INV =  "OutputItems";
    public static final String NBT_ENERGY = "Energy";
    public static final String NBT_COUNTER = "Counter";


    public static final int INPUT_SLOTS  = 1;
    public static final int OUTPUT_SLOTS = 3;

    public static final int ENERGY_CAPACITY = 200;
    public static final int ENERGY_REQUIRED = 10;

    public static final int CRAFTING_TICKS = 10;


    private final EasyItemStore itemInputInv = new EasyItemStore(stack -> recipes.containsKey(stack.getItem()), this::setChanged, INPUT_SLOTS);

    private final EasyItemStore itemOutputInv = new EasyItemStore((stack -> true), this::setChanged, OUTPUT_SLOTS);
    private final ExtractOnlyItemHandler itemOutputInvWrapper = new ExtractOnlyItemHandler(itemOutputInv);

    public final EasyEnergyStore energyStore = new EasyEnergyStore(ENERGY_CAPACITY, this::setChanged);


    private final LazyOptional<IItemHandler> itemInputOptional = LazyOptional.of(() -> itemInputInv);
    private final LazyOptional<IItemHandler> itemOutputOptional = LazyOptional.of(() -> itemOutputInv);
    private final LazyOptional<IEnergyStorage> energyStorageOptional = LazyOptional.of(() -> energyStore);

    private final LazyOptional<IItemHandler> combinedItems = LazyOptional.of(() -> EasyItemStore.observableMerged(itemInputInv, itemOutputInv));

    private int counter = 0;

    //This is called during FarmingRI's Common Setup event. Ensure Enqueue work
    public static void bootStrap() {
        registerRecipe(Registration.ITEM_LONG_WHEAT_SHEAF.get(),new ItemStack(Registration.ITEM_LONG_WHEAT_SEEDS.get(), 2), new ItemStack(Registration.ITEM_STRAW.get(), 2), new ItemStack(Registration.ITEM_CHAFF.get(), 4));
        registerRecipe(Registration.ITEM_TALL_FESCUE_SHEAF.get(), new ItemStack(Registration.ITEM_TALL_FESCUE_SEEDS.get(), 2), new ItemStack(Registration.ITEM_LOOSE_GRASS.get(), 2), new ItemStack(Registration.ITEM_CHAFF.get(), 4));
    }


    /**
     *Allows third parties to register their own recipes in here
     *You must call this method after forge registration. Ensure Enqueue Work
     * as these methods are not thread safe
     * The order you put the output ItemStacks will determine what slots they take up in
     * the threshingTable menu
     *
     * @param input The item that is being threshed
     * @param outputs Results of the threshed items, this array must be no larger
     *                than 3 items and no smaller than 1 item
     */
    public static void registerRecipe(Item input, ItemStack ... outputs) {
        if (recipes.containsKey(input)) {
            logger.error("Duplicate Key found for threshing table recipes. Ignoring!");
            return;
        }

        List<ItemStack> outputList = Arrays.stream(outputs).toList();

        if (outputList.size() > 3) {
            logger.error("Fatal! List is too big. Crashing!");
            throw new IllegalStateException("Recipe has max space of 3 available items");
        }

        if (outputList.isEmpty()) {
            logger.error("Fatal! List is empty. Crashing!");
            throw new IllegalStateException("Recipe has no output (must not be empty)");
        }

        recipes.put(input, outputList);
    }

    public static final int DATA_ACCESS_CRAFTING = 0;
    public static final int DATA_ACCESS_ENERGY = 1;
    public static final int DATA_SIZE = 2;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int accessID) {
            if (accessID == DATA_ACCESS_CRAFTING) {
                return counter;
            }

            if (accessID == DATA_ACCESS_ENERGY) {
                return energyStore.getEnergyStored();
            }

            return 0;
        }

        @Override
        public void set(int accessID, int value) {
            if (accessID == DATA_ACCESS_CRAFTING) {
                counter = value;
            }
            if (accessID == DATA_ACCESS_ENERGY) {
                energyStore.receiveEnergy(value, false);
            }
        }

        @Override
        public int getCount() {
            return DATA_SIZE;
        }
    };

    public ThreshingTableBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.TILE_THRESHING_TABLE.get(), pos, state);
    }


    public void tickServer() {

        //Check to see if we have enough energy
        if (energyStore.getEnergyStored() < ENERGY_REQUIRED) {
            counter = 0;
            return;
        }

        //Check to see if input item is part of recipe
        Item input = itemInputInv.getStackInSlot(0).getItem();
        if (! recipes.containsKey(input)) {
            counter = 0;
            return;
        }


        //Check to see if the output slots are free
        List<ItemStack> outputItems = recipes.get(input);
        for (int i = 0; i < outputItems.size(); i++) {
            Item itemToOutput = outputItems.get(i).getItem();
            ItemStack itemsInOuput = itemOutputInv.getStackInSlot(i);

            //Check if the output is not empty and that the item output is not part of the recipe
            if ((! itemsInOuput.isEmpty()) && (! itemToOutput.equals(outputItems.get(i).getItem()))) {
                counter = 0;
                return;
            }

            //Check to see if the item size is not full when adding the extra items
            if (itemsInOuput.getCount() + outputItems.get(i).getCount() > outputItems.get(i).getMaxStackSize()) {
                counter = 0;
                return;
            }
        }

        //Now we can check if the crafting tick is equal to the requirment
        if (counter >= CRAFTING_TICKS) {
            //if so produce some new items according to recipe
            consumeItems();
            counter = 0;
            return;
        }

        energyStore.extractEnergy(ENERGY_REQUIRED, false);
        counter++;
    }


    private void consumeItems() {
        //Our input item
        Item item = itemInputInv.extractItem(0, 1, false).getItem();

        for (int i = 0; i < recipes.get(item).size(); i++) {
            itemOutputInv.insertItem(i, recipes.get(item).get(i).copy(), false);
        }
    }



    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);

        compound.put(NBT_INPUT_INV, itemInputInv.serializeNBT());
        compound.put(NBT_OUTPUT_INV, itemOutputInv.serializeNBT());
        compound.put(NBT_ENERGY, energyStore.serializeNBT());
        compound.putInt(NBT_COUNTER, counter);
    }


    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);

        if (compound.contains(NBT_INPUT_INV)) {
            itemInputInv.deserializeNBT(compound.getCompound(NBT_INPUT_INV));
        }

        if (compound.contains(NBT_OUTPUT_INV)) {
            itemOutputInv.deserializeNBT(compound.getCompound(NBT_OUTPUT_INV));
        }

        if (compound.contains(NBT_ENERGY)) {
            energyStore.deserializeNBT(compound.get(NBT_ENERGY));
        }

        if (compound.contains(NBT_COUNTER)) {
            this.counter = compound.getInt(NBT_COUNTER);
        }
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemInputOptional.invalidate();
        itemOutputOptional.invalidate();
        energyStorageOptional.invalidate();
        combinedItems.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorageOptional.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return combinedItems.cast();
            }

            if (side == Direction.DOWN) {
                return itemOutputOptional.cast();
            }

            return itemInputOptional.cast();
        }

        return super.getCapability(cap, side);
    }


    public void destroyed() {
        this.itemInputInv.dropContents(level, getBlockPos());
        this.itemOutputInv.dropContents(level, getBlockPos());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(MENU_TITLE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inventory, @NotNull Player player) {
        return new ThreshingTableContainer(windowId, player, getBlockPos(), dataAccess);
    }

    public EasyItemStore getItemInputInv() {
        return itemInputInv;
    }

    public ExtractOnlyItemHandler getItemOutputInv() {
        return itemOutputInvWrapper;
    }

    public EasyEnergyStore getEnergyStore() {
        return energyStore;
    }
}
