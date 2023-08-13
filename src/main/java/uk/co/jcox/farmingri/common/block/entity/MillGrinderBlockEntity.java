package uk.co.jcox.farmingri.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import uk.co.jcox.farmingri.common.capabilities.EasyEnergyStore;
import uk.co.jcox.farmingri.common.capabilities.EasyItemStore;
import uk.co.jcox.farmingri.common.capabilities.ExtractOnlyItemHandler;
import uk.co.jcox.farmingri.common.setup.Registration;

public class MillGrinderBlockEntity extends BlockEntity {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 6;
    public static final int ENERGY_STORE = 400;
    public static final int ENERGY_REQUIREMENT = 40;

    public static final String NBT_INPUT_INV = "InputInv";
    public static final String NBT_OUTPUT_INV = "OutputInv";
    public static final String NBT_COUNTER = "Counter";


    private final ItemStackHandler inputInventory = new EasyItemStore(item -> true, this::setChanged, INPUT_SLOTS);
    private final ItemStackHandler internalOutputInventory = new EasyItemStore(item -> true, this::setChanged, OUTPUT_SLOTS);
    private final ItemStackHandler extractOnlyOuputInventory = new ExtractOnlyItemHandler(internalOutputInventory);

    private final EasyEnergyStore energyStore = new EasyEnergyStore(ENERGY_STORE, this::setChanged);

    private final LazyOptional<IItemHandler> itemInputLazy = LazyOptional.of(() -> inputInventory);
    private final LazyOptional<IItemHandler> itemOutputLazy = LazyOptional.of(() -> internalOutputInventory);
    private final LazyOptional<CombinedInvWrapper> combinedLazy = LazyOptional.of(() -> EasyItemStore.observableMerged(inputInventory, internalOutputInventory));

    public MillGrinderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(Registration.TILE_MILL_GRINDER_BLOCK.get(), pPos, pBlockState);
    }



    public void tickServer() {

    }


    public ItemStackHandler getInputInventory() {
        return inputInventory;
    }

    public ItemStackHandler getExtractOnlyOuputInventory() {
        return extractOnlyOuputInventory;
    }

    public EasyEnergyStore getEnergyStore() {
        return energyStore;
    }
}
