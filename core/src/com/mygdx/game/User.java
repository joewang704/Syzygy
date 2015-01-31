package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class User extends Rectangle {

    public static Array<Bullet> userBullets = new Array<Bullet>();
    private long lastShotTime;
    private int atkSpeed;

    public User() {
        atkSpeed = 300000000;
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= 180 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += 180 * Gdx.graphics.getDeltaTime();
    }

    public void move(Vector2 direction) {
        x += direction.x;
        y += direction.y;
    }

    public void fireBullet(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor(), speed, false);
        bullet.x = x;
        bullet.y = y;
        // + Constants.USER_WIDTH / 4
        if (!bullet.getVelocity().equals(new Vector2 (0, 0))) {
            bullet.width = Constants.BULLET_WIDTH;
            bullet.height = Constants.BULLET_HEIGHT;
            userBullets.add(bullet);
            lastShotTime = TimeUtils.nanoTime();
        }
    }

    public long getLastShotTime() { return lastShotTime; }

    public int getAtkSpeed() {
        return atkSpeed;
    }
}
