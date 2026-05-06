package net.max_rhino.gl2d.engine;

import net.max_rhino.gl2d.engine.shape.Rectangle;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Application {
    protected long window;
    protected int width;
    protected int height;
    protected String title;
    private Color clearColor = Color.BLACK;

    private long lastTime;
    private double deltaTime;

    private List<Drawable> drawables = new ArrayList<>();

    public Application(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void run() {
        init();
        loop();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }

        long monitor = GLFW.glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);

        if (videoMode != null) {
            GLFW.glfwSetWindowPos(
                    window,
                    (videoMode.width() - width) / 2,
                    (videoMode.height() - height) / 2
            );
        }

        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            float targetAspect = 16f / 9f;

            float windowAspect = (float) width / height;

            int vpX = 0, vpY = 0, vpW = width, vpH = height;

            if (windowAspect > targetAspect) {
                vpW = (int)(height * targetAspect);
                vpX = (width - vpW) / 2;
            } else {
                vpH = (int)(width / targetAspect);
                vpY = (height - vpH) / 2;
            }

            GL11.glViewport(vpX, vpY, vpW, vpH);
        });

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);

        GL.createCapabilities();

        GL11.glViewport(0, 0, width, height);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, -1, 1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        Input.init(window);

        onCreate();

        lastTime = System.nanoTime();
    }

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastTime) / 1_000_000_000f;
            lastTime = currentTime;

            update();
            render(deltaTime);

            GLFW.glfwSwapBuffers(window);
        }
    }

    protected void setClearColor(Color color) {
        this.clearColor = color;
        GL11.glClearColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    protected void clear(boolean useFillFunction) {
        if (useFillFunction) {
            new Rectangle(0, 0, this.width, this.height).setColor(this.clearColor).render(0);
        } else
        {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        }
    }

    protected void clear() {
        clear(false);
    }

    protected <T extends Drawable> void addDrawable(T drawable) {
        this.drawables.add(drawable);
    }

    protected abstract void onCreate();

    protected abstract void update();

    protected void render(double dt) {
        for (Drawable drawable : drawables) {
            drawable.render(dt);
        }
    }
}
