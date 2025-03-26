package kono.ceu.gtdo.api.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;

import kono.ceu.gtdo.GTDOConfig;
import kono.ceu.gtdo.Tags;
import kono.ceu.gtdo.api.unification.material.properties.GTDOPropertyKey;

public class GTDOValues {

    public static final String modId = Tags.MODID;
    public static final String modName = Tags.MODNAME;

    public static boolean explodeWhenWet = GTDOConfig.features.explodeWhenWet;

    public static Map<Material, Float> explodeMaterialMap = new HashMap<>();

    static {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            if (material.hasProperty(GTDOPropertyKey.EXPLOSION)) {
                float power = material.getProperties().getProperty(GTDOPropertyKey.EXPLOSION).getPower();
                explodeMaterialMap.put(material, power);
            }
        }
    }

    public static @NotNull ResourceLocation gtdoId(String path) {
        return new ResourceLocation(modId, path);
    }
}
