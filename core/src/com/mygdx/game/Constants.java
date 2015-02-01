package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by wojang on 1/30/15.
 */
public class Constants {
    //Constants
    static float GAMESCREEN_WIDTH = Gdx.graphics.getWidth();//800
    static float GAMESCREEN_HEIGHT = Gdx.graphics.getHeight();//480
    static float BULLET_WIDTH = GAMESCREEN_WIDTH / 15;//32
    static float BULLET_HEIGHT = GAMESCREEN_HEIGHT / 10;//32
    static float USER_WIDTH = GAMESCREEN_WIDTH / 10;//64
    static float USER_HEIGHT = GAMESCREEN_HEIGHT / 6;//64
    static float SLIME_ENEMY_HEIGHT = GAMESCREEN_HEIGHT / 10;
    static float SLIME_ENEMY_WIDTH = GAMESCREEN_WIDTH / 10;
    //radius or diameter? not sure
    static float CP_PADDING = GAMESCREEN_WIDTH/60;
    static float MOVE_STICK_RADIUS = GAMESCREEN_WIDTH / 12;
}