package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_MacroUI implements Screen {

    protected final Syzygy game;
    protected OrthographicCamera camera;
    protected Skin uiSkin;

    public Screen_MacroUI(Syzygy game) {
        this.game = game;
        uiSkin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.atlas"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        Syzygy.stage.draw();
        Syzygy.stage.act();
    }

    @Override
    public void dispose() {
        game.stage.dispose();
        uiSkin.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        game.getStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
