package net.max_rhino.super_max_maker;

public class Mario extends Player {
    public Mario() {
        super("mario", Controls.defaultInstance());
    }

    public static Mario getInstance() {
        return new Mario();
    }
}
