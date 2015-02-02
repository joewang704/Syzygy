package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by wojang on 1/26/15.
 */
public class Syzygy extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Stage stage;
    public Viewport viewport;
    public OrthographicCamera camera;

    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);
        viewport = new StretchViewport(Constants.GAMESCREEN_WIDTH,
            Constants.GAMESCREEN_HEIGHT, camera);
        stage = new Stage(viewport, batch);
        //Use default arial font
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
    }
}
