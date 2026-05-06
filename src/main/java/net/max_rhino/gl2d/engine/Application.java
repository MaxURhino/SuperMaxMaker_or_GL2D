package net.max_rhino.gl2d.engine;

import net.max_rhino.gl2d.engine.math.Recti;
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

    protected int definedWidth;
    protected int definedHeight;

    private Recti vp;

    private List<DrawableDisposable> drawables = new ArrayList<>();

    public Application(int width, int height, String title) {
        this.width  = width;  this.definedWidth  = width;
        this.height = height; this.definedHeight = height;
        this.title = title;
    }

    public void run() {
        init();
        loop();
        for (Disposable drawable : drawables) {
            drawable.dispose();
        }
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        vp = new Recti(0, 0, width, height);

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

        GLFW.glfwSetFramebufferSizeCallback(window, (_, width, height) -> {
            this.width = width;
            this.height = height;

            float targetAspect = (float)(this.definedWidth) / (float)(this.definedHeight);
            float windowAspect = (float) width / height;

            vp = new Recti(0, 0, width, height);

            if (windowAspect > targetAspect) {
                vp.size().x = (int) (height * targetAspect);
                vp.pos().x = (width - vp.size().x) / 2;
            } else {
                vp.size().y = (int) (width / targetAspect);
                vp.pos().y = (height - vp.size().y) / 2;
            }

            GL11.glViewport(vp.pos().x, vp.pos().y, vp.size().x, vp.size().y);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, this.definedWidth, this.definedHeight, 0, -1, 1);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
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

    protected void clear() {
            Color temp = this.clearColor;

            setClearColor(Color.BLACK);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            setClearColor(temp);

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(vp.pos().x, vp.pos().y, vp.size().x, vp.size().y);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    protected <T extends DrawableDisposable> void addDrawable(T drawable) {
        this.drawables.add(drawable);
    }

    protected abstract void onCreate();

    protected abstract void update();

    protected void render(double dt) {
        render(dt, false);
    }

    protected void render(double dt, boolean clear) {
        if (clear) {
            this.clear();
        }
        for (Drawable drawable : drawables) {
            drawable.render(dt);
        }
    }
}
