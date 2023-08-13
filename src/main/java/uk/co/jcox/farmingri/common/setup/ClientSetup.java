package uk.co.jcox.farmingri.common.setup;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import uk.co.jcox.farmingri.client.ThreshingTableScreen;
import uk.co.jcox.farmingri.client.WoodPowerGenScreen;

public class ClientSetup {

    private static final Logger logger = LogUtils.getLogger();

    private ClientSetup() {

    }


    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            logger.info("Starting Client Setup");
            MenuScreens.register(Registration.CONTAINER_THRESHING_TABLE.get(), ThreshingTableScreen::new);
            MenuScreens.register(Registration.CONTAINER_WOOD_POWER_GEN.get(), WoodPowerGenScreen::new);
        });
    }
}
