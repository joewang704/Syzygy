package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by wojang on 2/3/15.
 * TODO Portals .setVisible(false) on instantiation, .setVisible(true) on enemiesDefeated(), if (isVisible()) then collision sends to next room
 * TODO fix constructors
 */
public class Room {
    Dungeon dungeon;

    private boolean portalsAreOnStage;

    private Array<Portal> portals;

    private int enemyNumber;
    Vector2 position;

    public Room(Dungeon dungeon, int x, int y) {
        this.dungeon = dungeon;
        portals = new Array<Portal>();
        portals.addAll(null, null, null, null);
        position = new Vector2(x, y);

        enemyNumber = 2;
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
        return portals.get(0);
    }
    public Portal getLeftPortal() {
        return portals.get(1);
    }
    public Portal getRightPortal() {
        return portals.get(2);
    }
    public Portal getBottomPortal() {
        return portals.get(3);
    }
    public Portal getPortalByOrdinal(int ord) {
        return portals.get(ord);
    }

    public void setPortals(Array<Portal> portals) {
        this.portals = portals;
    }

    //sets portals to point to specific rooms
    //will have to check if the roomAbove is not null
    public void setTopPortal() {
        portals.set(0, new Portal(this, PortalPos.UP,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT));
    }
    public void setLeftPortal() {
        portals.set(1, new Portal(this, PortalPos.LEFT,
                -Constants.PORTAL_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT));
    }
    public void setRightPortal() {
        portals.set(2, new Portal(this, PortalPos.RIGHT,
                Constants.GAMESCREEN_WIDTH - Constants.PORTAL_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2 - Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT));
    }
    public void setBottomPortal() {
        portals.set(3, new Portal(this, PortalPos.DOWN,
                Constants.GAMESCREEN_WIDTH / 2 - Constants.PORTAL_WIDTH / 2,
                -Constants.PORTAL_HEIGHT / 2,
                Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT));
    }

    public Array<Portal> getPortals() {
        return portals;
    }

    public int getEnemyNumber() { return enemyNumber; }
    public void setEnemyNumber(int n) { enemyNumber = n; }

    public void removePortalsfromStage() {
        for (Portal p: portals) {
            p.remove();
        }
    }

    public void setPosition(int x, int y) {
        position = new Vector2(x, y);
    }

    public void setPosition(Vector2 newPosition) {
        position.x = newPosition.x;
        position.y = newPosition.y;
    }

    public boolean PortalsAreOnStage(boolean tf) {
        portalsAreOnStage = tf;
        return portalsAreOnStage;
    }
    public boolean PortalsAreOnStage() {return portalsAreOnStage;}

    public int getX() { return (int) position.x; }
    public int getY() { return (int) position.y; }
    public Vector2 getPosition() { return position; }
}
