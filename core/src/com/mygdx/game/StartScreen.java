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
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Syzygy! ", Constants.GAMESCREEN_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2);
        game.font.draw(game.batch, "Tap anywhere to begin!", Constants.GAMESCREEN_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT / 2 + 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new NewMainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void dispose() {
        game.font.dispose();
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
