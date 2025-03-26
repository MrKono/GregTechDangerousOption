package kono.ceu.gtdo.api.unification.material.properties;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;

import kono.ceu.gtdo.GTDOConfig;
import kono.ceu.gtdo.api.util.GTDOLog;

public class GTDOMaterialPropertyAddition {

    public static void init() {
        explosionWhenWet();
    }

    public static void explosionWhenWet() {
        if (!GTDOConfig.features.explodeWhenWet) return;
        for (String str : GTDOConfig.explosion.wetExplosives) {
            if (str.isEmpty()) return;
            String[] data = str.split(":");
            if (data.length == 2) {
                String name = data[0].trim();
                String floatValue = data[1].trim();
                try {
                    Material material = GregTechAPI.materialManager.getMaterial(name);
                    if (material != null) {
                        float value = Float.parseFloat(floatValue);

                        material.setProperty(GTDOPropertyKey.EXPLOSION, new GTDOExplosionProperty(
                                true, value));
                    } else {
                        GTDOLog.logger.error(name + "was null!! Skipped!!");
                    }
                } catch (NumberFormatException e) {
                    GTDOLog.logger.error("Numeric conversion was failed: " + floatValue);
                }
            }
        }
    }
}
