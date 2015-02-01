package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by wojang on 1/30/15.
 */
public class Constants {
    //Constants
    static int GAMESCREEN_WIDTH = Gdx.graphics.getWidth();//800
    static int GAMESCREEN_HEIGHT = Gdx.graphics.getHeight();//480
    static int BULLET_WIDTH = GAMESCREEN_WIDTH / 15;//32
    static int BULLET_HEIGHT = GAMESCREEN_HEIGHT / 10;//32
    static int USER_WIDTH = GAMESCREEN_WIDTH / 10;//64
    static int USER_HEIGHT = GAMESCREEN_HEIGHT / 6;//64
    static int SLIME_ENEMY_HEIGHT = GAMESCREEN_HEIGHT / 10;
    static int SLIME_ENEMY_WIDTH = GAMESCREEN_WIDTH / 10;
    //radius or diameter? not sure
    static int MOVE_STICK_RADIUS = GAMESCREEN_WIDTH / 12;
}