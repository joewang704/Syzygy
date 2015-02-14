package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    //Empty for now - add properties of every enemy later (health, etc)
    //protected for subclasses

    protected int moveCtr;
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

    public Bullet attack() {
        return null;
    }

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }

}
