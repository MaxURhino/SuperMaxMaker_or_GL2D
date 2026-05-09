package net.max_rhino.gl2d.engine;

import org.joml.Vector2f;

public interface Transformable {
    Vector2f getPosition();
    Transformable setPosition(Vector2f position);
    default Transformable setX(float x) {
        setPosition(new Vector2f(x, getPosition().y));
        return this;
    }
    default Transformable setY(float y) {
        setPosition(new Vector2f(getPosition().x, y));
        return this;
    }
    default Transformable addX(float x) {
        return setX(x + getPosition().x);
    }
    default Transformable addY(float y) {
        return setY(y + getPosition().y);
    }

    Vector2f getScale();
    Transformable setScale(Vector2f scale);
    Transformable setScale(float scale);
}
