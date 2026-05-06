package net.max_rhino.super_max_maker.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.max_rhino.super_max_maker.SuperMaxMakerPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NeoarcSpriteLoader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static NeoarcSpriteInfo load(String file) throws URISyntaxException, IOException {
        return load(SuperMaxMakerPaths.getPath(file));
    }

    public static NeoarcSpriteInfo load(Path path) throws URISyntaxException, IOException {
        BufferedReader reader = Files.newBufferedReader(path);
        NeoarcSpriteInfo info = gson.fromJson(reader, NeoarcSpriteInfo.class);
        reader.close();
        return info;
    }
}
