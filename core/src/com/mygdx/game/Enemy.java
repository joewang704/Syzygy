package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Actor {
    //Empty for now - add properties of every enemy later (health, etc)
    //protected for subclasses

    protected int moveCtr;
    protected long lastShotTime;
    protected Array<Bullet> enemyBullets;
    protected Vector2 moveDirection;
    protected Texture enemyImage;

    public Enemy() {
            //fill in later with properties
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemyImage, getX(), getY(), getWidth(), getHeight());
    }

    public void attack(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70).scl(3), speed, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        enemyBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }

}
