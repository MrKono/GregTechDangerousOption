package kono.ceu.gtdo.api.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import kono.ceu.gtdo.Tags;

public class GTDOValues {

    public static final String modId = Tags.MODID;
    public static final String modName = Tags.MODNAME;

    public static Map<Material, Float> explodeMaterialMap = new HashMap<>();

    static {
        explodeMaterialMap.put(Materials.Lithium, 2.0F);
        explodeMaterialMap.put(Materials.Sodium, 4.0F);
        explodeMaterialMap.put(Materials.Potassium, 6.0F);
        explodeMaterialMap.put(Materials.Rubidium, 8.0F);
        explodeMaterialMap.put(Materials.Caesium, 10.0F);
        explodeMaterialMap.put(Materials.Francium, 20.0F);
    }

    public static final Map<OrePrefix, Float> explodePrefixMap = new HashMap<>();

    static {
        explodePrefixMap.put(OrePrefix.block, 2.0F);
        explodePrefixMap.put(OrePrefix.ingot, 1.0F);
        explodePrefixMap.put(OrePrefix.dust, 1.0F);
        explodePrefixMap.put(OrePrefix.dustSmall, 0.25F);
        explodePrefixMap.put(OrePrefix.nugget, 0.1F);
        explodePrefixMap.put(OrePrefix.dustTiny, 0.1F);
    }

    public static @NotNull ResourceLocation gtdoId(String path) {
        return new ResourceLocation(modId, path);
    }
}
