package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_MacroUI implements Screen {

    protected final Syzygy game;
    protected OrthographicCamera camera;
    protected Skin uiSkin;

    protected HorizontalGroup horizontalGroup;
    private Table table;
    private TextButton dungeonButt;
    private TextButton equipButt;
    private TextButton storageButt;
    private TextButton rmScreenTemp;


    public Screen_MacroUI(Syzygy game) {
        this.game = game;
        uiSkin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.atlas"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        dungeonButt = new TextButton("pls enter a dungeon bbe!", uiSkin);
            dungeonButt.setSize(Constants.RIGHTABS_WIDTH, Constants.GAMESCREEN_HEIGHT/5);
        equipButt = new TextButton("Check your current equips bbe!", uiSkin);
            equipButt.setSize(Constants.RIGHTABS_WIDTH, Constants.GAMESCREEN_HEIGHT/5);
        storageButt = new TextButton("Check your storage bbe!", uiSkin);
            storageButt.setSize(Constants.RIGHTABS_WIDTH, Constants.GAMESCREEN_HEIGHT/5);
        rmScreenTemp = new TextButton("Go to RMSCRN bbe <3", uiSkin);
            rmScreenTemp.pad(100f);

        horizontalGroup = new HorizontalGroup();

        horizontalGroup.reverse();
        horizontalGroup.setFillParent(true);
        table = new Table(uiSkin);
        table.right();
        table.add(dungeonButt).minWidth(Constants.RIGHTABS_WIDTH).prefWidth(Constants.RIGHTABS_WIDTH * 2).row();
        table.add(equipButt).minWidth(Constants.RIGHTABS_WIDTH).prefWidth(Constants.RIGHTABS_WIDTH * 2).row();
        table.add(storageButt).minWidth(Constants.RIGHTABS_WIDTH).prefWidth(Constants.RIGHTABS_WIDTH * 2).row();
        horizontalGroup.addActor(table);
        horizontalGroup.addActor(rmScreenTemp);

        //getting NULLPOINTER EXCEPTION: set widths of all buttons in rightTabs
//        Actor[] rightTabsChildren = rightTabs.getChildren().begin();
//        System.out.print(rightTabsChildren);
//        for (int i = 0; i < rightTabsChildren.length; i++) {
//            Actor button = rightTabsChildren[i];
//            button.setWidth(Constants.RIGHTABS_WIDTH);
//            button.setHeight(Constants.GAMESCREEN_HEIGHT/5);
//        }
//        rightTabs.getChildren.end();

        Syzygy.stage.addActor(horizontalGroup);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        //TODO dispose() of this shit nigga

        Syzygy.stage.draw();
        Syzygy.stage.act();

        //TODO in child classes that use super.render(), how do we dispose of just THEIR assets?
        if (dungeonButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_DungeonList(game));
        } else if (equipButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Equipped(game));
        } else if (storageButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Storage(game));
        } else if (rmScreenTemp.isPressed()) {
            game.stage.dispose();
            game.stage.clear();
            game.setScreen(new RoomScreen(game));
        }
    }

    @Override
    public void dispose() {

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
