package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by wojang on 2/3/15.
 */
public class Room {
    Dungeon dungeon;
    private Portal topPortal;
    private Portal leftPortal;
    private Portal rightPortal;
    private Portal bottomPortal;
    int unassignedPortals = 4;
    Vector2 position;
    public Room(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
    public Room(Dungeon dungeon, int x, int y) {
        this.dungeon = dungeon;
        position = new Vector2(x, y);
    }
    public Portal getTopPortal() {
        return topPortal;
    }
    public Portal getLeftPortal() {
        return leftPortal;
    }
    public Portal getRightPortal() {
        return rightPortal;
    }
    public Portal getBottomPortal() {
        return bottomPortal;
    }
    //sets portals to point to specific rooms
    public void setTopPortal(Room nextRoom) {
        topPortal = new Portal(this, nextRoom,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        if (unassignedPortals > 0) {
            unassignedPortals--;
        }
    }
    public void setLeftPortal(Room nextRoom) {
        leftPortal = new Portal(this, nextRoom,
                0,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        if (unassignedPortals > 0) {
            unassignedPortals--;
        }
    }
    public void setRightPortal(Room nextRoom) {
        rightPortal = new Portal(this, nextRoom,
                Constants.GAMESCREEN_WIDTH - Constants.PORTAL_WIDTH,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        if (unassignedPortals > 0) {
            unassignedPortals--;
        }
    }
    public void setBottomPortal(Room nextRoom) {
        bottomPortal = new Portal(this, nextRoom,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                0,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        if (unassignedPortals > 0) {
            unassignedPortals--;
        }
    }
    public Array<Portal> getPortals() {
        Array<Portal> portalArray = new Array<Portal>();
        if(topPortal != null) {
            portalArray.add(topPortal);
        }
        if(leftPortal != null) {
            portalArray.add(leftPortal);
        }
        if(rightPortal != null) {
            portalArray.add(rightPortal);
        }
        if(bottomPortal != null) {
            portalArray.add(bottomPortal);
        }
        return portalArray;
    }
    public int getUnassignedPortals() {
        return unassignedPortals;
    }
    public void setPosition(int x, int y) {
        position = new Vector2(x, y);
    }
    public void setPosition(Vector2 newPosition) { position = newPosition; }
    public int getX() { return (int) position.x; }
    public int getY() {
        return (int) position.y;
    }


}
