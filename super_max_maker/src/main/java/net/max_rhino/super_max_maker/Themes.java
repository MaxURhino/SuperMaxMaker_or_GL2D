package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.GL2D;

public enum Themes {
    OVERWORLD,
    GHOST_HOUSE;

    @Override
    public String toString() {
        return GL2D.toPascalCase(this.name().toLowerCase());
    }
}
