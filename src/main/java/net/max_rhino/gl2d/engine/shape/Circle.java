package net.max_rhino.gl2d.engine.shape;

import org.lwjgl.opengl.GL11;

public class Circle extends Shape {
    private final float radius;
    private final int segments;

    public Circle(float x, float y, float radius, int segments) {
        super(x, y);
        this.radius = radius;
        this.segments = segments;
    }

    @Override
    public Circle render(double dt) {
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glVertex2f(position.x, position.y);

        for (int i = 0; i <= segments; i++) {
            double angle = (Math.PI * 2.0 * i) / segments;
            float px = position.x + (((float) Math.cos(angle) * radius) * scale.x);
            float py = position.y + (((float) Math.sin(angle) * radius) * scale.y);
            GL11.glVertex2f(px, py);
        }

        GL11.glEnd();

        return this;
    }
}
