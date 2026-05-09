package net.max_rhino.gl2d.engine.math;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public record Recti(Vector2i pos, Vector2i size) {
    public Recti(int x, int y, int w, int h) {
        this(new Vector2i(x, y), new Vector2i(w, h));
    }

    @NotNull
    @Override
    public String toString() {
        return "[Pos=" + pos + ",Size=" + size + "]";
    }
}
