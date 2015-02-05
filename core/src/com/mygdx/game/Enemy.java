package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {

    private int moveCtr;
    Vector2 moveDirection;
    Texture enemyImage;

    public Enemy() {
        moveCtr = 0;
        moveDirection = new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign());
        enemyImage = new Texture(Gdx.files.internal("cuteSlime64.png"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moveCtr < 20) {
            moveCtr++;
            setX(getX() + 200 * Gdx.graphics.getDeltaTime() * moveDirection.x);
            setY(getY() + 200 * Gdx.graphics.getDeltaTime() * moveDirection.y);
            if (getX() + getWidth() >= Constants.GAMESCREEN_WIDTH) {
                setX(Constants.GAMESCREEN_WIDTH - getWidth());
                changeDirectionX();
            } else if (getX() < 0) {
                setX(0);
                changeDirectionX();
            }
            if (getY() >= Constants.GAMESCREEN_HEIGHT - getHeight()) {
                setY(Constants.GAMESCREEN_HEIGHT - getHeight());
                changeDirectionY();
            } else if (getY() < 0) {
                setY(0);
                changeDirectionY();
            }
        } else {
            changeDirectionX();
            changeDirectionY();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemyImage, getX(), getY());
    }
    public void changeDirectionX() {
        moveDirection.set(MathUtils.random() * MathUtils.randomSign(), moveDirection.y);
        moveCtr = 0;
    }

    public void changeDirectionY() {
        moveDirection.set(moveDirection.x, MathUtils.random() * MathUtils.randomSign());
        moveCtr = 0;
    }
}
