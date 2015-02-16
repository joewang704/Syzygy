package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Jay on 2/15/2015.
 */
public class Enemy_BigSlime extends Enemy_Slime {
    private Array<Enemy> enemies;//regenerate slimes when hit
    private Stage s;
    public Enemy_BigSlime(float x, float y, Stage s, Array<Enemy> enemies) {
        super(x, y, 128, 128);
        this.s = s;
        this.enemies = enemies;
    }
    //public Enemy_BigSlime(float x, float y)
    @Override
    public int hitAction() {
        //remove();
        Enemy e1 = new Enemy_Slime(getX(), getY());
        Enemy e2 = new Enemy_Slime(getX(), getY());
        Enemy e3 = new Enemy_Slime(getX(), getY());
        Enemy e4 = new Enemy_Slime(getX(), getY());
        enemies.addAll(e1, e2, e3, e4);
        s.addActor(e1);
        s.addActor(e2);
        s.addActor(e3);
        s.addActor(e4);
        remove();
        return 3;//report net increase in enemies
    }
}