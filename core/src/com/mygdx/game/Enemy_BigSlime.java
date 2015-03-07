package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Jay on 2/15/2015.
 */
public class Enemy_BigSlime extends Enemy_Slime {
    private Array<Enemy> enemies;//regenerate slimes when hit

    public Enemy_BigSlime(Array<Enemy> enemies) {
        super(MathUtils.random(Constants.GAMESCREEN_WIDTH / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3),
              MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3),128, 128);
        this.enemies = enemies;
    }

    @Override
    public int hitAction() {
        //IT RANDOMLY ONLY SPLITS INTO TWO SOMETIMES WHAT WHY??? - Jatin
        remove();
        Enemy e1 = new Enemy_Slime(getX(), getY());
        Enemy e2 = new Enemy_Slime(getX(), getY());
        Enemy e3 = new Enemy_Slime(getX(), getY());
        Enemy e4 = new Enemy_Slime(getX(), getY());
        enemies.addAll(e1, e2, e3, e4);
        //lastSpawnTime = TimeUtils.nanoTime();
        Syzygy.stage.addActor(e1);
        Syzygy.stage.addActor(e2);
        Syzygy.stage.addActor(e3);
        Syzygy.stage.addActor(e4);
        return 4;//report any increase in enemies
    }
}