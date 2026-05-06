package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.engine.Application;

import java.awt.*;

public class MainApp extends Application {
    private Mario mario;

    public MainApp() {
        super(1280, 720, "Test App");
    }

    @Override
    protected void onCreate() {
        this.setClearColor(new Color(0x7B6BFF));
        mario = Mario.getInstance();
        this.addDrawable(mario);
        Main.LOGGER.info("App has started!");
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(double dt) {
        super.render(dt, true);
    }
}
