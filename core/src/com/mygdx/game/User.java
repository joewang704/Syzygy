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
        moveSpeed = 10;
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

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
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

    //70 and 3 are somewhat arbitrary, should be changed
    public Bullet fireBullet(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70).scl(3), speed, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        userBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
        return bullet;
    }


    public Rectangle getBounds() { return bounds; }
    public long getLastShotTime() { return lastShotTime; }
    public int getAtkSpeed() { return atkSpeed; }
}
