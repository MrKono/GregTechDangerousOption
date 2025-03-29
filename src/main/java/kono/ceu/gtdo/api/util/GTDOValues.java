package kono.ceu.gtdo.api.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

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

    public static Map<OrePrefix, Float> explodePrefixMap = new HashMap<>();
    static {
        for (String str : GTDOConfig.explosion.wetExplosivesPrefix) {
            if (!str.isEmpty()) {
                String[] data = str.split(":");
                if (data.length == 2) {
                    String name = data[0].trim();
                    String floatValue = data[1].trim();
                    try {
                        OrePrefix prefix = OrePrefix.getPrefix(name);
                        if (prefix != null) {
                            float value = Float.parseFloat(floatValue);
                            explodePrefixMap.put(prefix, value);
                        } else {
                            GTDOLog.logger.error("OrePrefix \"{}\" was null!! Skipped!!", name);
                        }
                    } catch (NumberFormatException e) {
                        GTDOLog.logger.error("Numeric conversion was failed: {}", floatValue);
                    }
                }
            }
        }
    }

    public static @NotNull ResourceLocation gtdoId(String path) {
        return new ResourceLocation(modId, path);
    }
}
