package net.max_rhino.gl2d.engine;

import net.max_rhino.gl2d.GL2D;
import org.lwjgl.glfw.GLFW;

public final class Input {
    private static long window;

    public static void init(long windowHandle) {
        window = windowHandle;
    }

    public static boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

    public static boolean isOneOfTheKeysDown(int... keys) {
        for (int key : keys) {
            if (isKeyDown(key)) {
                return true;
            }
        }
        return false;
    }

    public static int getAxisOfOneOfTheKeys(int[] left, int[] right) {
        return GL2D.booleanToInt(isOneOfTheKeysDown(right)) - GL2D.booleanToInt(isOneOfTheKeysDown(left));
    }

    public static int getAxis(int left, int right) {
        return GL2D.booleanToInt(isKeyDown(right)) - GL2D.booleanToInt(isKeyDown(left));
    }

    public static boolean isMouseDown(int button) {
        return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS;
    }
}
