package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jatin1 on 2/13/15.
 * Represents a larger, teleporting enemy.
 */
public class Enemy_Golem extends Enemy {

    public Enemy_Golem() {
        moveCtr = 0;
        enemyImage = new Texture(Gdx.files.internal("golem.png"));
        enemyHeight = Constants.GAMESCREEN_HEIGHT / 6f;
        enemyWidth = Constants.GAMESCREEN_WIDTH / 3.6f;
        setBounds(xPos, yPos, enemyWidth, enemyHeight);
    }

    @Override
    public void act(float delta) {
       if (moveCtr < 20) {
           moveCtr++;
       } else {
           super.act(delta);
           setX(MathUtils.random(Constants.GAMESCREEN_WIDTH - enemyWidth));
           setY(MathUtils.random(Constants.GAMESCREEN_HEIGHT - enemyHeight));
           if (getX() + getWidth() >= Constants.GAMESCREEN_WIDTH) {
               setX(Constants.GAMESCREEN_WIDTH - getWidth());
           } else if (getX() < 0) {
               setX(0);
           }
           if (getY() >= Constants.GAMESCREEN_HEIGHT - getHeight()) {
               setY(Constants.GAMESCREEN_HEIGHT - getHeight());
           } else if (getY() < 0) {
               setY(0);
           }
           moveCtr = 0;
       }
    }
}

