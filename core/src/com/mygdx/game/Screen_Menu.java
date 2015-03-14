package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GestureRecognizer.GestureWriter;

/**
 * Created by wojang on 2/17/15.
 */
public class Screen_Menu extends Screen_MacroUI {
    private Table table;
    private TextButton dungeonButt;
    private TextButton storageButt;
    private TextButton addGesture;
    private GestureWriter gestureWriter;
    public static TextField gestureName;//probably bad since you can get null pointer if ScreenMenu has not been instantiated

    public Screen_Menu(Syzygy game) {
        super(game);
        gestureWriter = new GestureWriter();

        dungeonButt = new TextButton("Enter a dungeon", uiSkin);
        storageButt = new TextButton("Inventory", uiSkin);
        addGesture = new TextButton("Add Gestures", uiSkin);
        gestureName = new TextField("Enter Gest. Name", uiSkin);
        addGesture.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent inputEvent, float x, float y, int pointer, int button) {
                super.touchDown(inputEvent, x, y, pointer, button);
                if (addGesture.getText().charAt(0) == 'A') {
                    Syzygy.stage.addActor(gestureName);
                    Syzygy.stage.addListener(gestureWriter);
                    addGesture.setText("Stop Adding Gestures");
                } else {
                    Syzygy.stage.removeListener(gestureWriter);
                    gestureName.setText("Enter Gest. Name");
                    gestureName.remove();
                    addGesture.setText("Add Gestures");
                }
                return true;
            }
        });
        //TODO use click listeners instead of is pressed so that buttons are pressed on release
        //addGesture.addListener(new ClickListener())

        dungeonButt.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);
        storageButt.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);
        addGesture.setSize(Constants.MAINMENU_BUTTON_WIDTH * 2, Constants.MAINMENU_BUTTON_HEIGHT);

        table = new Table(uiSkin);
        table.setPosition(Constants.MAINMENU_X, Constants.MAINMENU_Y);
        table.setSize(Constants.MAINMENU_WIDTH, Constants.MAINMENU_HEIGHT);

        table.add(dungeonButt).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT/2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();
        table.add(storageButt).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT / 2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();
        table.add(addGesture).minWidth(Constants.MAINMENU_BUTTON_WIDTH * 2).prefWidth(Constants.MAINMENU_BUTTON_WIDTH * 3)
                .minHeight(Constants.MAINMENU_BUTTON_HEIGHT / 2).prefHeight(Constants.MAINMENU_BUTTON_HEIGHT).pad(3f).row();

        Syzygy.stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (dungeonButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_DungeonList(game));
        }  else if (storageButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Storage(game));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Syzygy.stage.removeListener(gestureWriter);
    }
}
