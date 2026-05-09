package net.max_rhino.super_max_maker;

import static net.max_rhino.super_max_maker.MainApp.*;

public class MapGenerator {
    public MapGenerator() {}

    private void addWallColumn() {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            TILE_MAP.add(
                    Tiles.Tile.Default().pos(6, 0)
            );
        }
    }

    private void addBoxedColumn() {
        TILE_MAP.add(
                Tiles.Tile.Default().pos(6, 0)
        );
        for (int i = 0; i < GRID_HEIGHT - 2; i++) {
            TILE_MAP.add(
                    Tiles.Tile.Default().pos(0, 0)
            );
        }
        TILE_MAP.add(
                Tiles.Tile.Default().pos(3, 15)
        );
    }

    private void generateLevel() {
        TILE_MAP.clear();
        addWallColumn();
        for (int i = 0; i < GRID_WIDTH - 2; i++) {
            addBoxedColumn();
        }
        addWallColumn();
    }

    public void generateMap() {
        TILE_MAP.clear();
        if (TILE_MAP.isEmpty()) {
            GRID_WIDTH = 100;
            GRID_HEIGHT = 40;
            generateLevel();
        }
    }
}
