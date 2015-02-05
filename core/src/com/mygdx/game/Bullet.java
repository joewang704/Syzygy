package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by wojang on 1/26/15.
 */
public class Bullet extends Actor {

    private Rectangle bounds;
    public Vector2 direction;
    public float speed;
    public Vector2 velocity;
    public boolean isEnemy;

    public Bullet(Vector2 direction, float speed, boolean isEnemy) {
        this.bounds = new Rectangle();
        this.direction = direction;
        this.speed = speed;
        this.isEnemy = isEnemy;
        velocity = new Vector2(direction.x * speed, direction.y * speed);
    }

    public Bullet(Vector2 direction, float speed, boolean isEnemy,
                  float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.direction = direction;
        this.speed = speed;
        this.isEnemy = isEnemy;
        velocity = new Vector2(direction.x * speed, direction.y * speed);
    }

    @Override
    public void act(float delta) {
        bounds.x += velocity.x * delta;
        bounds.y += velocity.y * delta;
    }
    public Vector2 getVelocity() { return velocity; }

    public float getX() { return bounds.x; }
    public float getY() { return bounds.y; }
    public float getWidth() { return bounds.width; }
    public float getHeight() { return bounds.height; }

    public void setX(float x) { bounds.x = x; }
    public void setY(float y) { bounds.y = y; }
    public void setWidth(float x) { bounds.width = x; }
    public void setHeight(float x) { bounds.height = x; }
}
