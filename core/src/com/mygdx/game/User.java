package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class User extends Actor {

    public static Array<Bullet> userBullets = new Array<Bullet>();
    private Texture userImg;
    private long lastShotTime;
    private int atkSpeed;

    public User(float x, float y, float width, float height) {
        this();
        setBounds(x, y, width, height);
    }

    public User() {
        atkSpeed = 300000000;
        userImg = new Texture(Gdx.files.internal("wizard.png"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        move();
        checkScreenBoundsCollision();
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(userImg, getX(), getY());
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) setX(getX() - 200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) setX(getX() + 200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.S)) setY(getY() - 180 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.W)) setY(getY() + 180 * Gdx.graphics.getDeltaTime());
    }

    public void move(Vector2 direction) {
        setX(direction.x);
        setY(direction.y);

        //keep user from moving off the screen
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
        Bullet bullet = new Bullet(direction.nor(), speed, false);
        bullet.setX(getX());
        bullet.setY(getY());
        // + Constants.USER_WIDTH / 4
        if (!bullet.getVelocity().equals(new Vector2 (0, 0))) {
            bullet.setWidth(Constants.BULLET_WIDTH);
            bullet.setHeight(Constants.BULLET_HEIGHT);
            userBullets.add(bullet);
            lastShotTime = TimeUtils.nanoTime();
        }
        return bullet;
    }


    public long getLastShotTime() { return lastShotTime; }
    public int getAtkSpeed() { return atkSpeed; }
}
