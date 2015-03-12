package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_Storage extends Screen_MacroUI {

    //Inventory
    private Table storageTable;
    private ScrollPane scrollPane;
    private TextButton backButt;
    private Table storageOuterTable;
    private Table leftItemTable;

    //Equipment
    private VerticalGroup equipment;
    private HorizontalGroup glovesAndChest;
    private Image head;
    private ImageButton chest;
    private ImageButton handLeft, handRight;
    private ImageButton legs;
    private ImageButton feet;
    private ImageButton ring1, ring2;
    private Table rightEquipTable;

    //used for pop up item info
    private Button closeButt, equipButt;
    private Window itemInfo;
    private Item currentOpenedItem;

    //used for equipment
    private Item equippedHeadItem;
    private Item equippedHandLeftItem;
    private Item equippedHandRightItem;
    private Item equippedChestItem;
    private Item equippedLegsItem;
    private Item equippedFeetItem;

    public Screen_Storage(Syzygy game) {
        super(game);

        //Inventory

        leftItemTable = new Table(uiSkin);
        storageTable = new Table(uiSkin);
        storageOuterTable = new Table(uiSkin);

        //Item Information Pop Up
        closeButt = new TextButton("Close", uiSkin);
        equipButt = new TextButton("Equip", uiSkin);

        //storageTable.padTop(30);
        backButt = new TextButton("<", uiSkin);
        backButt.setSize(Constants.GAMESCREEN_WIDTH/20, Constants.GAMESCREEN_HEIGHT/20);
        backButt.setPosition(Constants.GAMESCREEN_WIDTH/70,
                Constants.GAMESCREEN_HEIGHT - backButt.getHeight()
                - Constants.GAMESCREEN_HEIGHT/60);

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
                img.addListener(new ItemClickListener(ItemsXMLReader.itemArray.get(i),
                        leftItemTable));
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
        /*head = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("equipHead.png")))));*/
        head = new Image(new Texture(Gdx.files.internal("equipHead.png")));
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
        //scaleButton(head);
        head.getDrawable().setMinWidth(Constants.GAMESCREEN_WIDTH / 5);
        head.getDrawable().setMinHeight(Constants.GAMESCREEN_HEIGHT / 4);
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
    private void scaleButton(ImageButton button) {
        button.getStyle().imageUp.setMinWidth(Constants.GAMESCREEN_WIDTH / 7);//button.getHeight() / 5);
        button.getStyle().imageUp.setMinHeight(Constants.GAMESCREEN_HEIGHT / 4);//button.getHeight() / 5);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        if (backButt.isPressed()) {
            game.getScreen().dispose();
            game.setScreen(new Screen_Menu(game));
        }
        if (closeButt.isPressed()) {
            itemInfo.clear();
            itemInfo.remove();
        }
        if (equipButt.isPressed()) {
            //equip item

            currentOpenedItem.setEquipped(true);
            //unequip previously equipped item
            //NOTE: CURRENTOPENEDITEM GETS CLICKED MANY TIMES IF YOU ACCIDENTALLY HOLD
            if(equippedHeadItem != null && equippedHeadItem != currentOpenedItem) {
                equippedHeadItem.setEquipped(false);
            }
            head.setDrawable(new SpriteDrawable(new Sprite(currentOpenedItem.getItemImage())));
            equippedHeadItem = currentOpenedItem;
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

    private class ItemClickListener extends ClickListener {
        Item item;
        String atkString, defString, spdString, itemName;
        Table table;
        public ItemClickListener(Item item, Table table) {
            this.item = item;
            itemName = item.getName();
            atkString = "Attack: " + item.getAtk();
            defString = "Defense: " + item.getDef();
            spdString = "Speed: " + item.getSpd();
            this.table = table;
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer,
                                 int button) {
            //create new popup screen with item info

            //if previous popup screen exists delete it
            if (itemInfo != null) {
                itemInfo.clear();
                itemInfo.remove();
            }
            itemInfo = new Window(itemName, uiSkin);
            Label atkLabel = new Label(atkString, uiSkin);
            Label defLabel = new Label(defString, uiSkin);
            Label spdLabel = new Label(spdString, uiSkin);
            itemInfo.add(atkLabel);
            itemInfo.row();
            itemInfo.add(defLabel);
            itemInfo.row();
            itemInfo.add(spdLabel);
            itemInfo.row();
            itemInfo.add(equipButt);
            itemInfo.row();
            itemInfo.add(closeButt);
            itemInfo.setPosition(Constants.GAMESCREEN_WIDTH / 4 - 10,
                    Constants.GAMESCREEN_HEIGHT / 2 - 10);
            table.add(itemInfo);
            currentOpenedItem = item;
            return false;
        }
    }
}
