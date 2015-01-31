package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lucas on 1/31/2015.
 */
public class ControlPad extends Circle implements InputProcessor {
    private Texture ctrlImg;
    private Vector2 center;
    private Vector2 directionVector;

    public ControlPad(String textureName, float x, float y, float radius) {
        ctrlImg = new Texture(Gdx.files.internal(textureName));
        this.x = x;
        this.y = y;
        this.radius = radius;
        center = new Vector2(x + radius/2, y + radius/2);
        directionVector = new Vector2(0, 0);
    }

    //screenX & Y are the touchPos
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = (int) (Constants.GAMESCREEN_HEIGHT - screenY);
        if (this.contains(screenX, screenY)) {
            directionVector.set(screenX, screenY).sub(center);
            directionVector.scl(1/64);
            return true;
        }
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = (int) (Constants.GAMESCREEN_HEIGHT - screenY);
        if (this.contains(screenX, screenY)) {
            directionVector.set(screenX, screenY).sub(center);
        } else {
            directionVector.nor();
            directionVector.scl(radius/64);
        }
        return true;
    }

    //TODO should this always return true?
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        directionVector.set(0, 0);
        return true;
    }

    public boolean keyUp(int keyCode) { return false; }
    public boolean keyTyped(char character) { return false; }
    public boolean keyDown(int keyCode) { return false; }
    public boolean mouseMoved(int screenX, int screenY) { return false; }
    public boolean scrolled(int amount) { return false; }

    public Texture getTexture() {
        return ctrlImg;
    }
    public Vector2 getDirectionVector() { return directionVector; }
}
