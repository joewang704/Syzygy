package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Rectangle {

    int moveCtr;
    Vector2 moveDirection;

    public Enemy() {
        moveCtr = 0;
        moveDirection = new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign());
    }

    public void move() {
        if (moveCtr < 20) {
            moveCtr++;
            x += 200 * Gdx.graphics.getDeltaTime() * moveDirection.x;
            y += 200 * Gdx.graphics.getDeltaTime() * moveDirection.y;
            if (x >= Constants.GAMESCREEN_WIDTH - 64) {
                x = Constants.GAMESCREEN_HEIGHT - 64;
                changeDirectionX();
            } else if (x < 0) {
                x = 0;
                changeDirectionX();
            }
            if (y >= Constants.GAMESCREEN_HEIGHT - 64) {
                y = Constants.GAMESCREEN_HEIGHT - 64;
                changeDirectionY();
            } else if (y < 0) {
                y = 0;
                changeDirectionY();
            }
        } else {
            changeDirectionX();
            changeDirectionY();
        }
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
