package uk.co.jcox.farmingri.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;
import uk.co.jcox.farmingri.common.setup.Registration;

public class ThreshingTableContainer extends InvMenu {


    public static final int INPUT_SLOTS_SIZE = 1;

    public ThreshingTableContainer(int windowID, Player player, BlockPos pos) {
        this(windowID, player, pos, new SimpleContainerData(ThreshingTableBlockEntity.DATA_SIZE));
    }


    public ThreshingTableContainer(int windowID, Player player, BlockPos pos, ContainerData dataAccess) {
        super(Registration.CONTAINER_THRESHING_TABLE.get(), Registration.BLOCK_THRESHING_TABLE.get(), windowID, player, pos, dataAccess);
    }


    @Override
    protected int layoutEntitySlots(BlockEntity entity) {

        if (entity instanceof ThreshingTableBlockEntity thresher) {

            addSlot(new SlotItemHandler(thresher.getItemInputInv(), 0, 35, 38));

            addSlot(new SlotItemHandler(thresher.getItemOutputInv(), 0, 97, 15));

            addSlot(new SlotItemHandler(thresher.getItemOutputInv(), 1, 97, 38));

            addSlot(new SlotItemHandler(thresher.getItemOutputInv(), 2, 97, 61));
        }

        return 3;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = getSlot(index);

        if (!slot.hasItem()) {
            return stack;
        }

        ItemStack item = slot.getItem();
        stack = item.copy();

        //Check to see if the index is in the block entity inventories
        if (getOwnerRange().isValidIntValue(index)) {
            if (!this.moveItemStackTo(item, (int) getPlayerRange().getMinimum(), (int) getPlayerRange().getMaximum(), false)) {
                return ItemStack.EMPTY;
            }
        }

        if (getPlayerRange().isValidIntValue(index)) {
            //We are using INPUT_SLOTS_SIZE instead of getMaximum(), as getMaximum() will include the output slots as well
            if (!this.moveItemStackTo(item, (int) getOwnerRange().getMinimum(), INPUT_SLOTS_SIZE, false)) {
                //Return the itemStack to delete
                return ItemStack.EMPTY;
            }
        }

        //Return the itemStack to delete
        return stack;
    }


    public int getCraftingData() {
        return this.dataAccess().get(ThreshingTableBlockEntity.DATA_ACCESS_CRAFTING);
    }


    public int getEnergyStored() {
        return this.dataAccess().get(ThreshingTableBlockEntity.DATA_ACCESS_ENERGY);
    }
}
