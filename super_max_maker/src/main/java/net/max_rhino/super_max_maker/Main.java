package net.max_rhino.super_max_maker;

import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger("SuperMaxMaker");

    public static final float SCALE = 3f;

    static void main(String[] args) {
        try {
            Path coconutPath = Path.of(Objects.requireNonNull(Main.class.getResource("/Resources/.supermaxmakerassetsroot")).toURI())
                    .getParent()
                    .resolve("coconut.jpg");
            if (Arrays.stream(args).toList().contains("--bypass-coconut") || Files.exists(coconutPath)) {
                new MainApp().run();
            } else {
                System.exit(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer posToTile(int x, int y) {
        return (y * 16) + x;
    }

    public static Integer posToTile(Vector2i pos) {
        return posToTile(pos.x, pos.y);
    }

    public static class Vector2iUtils {
        public static Vector2i vec(int x, int y) {
            return new Vector2i(x, y);
        }
    }

    public static int modulo(int integer, int modulo) {
        int newInteger = integer % modulo;
        if (integer < -modulo) {
            newInteger += ((newInteger / modulo) * modulo);
        }
        return newInteger;
    }
}
