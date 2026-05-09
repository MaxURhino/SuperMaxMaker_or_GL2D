package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.GL2D;
import net.max_rhino.gl2d.engine.DrawableDisposable;
import net.max_rhino.gl2d.engine.Sprite;
import net.max_rhino.gl2d.engine.Texture;
import net.max_rhino.gl2d.engine.math.Recti;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class Tiles implements DrawableDisposable {
    private Sprite tile;
    private Themes theme;

    public enum TileType {
        TILE,
        BACKGROUND,
        MAP_OBJECT;

        @Override
        public String toString() {
            return GL2D.toPascalCase(super.toString().toLowerCase());
        }
    }

    public Tiles(Themes theme) {
        this.theme = theme;
        this.tile = new Sprite(
                new Texture(SuperMaxMakerPaths.image("Tile" + theme)),
                0, 0
        );
    }

    public Themes getTheme() {
        return theme;
    }

    public Tiles setTheme(Themes theme) {
        this.theme = theme;
        this.tile.setTexture(
                new Texture(SuperMaxMakerPaths.image("Tile" + theme))
        );
        return this;
    }

    @Override
    public void dispose() {
        this.tile.dispose();
    }

    @Override
    public Tiles render(double dt) {
        for (int h = 0; h < 3; h++) {
            TileType currentType = switch (h) {
                case 1 -> TileType.BACKGROUND;
                case 2 -> TileType.MAP_OBJECT;
                default -> TileType.TILE;
            };
            int tileIndex = 0;
            int tileX = 16;
            for (int i = 0; i < MainApp.CLONE_COUNT_X; i++) {
                int tileY = 8;
                for (int j = 0; j < MainApp.CLONE_COUNT_Y; j++) {
                    int oldTileIndex = tileIndex;
                    if (Math.abs((tileX * 48) - MainApp.CAMERA.pos.x) > (MainApp.CLONE_COUNT_X * 24)) {
                        if ((tileX * 48) < MainApp.CAMERA.pos.x) {
                            tileX += MainApp.CLONE_COUNT_X;
                            tileIndex += MainApp.CLONE_COUNT_X * MainApp.GRID_HEIGHT;
                        } else {
                            tileX -= MainApp.CLONE_COUNT_X;
                            tileIndex -= MainApp.CLONE_COUNT_X * MainApp.GRID_HEIGHT;
                        }
                    }
                    if (currentType == TileType.TILE) {
                        int tile = Main.posToTile(MainApp.TILE_MAP.get(Math.floorMod(tileIndex, MainApp.TILE_MAP.size())).pos);
                        this.tile.setRegion(
                                new Recti(
                                        (tile % 16) * 16, (tile / 16) * 16,
                                        16, 16
                                )
                        ).setScale(Main.SCALE).setPosition(
                                new Vector2f(
                                        (tileX * 48) - MainApp.CAMERA.pos.x,
                                        (tileY * 48) - MainApp.CAMERA.pos.y
                                )
                        );
                        if (tile != 0) {
                            this.tile.render(dt);
                        }
                    }
                    tileIndex = oldTileIndex;
                    tileY += 1;
                    tileIndex += 1;
                }
                tileX += 1;
                tileIndex += MainApp.GRID_HEIGHT - MainApp.CLONE_COUNT_Y;
            }
        }
        return this;
    }

    public static class Tile {
        private TileType type;
        private Vector2i pos;

        public Tile() {}

        public TileType type() {
            return type;
        }

        public Vector2i pos() {
            return pos;
        }

        public Tile type(TileType type) {
            this.type = type;
            return this;
        }

        public Tile pos(Vector2i pos) {
            this.pos = pos;
            return this;
        }

        public Tile pos(int x, int y) {
            return pos(new Vector2i(x, y));
        }

        public static Tile Default() {
            return new Tile().type(TileType.TILE).pos(new Vector2i(0, 0));
        }
    }
}
