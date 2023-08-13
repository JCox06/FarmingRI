package uk.co.jcox.farmingri;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.co.jcox.farmingri.common.setup.Registration;

@Mod(FarmingRI.MODID)
public class FarmingRI {

    public static final String MODID = "farmingri";

    public FarmingRI() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        //Registration
        Registration.registerAll(modEventBus);
    }
}