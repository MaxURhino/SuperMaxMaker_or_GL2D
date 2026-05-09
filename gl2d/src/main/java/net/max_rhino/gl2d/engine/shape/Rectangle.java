package net.max_rhino.gl2d.engine.shape;

import org.lwjgl.opengl.GL11;

public class Rectangle extends Shape {
    private final float width;
    private final float height;

    public Rectangle(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle render(double dt) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor4f(
                this.color.getRed()    / 255f,
                this.color.getGreen() / 255f,
                this.color.getBlue()  / 255f,
                this.color.getAlpha() / 255f
        );
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + (width * scale.x), position.y);
        GL11.glVertex2f(position.x + (width * scale.x), position.y + (height * scale.y));
        GL11.glVertex2f(position.x, position.y + (height * scale.y));

        GL11.glEnd();

        GL11.glColor3f(1, 1, 1);

        return this;
    }
}
