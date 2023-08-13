package uk.co.jcox.farmingri.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.common.setup.Registration;

public class LongWheatBlockUpper extends CropBlock {

    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;


    public LongWheatBlockUpper(Properties properties) {
        super(properties);
    }


    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }


    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return Registration.ITEM_LONG_WHEAT_SEEDS.get();
    }

    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> states ) {
        states.add(getAgeProperty());
    }

    @SuppressWarnings("deprecated")
    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {

        final BlockPos posBelow = pos.below();
        final BlockState blockBelow = level.getBlockState(posBelow);

        return blockBelow.is(getLowerCounterpart());
    }


    protected Block getLowerCounterpart() {
        return Registration.BLOCK_LONG_WHEAT.get();
    }
}
