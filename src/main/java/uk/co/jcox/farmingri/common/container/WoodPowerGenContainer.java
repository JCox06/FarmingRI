package uk.co.jcox.farmingri.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.common.block.entity.WoodPowerGenBlockEntity;
import uk.co.jcox.farmingri.common.setup.Registration;

public class WoodPowerGenContainer extends InvMenu {


    private WoodPowerGenContainer(int windowID, @NotNull Player player, @NotNull BlockPos pos, @Nullable ContainerData dataAccess) {
        super(Registration.CONTAINER_WOOD_POWER_GEN.get(), Registration.BLOCK_WOOD_POWER_GENERATOR.get(), windowID, player, pos, dataAccess);
    }

    public WoodPowerGenContainer(int windowID, @NotNull Player player, @NotNull BlockPos pos) {
        this(windowID, player, pos, null);
    }


    @Override
    protected int layoutEntitySlots(BlockEntity entity) {
        if ((entity instanceof WoodPowerGenBlockEntity powerGen)) {
            addSlot(new SlotItemHandler(powerGen.getInputItemHandler(), 0, 35, 38));
        }


        //We return 0 here as 0 was the last grouped index used
        return 0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int groupedIndex) {
        ItemStack stack = ItemStack.EMPTY;
        ItemStack clicked = getSlot(groupedIndex).getItem();
        if (! clicked.isEmpty()) {

            stack = clicked.copy();

            if (getOwnerRange().isValidIntValue(groupedIndex)) {
                if (! moveItemStackTo(clicked, (int) getPlayerRange().getMinimum(), (int) getPlayerRange().getMaximum(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (getPlayerRange().isValidIntValue(groupedIndex)) {
                //Have to plus 1 onto the max range value as the range is left closed right open type
                if (! moveItemStackTo(clicked, (int) getOwnerRange().getMinimum(), (int) getOwnerRange().getMaximum() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }
}
