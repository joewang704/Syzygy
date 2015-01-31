package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lucas on 1/31/2015.
 */
public class ControlPad extends Circle {
    private Texture ctrlImg;
    private Vector2 center;

    public ControlPad(String textureName, float x, float y, float radius) {
        ctrlImg = new Texture(Gdx.files.internal(textureName));
        this.x = x;
        this.y = y;
        this.radius = radius;
        center = new Vector2(x + radius / 2, y + radius / 2);
    }

    //TODO Fix two fingers on a single control pad problem
    public Vector2 checkForInput() {
        for (int i = 0; i < 2; i++) {
            Vector2 usageVector = new Vector2();
            Vector2 touchPos = new Vector2(Gdx.input.getX(i), Gdx.input.getY(i));
            if (Gdx.input.isTouched(i) && this.contains(touchPos)) {
                usageVector.set(touchPos).sub(center);
                return usageVector;
            }
        }
        return null;
    }

    public Texture getTexture() {
        return ctrlImg;
    }
}
