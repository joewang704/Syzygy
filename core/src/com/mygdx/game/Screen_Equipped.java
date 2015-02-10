package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_Equipped extends Screen_MacroUI {

    Table storageTable;
    ScrollPane scrollPane;

    public Screen_Equipped(Syzygy game) {
        super(game);
        storageTable = new Table(uiSkin);
        scrollPane = new ScrollPane(storageTable);
        horizontalGroup.addActor(scrollPane);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 5; j++) {
                storageTable.add("Test" + i*j).pad(10f);
            }
            storageTable.row().fillX().fillY();
        }

    }
}
