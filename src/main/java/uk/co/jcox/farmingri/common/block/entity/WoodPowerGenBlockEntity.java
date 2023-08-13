package uk.co.jcox.farmingri.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.capabilities.EasyEnergyStore;
import uk.co.jcox.farmingri.common.capabilities.EasyItemStore;
import uk.co.jcox.farmingri.common.container.WoodPowerGenContainer;
import uk.co.jcox.farmingri.common.setup.Registration;

public class WoodPowerGenBlockEntity extends BlockEntity implements MenuProvider {


    public static final int INV_STORAGE = 1;
    public static final int POWER_STORAGE = 2000;
    public static final int POWER_GEN_PER_TICK = 10;
    public static final int POWER_SEND_PER_TICK = 20;


    public static final String MENU_NAME = "menu." + FarmingRI.MODID + ".wood_power_gen";

    public static final String NBT_COUNTER = "Counter";
    public static final String NBT_ITEM_INPUT = "ItemInput";
    public static final String NBT_ENERGY = "EnergyStorage";



    private final EasyItemStore inputItemHandler = new EasyItemStore((ItemStack stack) -> stack.is(ItemTags.LOGS), this::setChanged, INV_STORAGE);
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> inputItemHandler);

    private final EnergyStorage energyStorage = new EasyEnergyStore(POWER_STORAGE, this::setChanged);
    private final LazyOptional<IEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);


    private int counter = 0;

    public WoodPowerGenBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.TILE_WOOD_POWER_GENERATOR.get(), pos, state);
    }



    public void tickServer() {

        //Generate Power
        if (counter > 0) {
            energyStorage.receiveEnergy(POWER_GEN_PER_TICK, false);
            counter--;
        }


        //increment counter from items
        if (counter <= 0 && energyStorage.getEnergyStored() < POWER_STORAGE) {
            ItemStack stack = inputItemHandler.extractItem(0, 1, false);
            if (stack.is(ItemTags.LOGS)) {
                counter = 10;
            }
        }


        //Send out power
        if (energyStorage.getEnergyStored() <= 0) {
            return;
        }

        for (Direction dir: Direction.values()) {

            if (level == null) {
                return;
            }

            BlockEntity entity = level.getBlockEntity(getBlockPos().relative(dir));
            if (entity != null) {
                LazyOptional<IEnergyStorage> energySupport = entity.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite());
                energySupport.map( (IEnergyStorage otherStore) -> {
                    if (otherStore.canReceive()) {
                        int received = otherStore.receiveEnergy(Math.min(energyStorage.getEnergyStored(), POWER_SEND_PER_TICK), false);
                        energyStorage.extractEnergy(received, false);
                    }
                    return true;
                });
            }

        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put(NBT_ITEM_INPUT, inputItemHandler.serializeNBT());
        compound.put(NBT_ENERGY, energyStorage.serializeNBT());
        compound.putInt(NBT_COUNTER, counter);
    }


    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);

        if (compound.contains(NBT_ITEM_INPUT)) {
            this.inputItemHandler.deserializeNBT(compound.getCompound(NBT_ITEM_INPUT));
        }

        if (compound.contains(NBT_ENERGY)) {
            this.energyStorage.deserializeNBT(compound.get(NBT_ENERGY));
        }

        if (compound.contains(NBT_COUNTER)) {
            this.counter = compound.getInt(NBT_COUNTER);
        }
    }


    @Override
    public void invalidateCaps() {
        this.energyStorageLazyOptional.invalidate();
        this.itemHandlerLazyOptional.invalidate();
    }


    public void destroyed() {
        this.inputItemHandler.dropContents(level, getBlockPos());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {

            if (side == Direction.UP) {
                return itemHandlerLazyOptional.cast();
            }
        }

        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorageLazyOptional.cast();
        }


        return super.getCapability(cap, side);
    }

    public EasyItemStore getInputItemHandler() {
        return inputItemHandler;
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable(MENU_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player) {
        return new WoodPowerGenContainer(windowID, player, getBlockPos());
    }
}
