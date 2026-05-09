package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.engine.Application;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.max_rhino.super_max_maker.Tiles.Tile;

public class MainApp extends Application {

    public static List<Tile> TILE_MAP = new ArrayList<>();
    public static int GRID_WIDTH = 0;
    public static int GRID_HEIGHT = 0;

    public static int CLONE_COUNT_X = 16;
    public static int CLONE_COUNT_Y = 15;

    public static Camera CAMERA = new Camera();

    public MainApp() {
        super(1280, 720, "Super Max Maker");
        new MapGenerator().generateMap();
    }

    @Override
    protected void onCreate() {
        this.setClearColor(new Color(0x7B6BFF));

        // Tiles
        Tiles tiles = new Tiles(Themes.OVERWORLD);
        this.addDrawable(tiles);

        // Mario
        Mario mario = Mario.getInstance();
        this.addDrawable(mario);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(double dt) {
        super.render(dt, true);
    }
}
