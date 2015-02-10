package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;

import java.util.NoSuchElementException;

/**
 * Created by wojang on 2/10/15.
 */
public class Portal extends Actor {
    private Texture portalImg;
    private Room currentRoom, nextRoom;
    public Portal(Room currentRoom) {
        this(currentRoom, null);
    }
    public Portal(Room currentRoom, Room nextRoom) {
        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        portalImg = new Texture(Gdx.files.internal("portal.png"));
    }
    public Portal(Room currentRoom, Room nextRoom,
                  float x, float y, float width, float height) {
        this(currentRoom, nextRoom);
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
    }
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public Room getNextRoom() {
        if (nextRoom == null) {
            throw new NoSuchElementException("Portal's next room has not been set.");
        }
        return nextRoom;
    }
    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }
    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(portalImg, getX(), getY(), getWidth(), getHeight());
    }
}
