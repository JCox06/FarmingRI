package uk.co.jcox.farmingri.common.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.common.setup.Registration;

public class TallFescueBlockUpper extends LongWheatBlockUpper{

    public TallFescueBlockUpper(Properties properties) {
        super(properties);
    }


    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return Registration.ITEM_TALL_FESCUE_SEEDS.get();
    }

    @Override
    protected Block getLowerCounterpart() {
        return Registration.BLOCK_TALL_FESCUE.get();
    }
}
