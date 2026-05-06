package net.max_rhino.gl2d.engine;

import net.max_rhino.gl2d.engine.math.Recti;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Sprite implements Disposable, Transformable, Drawable {
    private Texture texture;
    private Vector2f position = new Vector2f();
    private Vector2f scale = new Vector2f(1);
    @Nullable
    private Recti region = null;
    private boolean flipX;
    private boolean flipY;

    public Sprite(Texture texture, float x, float y) {
        this.texture = texture;
        this.texture.bind();
        this.position.set(x, y);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.texture.bind();
    }

    public Vector2f getPosition() {
        return position;
    }

    public Sprite setRegion(@Nullable Recti region) {
        this.region = region;
        return this;
    }

    @Nullable
    public Recti getRegion() {
        return region;
    }

    @Override
    public Sprite setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    @Override
    public Vector2f getScale() {
        return this.scale;
    }

    @Override
    public Sprite setScale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public Sprite setScale(float scale) {
        return setScale(new Vector2f(scale));
    }

    public boolean isFlipY() {
        return flipY;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    @Override
    public Sprite render(double dt) {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        texture.bind();

        glBegin(GL_QUADS);

        float u0 = 0f;
        float v0 = 0f;
        float u1 = 1f;
        float v1 = 1f;

        int width = texture.width();
        int height = texture.height();

        if (region != null) {
            float texW = texture.width();
            float texH = texture.height();

            float x = region.pos().x;
            float y = region.pos().y;
            float w = region.size().x;
            float h = region.size().y - 1;

            u0 = x / texW;
            v0 = y / texH;
            u1 = (x + w) / texW;
            v1 = (y + h) / texH;

            width = region.size().x;
            height = region.size().y - 1;
        }

        float x = position.x;
        float y = position.y;

        float w = width * scale.x;
        float h = height * scale.y;

        if (isFlipX()) {
            float tempU0 = u0;

            u0 = u1;
            u1 = tempU0;
        }

        glTexCoord2f(u0, v0);
        glVertex2f(x, y);

        glTexCoord2f(u1, v0);
        glVertex2f(x + w, y);

        glTexCoord2f(u1, v1);
        glVertex2f(x + w, y + h);

        glTexCoord2f(u0, v1);
        glVertex2f(x, y + h);

        glEnd();

        texture.unbind();
        glDisable(GL_TEXTURE_2D);

        return this;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
