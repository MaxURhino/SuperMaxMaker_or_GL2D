package net.max_rhino.super_max_maker;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class SuperMaxMakerPaths {

    public static Path getPath(String file) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(Main.class.getResource(file)).toURI());
    }

    public static Path image(String file, boolean isImage) throws URISyntaxException {
        if (!file.startsWith("/")) {
            file = "/" + file;
        }
        if (isImage) {
            file += ".png";
        }
        return getPath("/assets/images" + file);
    }

    public static Path image(String file) throws URISyntaxException {
        return image(file, true);
    }
}
