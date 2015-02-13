package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;

import java.util.NoSuchElementException;

/**
 * Created by wojang on 2/10/15.
 * TODO Making left right upper and lower portal classes extend this would significantly abstract away code
 */
public class Portal extends Actor {

    private Texture portalImg;
    private Room currentRoom;
    private Room nextRoom;
    private PortalPos portalPos;

    public Portal(Room currentRoom, Room nextRoom, PortalPos portalPos) {
        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        this.portalPos = portalPos;
        portalImg = new Texture(Gdx.files.internal("portal.png"));
        setVisible(false);
    }

    public Portal(Room currentRoom, Room nextRoom, PortalPos portalPos,
                  float x, float y, float width, float height) {
        this(currentRoom, nextRoom, portalPos);
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
    }

    public Portal(Room currentRoom, PortalPos portalPos) {
        this.currentRoom = currentRoom;
        this.portalPos = portalPos;
        portalImg = new Texture(Gdx.files.internal("portal.png"));
        setVisible(false);
    }

    public Portal(Room currentRoom, PortalPos portalPos,
                  float x, float y, float width, float height) {
        this(currentRoom, portalPos);
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
    }

    public Room getCurrentRoom() { return currentRoom; }
    public PortalPos getPortalPos() { return portalPos; }
    public Room getNextRoom() {
        if (nextRoom == null) {
            throw new NoSuchElementException("Portal's next room has not been set.");
        }
        return nextRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
        setVisible(true);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(portalImg, getX(), getY(), getWidth(), getHeight());
    }
}
