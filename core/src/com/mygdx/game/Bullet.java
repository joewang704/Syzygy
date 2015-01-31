package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by wojang on 1/26/15.
 */
public class Bullet extends Rectangle {

    public Vector2 direction;
    public float speed;
    public Vector2 velocity;
    public boolean isEnemy;

    public Bullet(Vector2 direction, float speed, boolean isEnemy) {
        this.direction = direction;
        this.speed = speed;
        this.isEnemy = isEnemy;
        velocity = new Vector2(direction.x * speed, direction.y * speed);
    }

    public Vector2 getVelocity() { return velocity; }
}
