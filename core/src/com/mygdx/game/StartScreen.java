package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;

/**
 * Created by wojang on 1/26/15.
 */
public class StartScreen implements Screen {
    final Syzygy game;
    OrthographicCamera camera;

    public StartScreen(final Syzygy game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.getStage().getBatch().setProjectionMatrix(camera.combined);

        game.getStage().getBatch().begin();
        game.getFont().draw(game.getStage().getBatch(), "Welcome to Syzygy! ", Constants.GAMESCREEN_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2);
        game.getFont().draw(game.getStage().getBatch(), "Tap anywhere to begin!", Constants.GAMESCREEN_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2 + 100);
        game.getStage().getBatch().end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Screen_Menu(game));
            dispose();
        }
    }

    @Override
    public void dispose() {
        game.getFont().dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int a, int b) {

    }

    @Override
    public void show() {

    }
}
