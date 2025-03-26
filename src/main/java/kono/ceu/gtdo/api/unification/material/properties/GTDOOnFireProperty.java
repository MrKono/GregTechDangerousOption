package kono.ceu.gtdo.api.unification.material.properties;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;

public class GTDOOnFireProperty implements IMaterialProperty {

    private final boolean fireWhenWet;
    private final boolean fireWhenHeld;

    public GTDOOnFireProperty(boolean fireWhenWet, boolean fireWhenHeld) {
        this.fireWhenWet = fireWhenWet;
        this.fireWhenHeld = fireWhenHeld;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {}

    private boolean isFireWhenWet() {
        return this.fireWhenWet;
    }

    private boolean isFireWhenHeld() {
        return this.fireWhenHeld;
    }
}
