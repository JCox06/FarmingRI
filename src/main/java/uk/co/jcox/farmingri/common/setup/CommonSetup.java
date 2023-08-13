package uk.co.jcox.farmingri.common.setup;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;

public class CommonSetup {


    private CommonSetup() {}


    private static final Logger logger = LogUtils.getLogger();

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent commonSetupEvent) {
        logger.info("Starting FRI Common Setup");
        commonSetupEvent.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(Registration.ITEM_LONG_WHEAT_SEEDS.get(), 0.2f);
            ComposterBlock.COMPOSTABLES.put(Registration.ITEM_LONG_WHEAT_SHEAF.get(), 0.7f);
            ThreshingTableBlockEntity.bootStrap();
        });
    }
}
