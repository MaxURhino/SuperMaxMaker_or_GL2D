package net.max_rhino.gl2d;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.function.Supplier;

public class GL2D {
    public static int booleanToInt(boolean bool) {
        if (bool) return 1;
        else return 0;
    }

    public static String toPascalCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (String part : text.trim().split("[\\s_\\-]+")) {
            if (part.isEmpty()) {
                continue;
            }

            builder.append(Character.toUpperCase(part.charAt(0)));

            if (part.length() > 1) {
                builder.append(part.substring(1).toLowerCase());
            }
        }

        return builder.toString();
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static class MethodUnsupportedException extends RuntimeException {
        public MethodUnsupportedException(String message) {
            super(message);
        }
    }

    public static class Vectors {
        public static class Conversion {
            public static Vector2i vector2fToVector2i(Vector2f vector2f) {
                return new Vector2i((int) vector2f.x, (int) vector2f.y);
            }

            public static Vector2f vector2iToVector2f(Vector2i vector2f) {
                return new Vector2f((float) vector2f.x, (float) vector2f.y);
            }
        }

        public static class Math {
            public static Vector2f multiplyVector2fWithVector2i(Vector2f vector2f, Vector2i vector2i) {
                return vector2f.mul(Conversion.vector2iToVector2f(vector2i));
            }
        }
    }
}
