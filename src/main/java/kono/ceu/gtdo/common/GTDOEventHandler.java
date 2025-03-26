package kono.ceu.gtdo.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;

import kono.ceu.gtdo.Tags;
import kono.ceu.gtdo.api.unification.GTDOMaterialFlags;
import kono.ceu.gtdo.api.unification.material.properties.GTDOMaterialPropertyAddition;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class GTDOEventHandler {

    public GTDOEventHandler() {}

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterialsHigh(MaterialEvent event) {
        GTDOMaterialFlags.init();
        GTDOMaterialPropertyAddition.init();
    }

    @SubscribeEvent
    public static void registerMaterialsPost(PostMaterialEvent event) {}
}
