package kono.ceu.gtdo.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;

import kono.ceu.gtdo.Tags;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class GTDOEventHandler {

    public GTDOEventHandler() {}

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterialsHigh(MaterialEvent event) {}

    @SubscribeEvent
    public static void registerMaterialsPost(PostMaterialEvent event) {}
}
