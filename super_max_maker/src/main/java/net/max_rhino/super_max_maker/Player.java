package net.max_rhino.super_max_maker;

import net.max_rhino.gl2d.GL2D;
import net.max_rhino.gl2d.engine.*;
import net.max_rhino.gl2d.engine.math.Recti;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteInfo;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteLoader;
import net.max_rhino.super_max_maker.loaders.NeoarcSpriteUtil;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Player implements DrawableDisposable {
    private final Sprite sprite;
    private NeoarcSpriteInfo info;
    private float animFrame;
    private double frame;
    private Anim anim = Anim.IDLE;
    private Anim actualAnim = Anim.IDLE;
    private PowerUp powerUp = PowerUp.SMALL;
    private PowerUp lastPowerUp = PowerUp.SMALL;

    private final String character;

    public static int KEY_WALK = 0;

    private final Movement movement = Movement.getInstance();

    private Controls controls;

    public static final Map<String, Sound> SOUNDS = Map.ofEntries(
            Map.entry("JumpSmall", new Sound(SuperMaxMakerPaths.sound("JumpSmall"))),
            Map.entry("RunBreak", new Sound(SuperMaxMakerPaths.sound("RunBreak")))
    );

    public Player setControls(Controls controls) {
        this.controls = controls;
        return this;
    }

    public Controls getControls() {
        return controls;
    }

    public static float ACCELERATION = 0.4f;
    public static float MAX_WALK_SPEED_X = 5f;
    public static float MAX_SPEED_X = 15f;

    private static final int MOVEMENT_UPDATE_INTERVAL = 5;

    public static class Movement {
        private Vector2f position = new Vector2f(72 + 640, 360);
        private Vector2f velocity = new Vector2f();

        private float falling = 0f;
        private int jumping = 0;

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

        public int getJumping() {
            return jumping;
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

        public void setJumping(int jumping) {
            this.jumping = jumping;
        }

        /// Default instance
        public static Movement getInstance() {
            return new Movement();
        }
    }

    public static class Controls {
        private int[] left;
        private int[] right;
        private int[] up;
        private int[] down;
        private int[] jump;
        private int[] run;

        // Getters

        public int[] getLeft() {
            return left;
        }

        public int[] getRight() {
            return right;
        }

        public int[] getUp() {
            return up;
        }

        public int[] getDown() {
            return down;
        }

        public int[] getJump() {
            return jump;
        }

        public int[] getRun() {
            return run;
        }

        // Setters

        public Controls setLeft(int... left) {
            this.left = left;
            return this;
        }

        public Controls setRight(int... right) {
            this.right = right;
            return this;
        }

        public Controls setUp(int... up) {
            this.up = up;
            return this;
        }

        public Controls setDown(int... down) {
            this.down = down;
            return this;
        }

        public Controls setJump(int... jump) {
            this.jump = jump;
            return this;
        }

        public Controls setRun(int... run) {
            this.run = run;
            return this;
        }

        public static Controls defaultInstance() {
            return new Controls()
                    .setLeft(GLFW.GLFW_KEY_A)
                    .setRight(GLFW.GLFW_KEY_D)
                    .setUp(GLFW.GLFW_KEY_W)
                    .setDown(GLFW.GLFW_KEY_S)
                    .setJump(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_Z)
                    .setRun(GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_X);
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

    public Player(String character, Controls controls) {
        this.controls = controls;

        this.character = character;
        try {
            this.info = NeoarcSpriteLoader.load(
                    SuperMaxMakerPaths.image(StringUtils.capitalize(this.character) + "Small.sprite", false)
            );
            this.sprite = new Sprite(
                    new Texture(
                            SuperMaxMakerPaths.image(StringUtils.capitalize(this.character) + "Small")
                    ),
                    0,
                    0
            ).setScale(Main.SCALE);
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

    public void handleLeftRightMovement(double dt) {
        if ((int)frame % MOVEMENT_UPDATE_INTERVAL == 0) {
            if (this.anim == Anim.CROUCH) {
                int tempAxis = Input.getAxisOfOneOfTheKeys(this.controls.getLeft(), this.controls.getRight());
                if (Math.abs(this.movement.velocity.x) > ACCELERATION) {
                    this.movement.velocity.x += (-ACCELERATION * (Math.abs(this.movement.velocity.x)/this.movement.velocity.x));
                    this.makeSkidSmoke(dt);
                } else {
                    this.movement.velocity.x = 0;
                    this.animFrame = 0;
                }

                if (tempAxis != 0) {
                    this.sprite.setFlipX(tempAxis < 0);
                }
            } else {
                this.anim = Anim.WALK;

                KEY_WALK = Input.getAxisOfOneOfTheKeys(this.controls.getLeft(), this.controls.getRight());

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
                    float maxSpeedX = Input.isOneOfTheKeysDown(this.controls.run) ? MAX_SPEED_X : MAX_WALK_SPEED_X;
                    if ((KEY_WALK * this.movement.velocity.x) < maxSpeedX) {
                        if ((KEY_WALK * this.movement.velocity.x) < 0) {
                            this.movement.velocity.x += KEY_WALK * 0.8f;
                            if (this.movement.falling < 2) {
                                this.anim = Anim.SKID;
                                this.makeSkidSmoke(dt);
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
    }

    public void makeSkidSmoke(double dt) {
        animFrame += 1;
        if ((int)animFrame % 3 < 1) {
            SOUNDS.get("RunBreak").replay();
        }
    }

    public void getUp(double dt) {
        anim = Anim.IDLE;
    }

    public void handleJumpCrouchMovement(double dt) {
        if ((int)frame % MOVEMENT_UPDATE_INTERVAL == 0) {
            this.movement.velocity.y -= 2f;
            if (this.movement.velocity.y < -22) {
                this.movement.velocity.y = -22;
            }

            if (Input.isOneOfTheKeysDown(this.controls.getDown())) {
                if (this.movement.falling < 2) {
                    anim = Anim.CROUCH;
                }
                this.movement.jumping = 0;
            } else {
                if (this.anim == Anim.CROUCH) {
                    getUp(dt);
                } else {
                    if (Input.isOneOfTheKeysDown(this.controls.getJump())) {
                        if (this.movement.falling < 2 || this.movement.jumping > 0) {
                            this.movement.jumping += 1;
                            if (this.movement.jumping < 11) {
                                this.movement.velocity.y = 13;
                                if (this.movement.jumping == 1) {
                                    SOUNDS.get("JumpSmall").replay();
                                    if (this.powerUp == PowerUp.SMALL) {
                                        this.movement.jumping = 3;
                                    }
                                }
                            }
                        }
                    } else {
                        this.movement.jumping = 0;
                    }
                }
            }
        }
    }

    public void moveX(double dt) {
        this.movement.position.x += (this.movement.velocity.x * ((float) dt * 50f));
    }

    public void moveY(double dt) {
        this.movement.position.y -= (this.movement.velocity.y * ((float) dt * 50f));
        this.movement.falling += 1;

        if (this.movement.position.y >= (624 + 360)) {
            this.movement.falling = 0;
            this.movement.velocity.y = 0;
            this.movement.position.y = (624 + 360);
        }
    }

    private void makeActualAnimation() {
        this.actualAnim = this.anim;

        if (this.movement.falling > 0) {
            if (this.movement.velocity.y > 1) {
                this.actualAnim = Anim.JUMP;
            } else {
                this.actualAnim = Anim.JUMP_DOWN;
            }
        }
    }

    private void limitCameraEdge(int edgeX, int edgeY, int minusTop) {
        if (MainApp.CAMERA.pos.x < edgeX) {
            MainApp.CAMERA.pos.x = edgeX;
        }
        if (MainApp.CAMERA.pos.y < edgeY) {
            MainApp.CAMERA.pos.y = edgeY;
        }
    }

    @Override
    public Player render(double dt) {
        frame += 1;

        if (lastPowerUp != powerUp) {
            try {
                this.info = NeoarcSpriteLoader.load(
                        SuperMaxMakerPaths.image(StringUtils.capitalize(this.character) + StringUtils.capitalize(this.powerUp.toString()) + ".sprite", false)
                );
                this.sprite.setTexture(
                        new Texture(
                                SuperMaxMakerPaths.image(StringUtils.capitalize(this.character) + StringUtils.capitalize(this.powerUp.toString()))
                        )
                );
            } catch (Exception _) {}
            lastPowerUp = powerUp;
        }

        handleLeftRightMovement(dt);
        handleJumpCrouchMovement(dt);

        this.moveX(dt);
        this.moveY(dt);

        makeActualAnimation();

        List<NeoarcSpriteInfo.Frame> frames = NeoarcSpriteUtil.getFrames(info, actualAnim.toString());
        List<Recti> rectis = new ArrayList<>();
        List<Vector2i> vector2is = new ArrayList<>();
        for (NeoarcSpriteInfo.Frame frame : frames) {
            rectis.add(NeoarcSpriteUtil.convertRectToRecti(frame));
            vector2is.add(NeoarcSpriteUtil.convertOffsetToVector2i(frame));
        }

        int intAnimFrame = ((int) (animFrame)) % rectis.size();

        if (intAnimFrame >= rectis.size()) {
            intAnimFrame = 0;
        }

        Recti rect = rectis.get(intAnimFrame);
        Vector2i offset = vector2is.get(intAnimFrame);

        MainApp.CAMERA.pos.x = (this.movement.position.x - offset.x) - 640;
        MainApp.CAMERA.pos.y = (this.movement.position.y - offset.y) - 360;

        limitCameraEdge(640, 360, 0);

        sprite.setOffset(
                new Vector2f(
                        offset.x,
                        offset.y
                )
        ).setPosition(
                new Vector2f(
                        this.movement.position.x - MainApp.CAMERA.pos.x,
                        this.movement.position.y - MainApp.CAMERA.pos.y
                )
        );

        sprite.setRegion(rect).render(dt);
        return this;
    }

    @Override
    public void dispose() {

    }
}
