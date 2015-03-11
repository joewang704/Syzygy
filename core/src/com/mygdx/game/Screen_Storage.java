package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_Storage extends Screen_MacroUI {

    //Inventory
    Table storageTable;
    ScrollPane scrollPane;
    private TextButton backButt;
    private Table storageOuterTable;
    private Table leftItemTable;

    //Equipment
    VerticalGroup equipment;
    HorizontalGroup glovesAndChest;
    ImageButton head;
    ImageButton chest;
    ImageButton handLeft, handRight;
    ImageButton legs;
    ImageButton feet;
    ImageButton ring1, ring2;
    private Table rightEquipTable;

    public Screen_Storage(Syzygy game) {
        super(game);

        //Inventory

        leftItemTable = new Table(uiSkin);
        storageTable = new Table(uiSkin);
        storageOuterTable = new Table(uiSkin);

        //storageTable.padTop(30);
        backButt = new TextButton("<", uiSkin);
        backButt.setSize(Constants.GAMESCREEN_WIDTH/20, Constants.GAMESCREEN_HEIGHT/20);
        backButt.setPosition(Constants.GAMESCREEN_WIDTH/70,
                Constants.GAMESCREEN_HEIGHT - backButt.getHeight()
                - Constants.GAMESCREEN_HEIGHT/60);
        //Image testImage = new Image(ItemsXMLReader.itemArray.get(0).getItemImage());
        /*storageTable.add(testImage);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 5; j++) {
                storageTable.add("Test" + i + j).pad(10f);
            }
            storageTable.row().fillX().fillY();
        }*/
        //box space of 50
        for (int i = 0; i < 50; i++) {
            if (i % 4 == 0) {
                storageTable.row().fillX().fillY();
            }
            //fills empty space after items iterated through with placeholders
            if (i >= ItemsXMLReader.itemArray.size) {
                storageTable.add("TEST" + i).pad(10f);
            } else {
                Image img = new Image(ItemsXMLReader.itemArray.get(i).getItemImage());
                storageTable.add(img);
            }
        }
        scrollPane = new ScrollPane(storageTable);
        Syzygy.stage.addActor(leftItemTable);
        leftItemTable.setWidth(Constants.GAMESCREEN_WIDTH / 2);
        leftItemTable.setHeight(Constants.GAMESCREEN_HEIGHT);
        leftItemTable.addActor(storageOuterTable);
        storageOuterTable.setFillParent(true);
        storageOuterTable.padTop(40);
        storageOuterTable.add(scrollPane);
        leftItemTable.addActor(backButt);

        //Equipment
        rightEquipTable = new Table(uiSkin);
        rightEquipTable.setWidth(Constants.GAMESCREEN_WIDTH / 2);
        rightEquipTable.setHeight(Constants.GAMESCREEN_HEIGHT);
        rightEquipTable.setPosition(Constants.GAMESCREEN_WIDTH / 2, 0);
        equipment = new VerticalGroup();
        glovesAndChest = new HorizontalGroup();
        //make equipped item buttons with generic textures
        head = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipHead.png")))));
        chest = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipChest.png")))));
        handLeft = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipLeftHand.png")))));
        handRight = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipRightHand.png")))));
        legs = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipLegs.png")))));
        feet = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipFeet.png")))));

        //scale each button by the height of the Game, and then divide it by 5 <-- shit implementation
        scaleButton(head);
        scaleButton(chest);
        scaleButton(handLeft);
        scaleButton(handRight);
        scaleButton(legs);
        scaleButton(feet);

        glovesAndChest.addActor(handLeft);
        glovesAndChest.addActor(chest);
        glovesAndChest.addActor(handRight);

        equipment.addActor(head);
        equipment.addActor(glovesAndChest);
        equipment.addActor(legs);
        equipment.addActor(feet);

        rightEquipTable.addActor(equipment);
        equipment.setFillParent(true);
        Syzygy.stage.addActor(rightEquipTable);
    }
    private void scaleButton (ImageButton button) {
        button.getStyle().imageUp.setMinWidth(Constants.GAMESCREEN_HEIGHT / 5);//button.getHeight() / 5);
        button.getStyle().imageUp.setMinHeight(Constants.GAMESCREEN_HEIGHT / 4);//button.getHeight() / 5);
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
        leftItemTable.clear();
        leftItemTable.remove();
        rightEquipTable.clear();
        rightEquipTable.remove();
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
