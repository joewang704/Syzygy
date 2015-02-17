package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

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
        super.hitAction();
        Enemy e1 = new Enemy_Slime(getX(), getY());
        Enemy e2 = new Enemy_Slime(getX(), getY());
        Enemy e3 = new Enemy_Slime(getX(), getY());
        Enemy e4 = new Enemy_Slime(getX(), getY());
        enemies.addAll(e1, e2, e3, e4);
        //lastSpawnTime = TimeUtils.nanoTime();
        s.addActor(e1);
        s.addActor(e2);
        s.addActor(e3);
        s.addActor(e4);
        return 4;//report any increase in enemies
    }
}