package uk.co.jcox.farmingri.common.block;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.common.setup.Registration;


public class LongWheatBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final int MAX_AGE = 5;


    public LongWheatBlock(Properties blockBehaviour) {
        super(blockBehaviour);
    }


    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(getAgeProperty());
    }


    @SuppressWarnings("deprecated")
    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Entity entity) {
        entity.makeStuckInBlock(state, new Vec3(0.9f, 0.9f, 0.9f));
    }


    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.is(this)|| state.getValue(AGE) != MAX_AGE;
    }

    @SuppressWarnings("deprecated")
    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel serverLevel, @NotNull BlockPos pos, @NotNull RandomSource randomSource) {
        super.randomTick(state, serverLevel, pos, randomSource);

        int currentAge = state.getValue(getAgeProperty());

        if (currentAge != MAX_AGE) {
            return;
        }

        if (! serverLevel.getBlockState(pos.above()).isAir()) {
            return;
        }

        serverLevel.setBlockAndUpdate(pos.above(), getDoubleCounterpart().defaultBlockState());
    }

    protected Block getDoubleCounterpart() {
        return Registration.BLOCK_LONG_WHEAT_UPPER.get();
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return Registration.ITEM_LONG_WHEAT_SEEDS.get();
    }

}