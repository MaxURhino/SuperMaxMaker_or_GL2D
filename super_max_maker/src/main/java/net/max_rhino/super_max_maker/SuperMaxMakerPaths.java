package net.max_rhino.super_max_maker;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class SuperMaxMakerPaths {

    public static Path getPath(String file) {
        try {
            Path resourcesPath = Path.of(Objects.requireNonNull(SuperMaxMakerPaths.class.getResource("/Resources/.supermaxmakerassetsroot")).toURI()).toAbsolutePath().getParent().getParent();
            return resourcesPath.resolve(file);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path image(String file, boolean isImage) {
        if (!file.startsWith("/")) {
            file = "/" + file;
        }
        if (isImage) {
            file += ".png";
        }
        return getPath("Resources/Sprite" + file);
    }

    public static Path image(String file) {
        return image(file, true);
    }

    public static Path sound(String file) {
        if (!file.startsWith("/")) {
            file = "/" + file;
        }
        return getPath("Resources/Sound" + file);
    }
}
