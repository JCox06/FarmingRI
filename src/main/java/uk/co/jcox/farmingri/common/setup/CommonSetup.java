package uk.co.jcox.farmingri.common.setup;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;

public class CommonSetup {


    private CommonSetup() {}

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent commonSetupEvent) {
        FarmingRI.LOGGER.info("Starting COMMON setup");
        commonSetupEvent.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(Registration.ITEM_LONG_WHEAT_SEEDS.get(), 0.2f);
            ComposterBlock.COMPOSTABLES.put(Registration.ITEM_LONG_WHEAT_SHEAF.get(), 0.7f);
            ThreshingTableBlockEntity.bootStrap();
        });
    }
}
