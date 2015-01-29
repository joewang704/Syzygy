package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by wojang on 1/26/15.
 */
public class Bullet extends Rectangle {

    public float angle;
    public Vector2 v;
    public boolean isEnemy;

    public Bullet(Vector2 v, boolean isEnemy) {
        this.v = v;
        this.isEnemy = isEnemy;
    }
}
