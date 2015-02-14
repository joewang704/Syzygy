package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Lucas on 2/13/2015.
 */
public class Enemy_Volans extends Enemy {

    public static Array<Bullet> enemyBullets = new Array<Bullet>();

    public Enemy_Volans() {
        moveCtr = 0;
        //vector needs to be normalized even if its x and y are less than 1
        moveDirection = (new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign())).nor();
        enemyImage = new Texture(Gdx.files.internal("volans.png"));
    }

    @Override
    public void act(float delta) {
        if (moveCtr < 20) {
            moveCtr++;
        } else {
            super.act(delta);
            setX(getX() + MathUtils.random(Constants.GAMESCREEN_WIDTH - Constants.ENEMY_VOLANS_WIDTH));
            setY(getY() + MathUtils.random(Constants.GAMESCREEN_HEIGHT - Constants.ENEMY_VOLANS_HEIGHT));
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

    public void attack(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70).scl(3), speed, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        enemyBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }
}
