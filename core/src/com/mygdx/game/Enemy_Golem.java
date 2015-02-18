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
    }

    public Enemy_Golem(float x, float y, float width, float height) {
        this();
        setBounds(x, y, width, height);
    }

    //now using actions to move, and alpha for fadeOut and fadeIn not working I guess...
    @Override
    public void act(float delta) {
        super.act(delta);
        if (moveCtr < 100) {
           moveCtr++;
       } else {
           addAction(Actions.sequence(Actions.fadeOut(.5f),
                   Actions.moveTo(MathUtils.random(Constants.GAMESCREEN_WIDTH - Constants.ENEMY_GOLEM_WIDTH),
                           MathUtils.random(Constants.GAMESCREEN_HEIGHT - Constants.ENEMY_GOLEM_HEIGHT)),
                   Actions.fadeIn(.5f)));
           checkBoundsCollision();

           moveCtr = 0;
       }
    }
}

