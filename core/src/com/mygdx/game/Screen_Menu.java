package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by wojang on 2/17/15.
 */
public class Screen_Menu extends Screen_MacroUI {
    private Table table;
    private TextButton dungeonButt;
    //private TextButton equipButt;
    private TextButton storageButt;
    private TextButton addGesture;

    public Screen_Menu(Syzygy game) {
        super(game);
        dungeonButt = new TextButton("Enter a dungeon", uiSkin);
        storageButt = new TextButton("Inventory", uiSkin);
        addGesture = new TextButton("Add Gesture", uiSkin);

        dungeonButt.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);
        storageButt.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);
        addGesture.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);

        table = new Table(uiSkin);
        table.setPosition(Constants.MAINMENU_X, Constants.MAINMENU_Y);
        table.setSize(Constants.MAINMENU_WIDTH, Constants.MAINMENU_HEIGHT);

        table.add(dungeonButt).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT/2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();
        table.add(storageButt).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT/2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();
        table.add(addGesture).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT/2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();

        Syzygy.stage.addActor(table);
    }
    public void render(float delta) {
        super.render(delta);
        if (dungeonButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_DungeonList(game));
        } /*else if (equipButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Equipped(game));
        }*/ else if (storageButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Storage(game));
        } else if (addGesture.isPressed()) {
            //TODO gesture reader
        }
    }
}
