package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Lucas on 3/7/2015.
 */
public class ChangeScreenPortal extends Actor {

    private Texture image;

    public ChangeScreenPortal(FileHandle image) {
        this.image = new Texture(image);
        setBounds(Constants.GAMESCREEN_WIDTH/2 - Constants.HOME_PORTAL_WIDTH/2,
                Constants.GAMESCREEN_HEIGHT/2 - Constants.HOME_PORTAL_HEIGHT/2,
                Constants.HOME_PORTAL_WIDTH,
                Constants.HOME_PORTAL_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY(), getWidth(), getHeight());
    }
}
