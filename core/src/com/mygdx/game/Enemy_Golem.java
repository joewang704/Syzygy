package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by jatin1 on 2/13/15.
 * Represents a larger, teleporting enemy.
 */
public class Enemy_Golem extends Enemy {

    public static ClassName className = ClassName.GOLEM;

    public Enemy_Golem() {
        moveCtr = 0;
        enemyImage = new Texture(Gdx.files.internal("golem.png"));
        enemyHeight = Constants.GAMESCREEN_HEIGHT / 3.6f;
        enemyWidth = Constants.GAMESCREEN_WIDTH / 6f;
        setBounds(xPos, yPos, enemyWidth, enemyHeight);
    }

    //for creating bigger golems
    public Enemy_Golem(float golemWidth, float golemHeight) {
        this();
        setBounds(xPos, yPos, golemWidth, golemHeight);
    }

    //now using actions to move, and alpha for fadeOut and fadeIn not working I guess...
    @Override
    public void act(float delta) {
        super.act(delta);
        if (moveCtr < 100) {
           moveCtr++;
       } else {
           addAction(Actions.sequence(Actions.fadeOut(.5f),
                   Actions.moveTo(MathUtils.random(Constants.GAMESCREEN_WIDTH - enemyWidth),
                           MathUtils.random(Constants.GAMESCREEN_HEIGHT - enemyHeight)),
                   Actions.fadeIn(.5f)));
           checkBoundsCollision();

           moveCtr = 0;
       }
    }
}

