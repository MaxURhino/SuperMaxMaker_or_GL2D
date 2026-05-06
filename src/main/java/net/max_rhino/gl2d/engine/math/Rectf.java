package net.max_rhino.gl2d.engine.math;

import org.joml.Vector2f;

public record Rectf(Vector2f pos, Vector2f size) {
    public Rectf(float x, float y, float w, float h) {
        this(new Vector2f(x, y), new Vector2f(w, h));
    }
}
