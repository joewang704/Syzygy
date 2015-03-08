package com.mygdx.game;

/**
 * Created by Lucas on 2/13/2015.
 */
public class Room_Trap extends Room {

    public Room_Trap(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        setEnemyNumber(1);
    }

}
