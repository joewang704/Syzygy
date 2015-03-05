package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by wojang on 2/3/15.
 * TODO Portals .setVisible(false) on instantiation, .setVisible(true) on enemiesDefeated(), if (isVisible()) then collision sends to next room
 * TODO Have map of Vector2 that maps Vector2 pos --> Room room,
 * TODO make portal position constants
 * TODO fix constructors
 */
public class Room {
    Dungeon dungeon;

    private Portal topPortal;
    private Portal leftPortal;
    private Portal rightPortal;
    private Portal bottomPortal;

    private Array<Portal> portals;

    private int enemyNumber;
    Vector2 position;

    public Room(Dungeon dungeon, int x, int y) {
        this.dungeon = dungeon;
        portals = new Array();
        position = new Vector2(x, y);

        enemyNumber = 7;
        setTopPortal();
        setLeftPortal();
        setRightPortal();
        setBottomPortal();
    }

    public Room(Dungeon dungeon, int x, int y, int enemyNumber) {
        this(dungeon, x, y);
        this.enemyNumber = enemyNumber;
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
    //will have to check if the roomAbove is not null
    public void setTopPortal() {
        if (portals.contains(topPortal, true)) {
            portals.removeValue(topPortal, true);
        }
        topPortal = new Portal(this, PortalPos.UP,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT/2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        portals.add(topPortal);
    }
    public void setLeftPortal() {
        if (portals.contains(leftPortal, true)) {
            portals.removeValue(leftPortal, true);
        }
        leftPortal = new Portal(this, PortalPos.LEFT,
                -Constants.PORTAL_WIDTH/2,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        portals.add(leftPortal);
    }
    public void setRightPortal() {
        if (portals.contains(rightPortal, true)) {
            portals.removeValue(rightPortal, true);
        }
        rightPortal = new Portal(this, PortalPos.RIGHT,
                Constants.GAMESCREEN_WIDTH - Constants.PORTAL_WIDTH/2,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        portals.add(rightPortal);
    }
    public void setBottomPortal() {
        if (portals.contains(bottomPortal, true)) {
            portals.removeValue(bottomPortal, true);
        }
        bottomPortal = new Portal(this,PortalPos.DOWN,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                -Constants.PORTAL_HEIGHT/2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        portals.add(bottomPortal);
    }

    public Array<Portal> getPortals() {
        return portals;
    }

    public int getEnemyNumber() { return enemyNumber; }
    public void setEnemyNumber(int n) { enemyNumber = n; }

    public void removePortalsfromStage() {
        topPortal.remove();
        leftPortal.remove();
        rightPortal.remove();
        bottomPortal.remove();
    }

    public void setPosition(int x, int y) {
        position = new Vector2(x, y);
    }
    public void setPosition(Vector2 newPosition) {
        position.x = newPosition.x;
        position.y = newPosition.y;
    }
    public int getX() { return (int) position.x; }
    public int getY() { return (int) position.y; }
    public Vector2 getPosition() { return position; }
    public String toString() {
        return "" + topPortal + "\n" + leftPortal + "\n" + bottomPortal + "\n" + rightPortal;
    }

}
