package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.engine.Application;
import net.max_rhino.gl2d.engine.Sprite;

public class MainApp extends Application {
    private Mario mario;

    public MainApp() {
        super(1280, 720, "Test App");
    }

    @Override
    protected void onCreate() {
        mario = Mario.getInstance();
        this.addDrawable(mario);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(double dt) {
        this.clear();
        super.render(dt);
    }
}
