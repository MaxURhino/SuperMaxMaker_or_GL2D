package net.max_rhino.gl2d.engine.shape;

import net.max_rhino.gl2d.engine.Disposable;
import net.max_rhino.gl2d.engine.Drawable;
import net.max_rhino.gl2d.engine.Transformable;
import org.joml.Vector2f;

import java.awt.*;

public abstract class Shape implements Drawable, Transformable, Disposable {
    protected Vector2f position = new Vector2f();
    protected Vector2f scale = new Vector2f(1);
    protected Color color = Color.WHITE;

    public Shape(float x, float y) {
        position.set(x, y);
    }

    public Color getColor() {
        return color;
    }

    public Shape setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public Shape setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    @Override
    public Vector2f getScale() {
        return scale;
    }

    @Override
    public Shape setScale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public Shape setScale(float scale) {
        this.scale = new Vector2f(scale);
        return this;
    }

    @Override
    public void dispose() {

    }
}
