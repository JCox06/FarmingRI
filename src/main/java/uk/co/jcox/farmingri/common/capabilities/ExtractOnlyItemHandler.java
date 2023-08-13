package uk.co.jcox.farmingri.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;


//Thanks to Draco18s (User on Forge Forums) who suggested to wrap the handler
//Link to Forum Post I found: https://forums.minecraftforge.net/topic/86173-1152-add-item-to-output-slot-player-can-only-remove/


//Simple class to delegate all actions to the internal handler apart from inserting the item in
public class ExtractOnlyItemHandler extends ItemStackHandler {

    private final ItemStackHandler internalHandler;

    public ExtractOnlyItemHandler(ItemStackHandler handler) {
        internalHandler = handler;
    }

    @Override
    public void setSize(int size) {
        internalHandler.setSize(size);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        internalHandler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return internalHandler.getSlots();
    }

    @Override
    @NotNull
    public ItemStack getStackInSlot(int slot) {
        return internalHandler.getStackInSlot(slot);
    }

    @Override
    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        //This method here is the most important.
        //It stops the item from actually entering the slot serverside
        return ItemStack.EMPTY;
    }

    @Override
    @NotNull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return internalHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return internalHandler.getSlotLimit(slot);
    }


    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        //Stops the client from allowing the item to be placed in the slot
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        return internalHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        internalHandler.deserializeNBT(nbt);
    }
}
