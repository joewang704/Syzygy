package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_DungeonList extends Screen_MacroUI {

    private ScrollPane scrollPane;
    private Table dungeonTable;

    public Screen_DungeonList(Syzygy game) {
        super(game);
        dungeonTable = new Table(uiSkin);
        scrollPane = new ScrollPane(dungeonTable);
        horizontalGroup.addActor(scrollPane);

        for (int i = 0; i < 100; i++) {
            dungeonTable.add("Test " + i).pad(10f).fillX().fillY();
            dungeonTable.row();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {

    }
}
