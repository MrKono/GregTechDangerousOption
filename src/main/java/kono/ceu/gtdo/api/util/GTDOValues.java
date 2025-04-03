package kono.ceu.gtdo.api.util;

import static kono.ceu.gtdo.api.util.GTDOUtility.parseIntOrDefault;

import java.util.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    public static boolean temperatureDamage = GTDOConfig.features.temperatureDamage;
    public static boolean heatDamage = GTDOConfig.temperature.damageHot;
    public static boolean frostDamage = GTDOConfig.temperature.damageFrost;
    public static boolean tempDamageOnlyHold = GTDOConfig.temperature.damageOnlyHold;
    public static Set<Item> fluidContainer = new HashSet<>();
    static {
        for (String str : GTDOConfig.temperature.listFluidContainer) {
            String[] parts = str.split(":");
            if (parts.length < 2 || parts.length > 3) {
                GTDOLog.logger.error("Invalid entry: {}", str);
                continue;
            }
            String id = parts[0];
            String name = parts.length == 2 ? parts[1].trim() : parts[1];
            int meta = (parts.length == 3) ? parseIntOrDefault(parts[2].trim(), 0) : 0;

            ItemStack stack = GameRegistry.makeItemStack(id + ":" + name, meta, 1, null);
            if (stack != null && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                fluidContainer.add(stack.getItem());
            } else {
                GTDOLog.logger.error("Failed to create ItemStack for: {}", str);
            }
        }
    }

    public static @NotNull ResourceLocation gtdoId(String path) {
        return new ResourceLocation(modId, path);
    }
}
