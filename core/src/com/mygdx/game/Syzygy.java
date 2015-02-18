package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by wojang on 1/26/15.
 */
public class Syzygy extends Game {

    private BitmapFont font;
    public static Stage stage;
    private OrthographicCamera camera;

    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);
        stage = new Stage(new StretchViewport(Constants.GAMESCREEN_WIDTH,
                Constants.GAMESCREEN_HEIGHT, camera), new SpriteBatch() );
        Gdx.input.setInputProcessor(stage);
        //Use default arial font
        font = new BitmapFont();
        this.setScreen(new StartScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    public Stage getStage() { return stage; }
    public BitmapFont getFont() { return font; }
    public OrthographicCamera getCamera() { return camera; }
}
