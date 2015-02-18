package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_DungeonList extends Screen_MacroUI {

    private ScrollPane scrollPane;
    private Table dungeonTable;
    private TextButton backButt;

    public Screen_DungeonList(Syzygy game) {
        super(game);
        //adding a scrollpane which contains a table to our horizontalGroup layout
        dungeonTable = new Table(uiSkin);
        backButt = new TextButton("<", uiSkin);
        backButt.setSize(Constants.GAMESCREEN_WIDTH/20, Constants.GAMESCREEN_HEIGHT/20);
        backButt.setPosition(Constants.GAMESCREEN_WIDTH/70,
                Constants.GAMESCREEN_HEIGHT - backButt.getHeight()
                        - Constants.GAMESCREEN_HEIGHT/60);
        scrollPane = new ScrollPane(dungeonTable);
        Syzygy.stage.addActor(scrollPane);
        scrollPane.setFillParent(true);
        Syzygy.stage.addActor(backButt);

        for (int i = 0; i < 40; i++) {
            dungeonTable.add(new TextButton("Dungeon " + i, uiSkin)).pad(10f).fillX().fillY();
            dungeonTable.row();
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        super.render(delta);
        //render checks if each button is touched and enters dungeon
        TextButton txtButton;
        for (Actor button: dungeonTable.getChildren()) {
            txtButton = (TextButton) button;
            if(txtButton.isPressed()) {
                Syzygy.stage.dispose();
                Syzygy.stage.clear();
                game.setScreen(new RoomScreen(game));
            }
        }
        if (backButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Menu(game));
        }
    }

    @Override
    public void dispose() {
        scrollPane.clear();
        scrollPane.remove();
        backButt.clear();
        backButt.remove();
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
