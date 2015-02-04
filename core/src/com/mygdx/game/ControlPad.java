package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lucas on 1/31/2015.
 */
public class ControlPad extends Circle implements InputProcessor {
    private Texture ctrlImg;
    private int inputPointer;
    private Vector2 directionVector;

    //TODO MASSIVE problem reading input where you don't need to press on a pad for it to register?
    public ControlPad(String textureName, float x, float y, float radius) {
        ctrlImg = new Texture(Gdx.files.internal(textureName));
        this.x = x;
        this.y = y;
        this.radius = radius;
        directionVector = new Vector2(0, 0);
    }

    //screenX & Y are the touchPos
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = (int) (Constants.GAMESCREEN_HEIGHT - screenY);
        if (pointer < 3 && this.contains(screenX, screenY)) {
            inputPointer = pointer;
            RoomScreen.touchMap.get(pointer).x = screenX;
            RoomScreen.touchMap.get(pointer).y = screenY;
            RoomScreen.touchMap.get(pointer).touched = true;
            setDirectionVector(RoomScreen.touchMap.get(pointer));
        }
        return false;
        /*if (this.contains(screenX, screenY)) {
            directionVector.set(screenX, screenY).sub(x, y);
            directionVector.scl(1/8f);
            touchDownOnPad = true;
        }
        return false; */
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = (int) (Constants.GAMESCREEN_HEIGHT - screenY);
        if (pointer == inputPointer) {
            RoomScreen.touchMap.get(pointer).x = screenX;
            RoomScreen.touchMap.get(pointer).y = screenY;
            RoomScreen.touchMap.get(pointer).touched = true;
            if (this.contains(screenX, screenY)) {
                setDirectionVector(RoomScreen.touchMap.get(pointer));
                directionVector.scl(1/8f);
            } else {
                setDirectionVector(RoomScreen.touchMap.get(pointer));
                directionVector.nor().scl(radius/8f);
            }
        }
        return false;
        /*if (this.contains(screenX, screenY) && touchDownOnPad) {
            directionVector.set(screenX, screenY).sub(x, y).scl(1/8f);
        } else if (touchDownOnPad) {
            directionVector.set(screenX, screenY).sub(x, y);
            directionVector.nor().scl(radius/8f);
        }
        return false;*/
    }

    //TODO should this always return true?
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == inputPointer) {
            RoomScreen.touchMap.get(pointer).x = 0;
            RoomScreen.touchMap.get(pointer).y = 0;
            RoomScreen.touchMap.get(pointer).touched = false;
            setDirectionVector(RoomScreen.touchMap.get(pointer));
        }
        return false;
        /*directionVector.set(0, 0);
        touchDownOnPad = false;
        return false;*/
    }

    public void setDirectionVector(TouchInfo info) {
        directionVector.set(RoomScreen.touchMap.get(inputPointer).x,
                RoomScreen.touchMap.get(inputPointer).y);
        directionVector.sub(x, y);
    }
    public boolean keyUp(int keyCode) { return false; }
    public boolean keyTyped(char character) { return false; }
    public boolean keyDown(int keyCode) { return false; }
    public boolean mouseMoved(int screenX, int screenY) { return false; }
    public boolean scrolled(int amount) { return false; }

    public Texture getTexture() { return ctrlImg; }
    public Vector2 getDirectionVector() { return directionVector; }
    public int getInputPointer() { return inputPointer; }
}
