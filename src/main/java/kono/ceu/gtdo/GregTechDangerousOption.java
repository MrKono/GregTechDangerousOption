package kono.ceu.gtdo;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.GTInternalTags;

import kono.ceu.gtdo.api.util.GTDOValues;
import kono.ceu.gtdo.common.CommonProxy;

@Mod(modid = GTDOValues.modId,
     name = GTDOValues.modName,
     acceptedMinecraftVersions = "[1.12, 1.12.2]",
     version = Tags.VERSION,
     dependencies = GTInternalTags.DEP_VERSION_STRING + "required-after:mixinbooter;")
@Mod.EventBusSubscriber(modid = GTDOValues.modId)
public class GregTechDangerousOption {

    @SidedProxy(modId = GTDOValues.modId,
                clientSide = "kono.ceu.gtdo.client.ClientProxy",
                serverSide = "kono.ceu.gtdo.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GregTechDangerousOption instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MODID)) {
            ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
        }
    }
}
