package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_DungeonList extends Screen_MacroUI {

    private ScrollPane scrollPane;
    private Table dungeonTable;

    public Screen_DungeonList(Syzygy game) {
        super(game);
        //adding a scrollpane which contains a table to our horizontalGroup layout
        dungeonTable = new Table(uiSkin);
        scrollPane = new ScrollPane(dungeonTable);
        horizontalGroup.addActor(scrollPane);

        for (int i = 0; i < 40; i++) {
            dungeonTable.add(new TextButton("Dungeon " + i, uiSkin)).pad(10f).fillX().fillY();
            dungeonTable.row();
        }
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
    }

    @Override
    public void dispose() {
        scrollPane.clear();
        scrollPane.remove();
    }
}