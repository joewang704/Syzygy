package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Screen;

/**
 * Created by wojang on 2/3/15.
 */
public class Dungeon {
    Array<Room> roomArray;
    Screen roomScreen;
    public Dungeon(Syzygy game, int dungeonId, int roomNumber) {
        roomArray = new Array<Room>(roomNumber);
        for(int i = 0; i < roomArray.size; i++) {
            roomArray.add(new Room(this));
        }
    }
}
