package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by wojang on 1/26/15.
 */
public class Bullet extends Actor {

    public Vector2 direction;
    public float speed;
    public Vector2 velocity;
    private Texture bulletImg;
    public boolean isEnemy;
    private Rectangle bounds;
    int ctr = 0;

    //this constructor has no x y height or width
    public Bullet(Vector2 direction, float speed, boolean isEnemy) {
        bulletImg = new Texture(Gdx.files.internal("soccer.png"));
        this.direction = direction;
        this.speed = speed;
        this.isEnemy = isEnemy;
        velocity = new Vector2(direction.x * speed, direction.y * speed);
//        System.out.println(velocity);
    }

    public Bullet(Vector2 direction, float speed, boolean isEnemy,
                  float x, float y, float width, float height) {
        this(direction, speed, isEnemy);
        setBounds(x, y, width, height);
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        bounds.x = getX();
        bounds.y = getY();
//        ctr++;
//        if (ctr == 60) {
//            ctr = 0;
//            System.out.println(getX() + ", "  + getY());
//        }
    }

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bulletImg, getX(), getY(), getWidth(), getHeight());
    }

    public Vector2 getVelocity() { return velocity; }

    public Rectangle getBounds() { return bounds; }
}
