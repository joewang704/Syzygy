package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lucas on 2/13/2015.
 *  TODO implement Room_Boss
 */
public class Room_Boss extends Room {

    private Enemy boss;
    private ChangeScreenPortal endDungeon;

    public Room_Boss(Dungeon dungeon, int x, int y, User user, Game syzygy) {
        super(dungeon, x, y);
        setEnemyNumber(1);
        boss = new Boss_Volans(user);

        endDungeon = new ChangeScreenPortal(Gdx.files.internal("homePortal.png"));
        endDungeon.setVisible(false);
    }

    public ChangeScreenPortal getEndDungeon() {return endDungeon;}
    public Enemy getBoss() {return boss;}
}
