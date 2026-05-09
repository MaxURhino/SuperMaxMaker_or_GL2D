package net.max_rhino.gl2d.engine.math;

import org.joml.Vector2i;

public record Placement(Vector2i pos, Vector2i areaSize) {
    public enum Placements {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public int getX(Placements placement) {
        int x = pos.x;
        return switch(placement) {
            case LEFT -> x;
            case MIDDLE -> (areaSize.x / 2) - (x / 2);
            case RIGHT -> (areaSize.x) - x;
        };
    }

    public int getY(Placements placement) {
        int y = pos.y;
        return switch(placement) {
            case LEFT -> y;
            case MIDDLE -> (areaSize.y / 2) - (y / 2);
            case RIGHT -> (areaSize.y) - y;
        };
    }
}
