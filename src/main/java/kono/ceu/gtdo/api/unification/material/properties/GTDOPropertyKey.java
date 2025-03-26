package kono.ceu.gtdo.api.unification.material.properties;

import gregtech.api.unification.material.properties.PropertyKey;

public class GTDOPropertyKey {

    public static final PropertyKey<GTDOExplosionProperty> EXPLOSION = new PropertyKey<>("explode",
            GTDOExplosionProperty.class);
    public static final PropertyKey<GTDOOnFireProperty> ON_FIRE = new PropertyKey<>("on_fire",
            GTDOOnFireProperty.class);
}
