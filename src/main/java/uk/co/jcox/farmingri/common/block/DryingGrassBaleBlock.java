package uk.co.jcox.farmingri.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.List;

public class DryingGrassBaleBlock extends Block {

    public static final String TOOLTIP = "tooltip." + FarmingRI.MODID + ".grass_bale";

    public DryingGrassBaleBlock(Properties pProperties) {
        super(pProperties);
    }


    @SuppressWarnings("deprecated")
    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource random) {
        if (canDry(pLevel, pPos)) {
            //Get a random chance of 1%
            if (random.nextInt(100) == 0) {
                setStateDry(pLevel, pPos);
            }
        }
    }


    //Level#isRainingAt() was confusing at first, and I actually spent two days
    //trying to figure out why this wasn't working, but actually it's quite simple:
    //MAKE SURE TO CHECK THE BLOCK ABOVE THE BLOCK YOU ACTUALLY WANT
    //I'm assuming this has something to do with the light level
    //as to check if the block is the most hightest level block, level will check if the block has max light level (15)
    private boolean canDry(Level level, BlockPos pos) {
        if (level.isRainingAt(pos.above())) {
            return false;
        }
        return true;
    }


    private void setStateDry(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Registration.BLOCK_HAY_BLOCK.get().defaultBlockState());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable(TOOLTIP));
    }


}
