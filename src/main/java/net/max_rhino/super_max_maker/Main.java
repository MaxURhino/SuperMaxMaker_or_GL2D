package net.max_rhino.super_max_maker;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class Main {
    static void main(String[] args) {
        new MainApp().run();
    }

    public static Path getPath(String file) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(Main.class.getResource(file)).toURI());
    }
}
