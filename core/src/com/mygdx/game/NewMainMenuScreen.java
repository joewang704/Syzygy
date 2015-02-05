package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;

/**
 * Created by wojang on 2/3/15.
 */
public class NewMainMenuScreen implements Screen {
    final Syzygy game;
    OrthographicCamera camera;
    public NewMainMenuScreen(Syzygy game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.getStage().getBatch().begin();
        game.getFont().draw(game.getStage().getBatch(), "Tap to enter dungeon.", Constants.GAMESCREEN_WIDTH / 2 - 100,
                Constants.GAMESCREEN_HEIGHT / 2 - 100);
        game.getStage().getBatch().end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new RoomScreen(game));
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
