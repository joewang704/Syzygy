package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        //TODO dispose() of this shit nigga
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.atlas"));
        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("Enter your character's name: ", skin);
        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.add(nameLabel);
        table.add(nameText).width(Constants.GAMESCREEN_WIDTH/4);
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(Constants.GAMESCREEN_WIDTH/4);
        Syzygy.stage.addActor(table);

        Syzygy.stage.draw();
        Syzygy.stage.act();


        if (Gdx.input.isTouched()) {
            Syzygy.stage.clear();
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
