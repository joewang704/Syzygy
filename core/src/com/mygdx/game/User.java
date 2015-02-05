package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class User extends Actor {

    public static Array<Bullet> userBullets = new Array<Bullet>();
    private Rectangle bounds;
    private long lastShotTime;
    private int atkSpeed;

    public User(int x,int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
        atkSpeed = 300000000;
    }

    @Override
    //act() calls act(float delta) with delta = getDeltaTime
    public void act(float delta) {
        move();
        move();

    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) bounds.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) bounds.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) bounds.y -= 180 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) bounds.y += 180 * Gdx.graphics.getDeltaTime();
    }

    public void move(Vector2 direction) {
        bounds.x += direction.x;
        bounds.y += direction.y;

        //keep user from moving off the screen
        if (bounds.x < 0) bounds.x = 0;
        if (bounds.y < 0) bounds.y = 0;
        if (bounds.x > Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH) {
            bounds.x = Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH;
        }
        if (bounds.y > Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT){
            bounds.y = Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT;
        }

    }

    public void fireBullet(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor(), speed, false);
        bullet.setX(bounds.x);
        bullet.setY(bounds.y);
        // + Constants.USER_WIDTH / 4
        if (!bullet.getVelocity().equals(new Vector2 (0, 0))) {
            bullet.setWidth(Constants.BULLET_WIDTH);
            bullet.setHeight(Constants.BULLET_HEIGHT);
            userBullets.add(bullet);
            lastShotTime = TimeUtils.nanoTime();
        }
    }


    public long getLastShotTime() { return lastShotTime; }
    public int getAtkSpeed() {
        return atkSpeed;
    }

    public float getX() { return bounds.x; }
    public float getY() { return bounds.y; }
    public float getWidth() { return bounds.width; }
    public float getHeight() { return bounds.height; }

    public void setX(float x) { bounds.x = x; }
    public void setY(float y) { bounds.y = y; }
    public void setWidth(float x) { bounds.width = x; }
    public void setHeight(float x) { bounds.height = x; }
}
