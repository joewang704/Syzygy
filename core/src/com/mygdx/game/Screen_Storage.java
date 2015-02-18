package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_Storage extends Screen_MacroUI {

    Table storageTable;
    ScrollPane scrollPane;
    private TextButton backButt;
    protected Table containerTable;

    public Screen_Storage(Syzygy game) {
        super(game);
        containerTable = new Table(uiSkin);
        storageTable = new Table(uiSkin);
        backButt = new TextButton("<", uiSkin);
        backButt.setSize(Constants.GAMESCREEN_WIDTH/20, Constants.GAMESCREEN_HEIGHT/20);
        backButt.setPosition(Constants.GAMESCREEN_WIDTH/70,
                Constants.GAMESCREEN_HEIGHT - backButt.getHeight()
                - Constants.GAMESCREEN_HEIGHT/60);
        containerTable.addActor(backButt);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 5; j++) {
                storageTable.add("Test" + i + j).pad(10f);
            }
            storageTable.row().fillX().fillY();
        }
        scrollPane = new ScrollPane(storageTable);
        Syzygy.stage.addActor(scrollPane);
        scrollPane.setFillParent(true);
        Syzygy.stage.addActor(containerTable);
        containerTable.setFillParent(true);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        if (backButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Menu(game));
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void dispose() {
        scrollPane.clear();
        scrollPane.remove();
        containerTable.clear();
        containerTable.remove();
    }
    @Override
    public void hide() {

    }

    @Override
    public void resize(int x, int y) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
