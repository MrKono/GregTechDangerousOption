package kono.ceu.gtdo.api.unification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public class GTDOMaterialFlags {

    public static void init() {
        List<Material> material = new ArrayList<>(Arrays.asList(Materials.Lithium, Materials.Sodium,
                Materials.Potassium, Materials.Rubidium, Materials.Caesium, Materials.Francium));
        for (Material mat : material) {
            if (!mat.hasProperty(PropertyKey.INGOT)) {
                mat.setProperty(PropertyKey.INGOT, new IngotProperty());
            }
        }
    }
}
