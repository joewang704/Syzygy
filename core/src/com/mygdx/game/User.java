package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class User extends Actor {

    public static Array<Bullet> userBullets = new Array<Bullet>();
    private Texture userImg;
    private long lastShotTime;
    private int atkSpeed;
    private float moveSpeed;
    private Touchpad movePad;
    private Touchpad firePad;
    private Rectangle bounds;

    public User(Touchpad move, Touchpad fire, float x, float y, float width, float height) {
        this(move, fire);
        setBounds(x, y, width, height);
        bounds = new Rectangle(x, y, width, height);
    }

    public User(Touchpad move, Touchpad fire) {
        movePad = move;
        firePad = fire;
        atkSpeed = 300000000;
        moveSpeed = 5;
        userImg = new Texture(Gdx.files.internal("wizard.png"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //move();
        //Why does getKnobPercent() work but not getKnob()?
        move(new Vector2(movePad.getKnobPercentX(), movePad.getKnobPercentY()));
        checkScreenBoundsCollision();
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(userImg, getX(), getY(), getWidth(), getHeight());
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) setX(getX() - 200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) setX(getX() + 200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.S)) setY(getY() - 180 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.W)) setY(getY() + 180 * Gdx.graphics.getDeltaTime());
    }

    public void move(Vector2 direction) {
        setX(getX() + direction.x*moveSpeed);
        setY(getY() + direction.y*moveSpeed);
    }

    public void checkScreenBoundsCollision() {
        if (getX() < 0) setX(0);
        if (getY() < 0) setY(0);
        if (getX() > Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH) {
            setX(Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH);
        }
        if (getY() > Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT){
            setY(Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT);
        }
    }

    public Bullet fireBullet(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70), speed, false,
                this.getX(), this.getY(), this.getWidth(), this.getHeight());
        // + Constants.USER_WIDTH / 4
        userBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
        return bullet;
    }


    public Rectangle getBounds() { return bounds; }
    public long getLastShotTime() { return lastShotTime; }
    public int getAtkSpeed() { return atkSpeed; }
}
