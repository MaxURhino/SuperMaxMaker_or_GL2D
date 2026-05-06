package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.GL2D;
import net.max_rhino.gl2d.engine.*;
import net.max_rhino.gl2d.engine.math.Recti;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteInfo;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteLoader;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteUtil;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements DrawableDisposable {
    private Sprite sprite;
    private NeoarcSpriteInfo info;
    private float animFrame;
    private int intAnimFrame;
    private double frame;
    private Anim anim = Anim.IDLE;
    private PowerUp powerUp = PowerUp.SMALL;
    private PowerUp lastPowerUp = PowerUp.SMALL;
    private Vector2f lastVelocity = new Vector2f();

    private final String character;

    public static int KEY_WALK = 0;

    private final Movement movement = Movement.getInstance();

    private List<Recti> rectis;
    private List<Vector2i> vector2is;

    public static float ACCELERATION = 0.4f;
    public static float MAX_SPEED_X = 10f;

    public static class Movement {
        private Vector2f position = new Vector2f();
        private Vector2f velocity = new Vector2f();

        private float falling = 0f;

        // Getters

        public Vector2f getPosition() {
            return position;
        }

        public Vector2f getVelocity() {
            return velocity;
        }

        public float getFalling() {
            return falling;
        }

        // Setters

        public void setPosition(Vector2f position) {
            this.position = position;
        }

        public void setVelocity(Vector2f velocity) {
            this.velocity = velocity;
        }

        public void setFalling(float falling) {
            this.falling = falling;
        }

        public static Movement getInstance() {
            return new Movement();
        }
    }

    public enum PowerUp {
        SMALL,
        BIG,
        FIRE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Anim {
        IDLE,
        WALK,
        JUMP,
        JUMP_DOWN,
        SKID,
        CROUCH;

        @Override
        public String toString() {
            return GL2D.toPascalCase(this.name());
        }
    }

    public Player(String character) {
        this.character = character;
        try {
            this.info = NeoarcSpriteLoader.load(
                    SuperMaxMakerPaths.image("objects/characters/players/" + this.character + "/small.json", false)
            );
            this.sprite = new Sprite(
                    new Texture(
                            SuperMaxMakerPaths.image("objects/characters/players/" + this.character + "/small")
                    ),
                    0,
                    0
            ).setScale(3f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public void moveLeftRight(double dt) {
        if ((int)frame % 7 == 0) {
            this.anim = Anim.WALK;

            KEY_WALK = Input.getAxis(GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D);

            if (KEY_WALK == 0) {
                this.anim = Anim.IDLE;
                if (this.movement.falling < 2) {
                    if (this.movement.velocity.x > ACCELERATION) {
                        this.movement.velocity.x -= ACCELERATION;
                    } else if (this.movement.velocity.x < -ACCELERATION) {
                        this.movement.velocity.x += ACCELERATION;
                    } else {
                        this.movement.velocity.x = 0;
                        this.animFrame = 0;
                    }
                }
            } else {
                this.sprite.setFlipX(KEY_WALK < 0);
                if ((KEY_WALK * this.movement.velocity.x) < MAX_SPEED_X) {
                    if ((KEY_WALK * this.movement.velocity.x) < 0) {
                        this.movement.velocity.x += KEY_WALK * 0.8f;
                        if (this.movement.falling < 2) {
                            this.anim = Anim.SKID;
                        }
                    } else {
                        this.movement.velocity.x += KEY_WALK * ACCELERATION;
                    }
                }
            }

            float temp = (Math.abs(this.movement.velocity.x)) / 19f;
            if (temp < 0.2f) {
                temp = 0.2f;
            }

            this.animFrame += temp;
        }
    }

    @Override
    public Player render(double dt) {
        frame += 1;

        if (lastPowerUp != powerUp) {
            try {
                this.info = NeoarcSpriteLoader.load(
                        SuperMaxMakerPaths.image("objects/characters/players/" + this.character + "/" + this.powerUp + ".json", false)
                );
                this.sprite.setTexture(
                        new Texture(
                                SuperMaxMakerPaths.image("objects/characters/players/" + this.character + "/" + this.powerUp)
                        )
                );
            } catch (Exception _) {}
            lastPowerUp = powerUp;
        }

        List<NeoarcSpriteInfo.Frame> frames = NeoarcSpriteUtil.getFrames(info, anim.toString());
        rectis = new ArrayList<>();
        vector2is = new ArrayList<>();
        for (NeoarcSpriteInfo.Frame frame : frames) {
            rectis.add(NeoarcSpriteUtil.convertRectToRecti(frame));
            vector2is.add(NeoarcSpriteUtil.convertOffsetToVector2i(frame));
        }

        moveLeftRight(dt);

        intAnimFrame = ((int)(animFrame)) % rectis.size();

        if (intAnimFrame >= rectis.size()) {
            intAnimFrame = 0;
        }

        Recti rect = rectis.get(intAnimFrame);
        Vector2i offset = vector2is.get(intAnimFrame);

        this.lastVelocity.set(this.movement.velocity);

        this.movement.position.x += (this.lastVelocity.x * ((float) dt * 50f));
        this.movement.position.y += (this.lastVelocity.y * ((float) dt * 50f));
        sprite.setPosition(
                new Vector2f(
                        this.movement.position.x + (offset.x * sprite.getScale().x),
                        this.movement.position.y + (offset.y * sprite.getScale().y)
                )
        );

        sprite.setRegion(rect).render(dt);
        return this;
    }

    @Override
    public void dispose() {

    }
}
