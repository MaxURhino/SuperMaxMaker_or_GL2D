package net.max_rhino.gl2d.engine.shape;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class Triangle extends Shape {
    private final Vector2f p1;
    private final Vector2f p2;
    private final Vector2f p3;

    public Triangle(Vector2f p1, Vector2f p2, Vector2f p3) {
        super(0, 0);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public Triangle render(double dt) {
        GL11.glBegin(GL11.GL_TRIANGLES);

        GL11.glColor4f(this.color.getRed() / 255f, this.color.getGreen() / 255f, this.color.getBlue() / 255f, this.color.getAlpha() / 255f);
        GL11.glVertex2f(p1.x * scale.x, p1.y * scale.y);
        GL11.glVertex2f(p2.x * scale.x, p2.y * scale.y);
        GL11.glVertex2f(p3.x * scale.x, p3.y * scale.y);

        GL11.glEnd();

        GL11.glColor3f(1, 1, 1);

        return this;
    }
}
