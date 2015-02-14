package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {
    //Empty for now - add properties of every enemy later (health, etc)
    //protected for subclasses

    protected int moveCtr;
    protected Vector2 moveDirection;
    protected Texture enemyImage;
    protected float enemyHeight;
    protected float enemyWidth;
    protected float xPos = MathUtils.random(Constants.GAMESCREEN_WIDTH / 3,
            2 * Constants.GAMESCREEN_WIDTH / 3);
    protected float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
            2 * Constants.GAMESCREEN_HEIGHT / 3);

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

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }
}
