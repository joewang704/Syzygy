package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {

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
            if (x >= Constants.GAMESCREEN_WIDTH - width) {
                x = Constants.GAMESCREEN_HEIGHT - height;
                changeDirectionX();
            } else if (x < 0) {
                x = 0;
                changeDirectionX();
            }
            if (y >= Constants.GAMESCREEN_HEIGHT - height) {
                y = Constants.GAMESCREEN_HEIGHT - height;
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
