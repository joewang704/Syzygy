package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by wojang on 1/30/15.
 */
public class Constants {
    //Constants
    //Enemy-specific constants are now protected variables in the class. Access via "Enemy_Name.enemyHeight", etc.
    static float GAMESCREEN_WIDTH = Gdx.graphics.getWidth();//800
    static float GAMESCREEN_HEIGHT = Gdx.graphics.getHeight();//480
    static float BULLET_WIDTH = GAMESCREEN_WIDTH / 25;//32
    static float BULLET_HEIGHT = GAMESCREEN_HEIGHT / 15;//32
    static float USER_WIDTH = GAMESCREEN_WIDTH / 10;//64
    static float USER_HEIGHT = GAMESCREEN_HEIGHT / 6;//64
    static float PORTAL_WIDTH = GAMESCREEN_WIDTH / 7.5f;
    static float PORTAL_HEIGHT = GAMESCREEN_HEIGHT / 4.5f;

    static float HOME_PORTAL_WIDTH = PORTAL_WIDTH * 1.5f;
    static float HOME_PORTAL_HEIGHT = PORTAL_HEIGHT * 1.5f;

    //UI constants
    static float MAINMENU_WIDTH = GAMESCREEN_WIDTH / 6;
    static float MAINMENU_HEIGHT = GAMESCREEN_HEIGHT / 15;
    static float MAINMENU_X = GAMESCREEN_WIDTH / 2 - MAINMENU_WIDTH / 2;
    static float MAINMENU_Y = GAMESCREEN_HEIGHT / 2 - MAINMENU_HEIGHT / 2;
    static float MAINMENU_BUTTON_HEIGHT = GAMESCREEN_HEIGHT / 5;
    static float MAINMENU_BUTTON_WIDTH = GAMESCREEN_WIDTH / 6;

    //lets not use stretch viewport because we don't really want aspect ratio being changed, maybe?
}