package kono.ceu.gtdo.api.unification.material.properties;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;

public class GTDOExplosionProperty implements IMaterialProperty {

    private final boolean explodeWhenWet;
    private final float power;

    public GTDOExplosionProperty(boolean explodeWhenWet, float power) {
        this.explodeWhenWet = explodeWhenWet;
        this.power = power;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {}

    public boolean isExplodeWhenWet() {
        return this.explodeWhenWet;
    }

    public float getPower() {
        return this.power;
    }
}
