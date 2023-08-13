package uk.co.jcox.farmingri.common.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import uk.co.jcox.farmingri.common.setup.Registration;

public class TallFescueBlock extends LongWheatBlock{

    public TallFescueBlock(Properties blockBehaviour) {
        super(blockBehaviour);
    }

    @Override
    protected Block getDoubleCounterpart() {
        return Registration.BLOCK_TALL_FESCUE_UPPER.get();
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return Registration.ITEM_TALL_FESCUE_SEEDS.get();
    }
}
