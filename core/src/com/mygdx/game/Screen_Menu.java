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
    private TextButton rmScreenTemp;

    public Screen_Menu(Syzygy game) {
        super(game);
        dungeonButt = new TextButton("Enter a dungeon", uiSkin);
        dungeonButt.setSize(Constants.RIGHTABS_WIDTH * 2, Constants.GAMESCREEN_HEIGHT/5);
        /*equipButt = new TextButton("Equip", uiSkin);
        equipButt.setSize(Constants.RIGHTABS_WIDTH * 2, Constants.GAMESCREEN_HEIGHT/5);*/
        storageButt = new TextButton("Inventory", uiSkin);
        storageButt.setSize(Constants.RIGHTABS_WIDTH * 2, Constants.GAMESCREEN_HEIGHT/5);
        rmScreenTemp = new TextButton("RoomScreen Test", uiSkin);
        rmScreenTemp.setSize(Constants.RIGHTABS_WIDTH * 2, Constants.GAMESCREEN_HEIGHT/5);

        table = new Table(uiSkin);
        table.setPosition(Constants.MAINMENU_X, Constants.MAINMENU_Y);
        table.setSize(Constants.MAINMENU_WIDTH, Constants.MAINMENU_HEIGHT);
        table.add(dungeonButt).minWidth(Constants.RIGHTABS_WIDTH * 2).prefWidth(Constants.RIGHTABS_WIDTH * 3).pad(3f).row();
        //table.add(equipButt).minWidth(Constants.RIGHTABS_WIDTH * 2).prefWidth(Constants.RIGHTABS_WIDTH * 3).pad(3f).row();
        table.add(storageButt).minWidth(Constants.RIGHTABS_WIDTH * 2).prefWidth(Constants.RIGHTABS_WIDTH * 3).pad(3f).row();
        table.add(rmScreenTemp).minWidth(Constants.RIGHTABS_WIDTH * 2).prefWidth(Constants.RIGHTABS_WIDTH * 3).pad(3f).row();

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
        } else if (rmScreenTemp.isPressed()) {
            game.stage.dispose();
            game.stage.clear();
            game.setScreen(new RoomScreen(game, 0));
        }
    }
}
