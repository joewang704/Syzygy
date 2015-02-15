package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by jatin1 on 2/13/15.
 * Slime enemy - normal movement.
 */
public class Enemy_Slime extends Enemy{

    public static ClassName className = ClassName.SLIME;

    public Enemy_Slime() {
        moveCtr = 0;
        //vector needs to be normalized even if its x and y are less than 1
        moveDirection = (new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign())).nor();
        enemyImage = new Texture(Gdx.files.internal("cuteSlime64.png"));
    }

    public Enemy_Slime(float x, float y) {

        this(x, y, 64, 64);
    }

    public Enemy_Slime(float x, float y, float width, float height) {
        this();
        setBounds(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moveCtr < 75) {
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

    public void changeDirectionX() {
        moveDirection.set(MathUtils.random() * MathUtils.randomSign(), moveDirection.y);
        moveCtr = 0;
    }

    public void changeDirectionY() {
        moveDirection.set(moveDirection.x, MathUtils.random() * MathUtils.randomSign());
        moveCtr = 0;
    }
}
