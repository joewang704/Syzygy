package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Lucas on 2/9/2015.
 */
public class Screen_Equipped extends Screen_MacroUI {

    VerticalGroup equipment;
    HorizontalGroup glovesAndChest;

    ImageButton head;
    ImageButton chest;
    ImageButton handLeft, handRight;
    ImageButton legs;
    ImageButton feet;
    ImageButton ring1, ring2;

    public Screen_Equipped(Syzygy game) {
        super(game);
        equipment = new VerticalGroup();
        glovesAndChest = new HorizontalGroup();
        head = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipHead.png")))));
        chest = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipChest.png")))));
        handLeft = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipLeftHand.png")))));
        handRight = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipRightHand.png")))));
        legs = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipLegs.png")))));
        feet = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipFeet.png")))));

        glovesAndChest.addActor(handLeft);
        glovesAndChest.addActor(chest);
        glovesAndChest.addActor(handRight);

        equipment.addActor(head);
        equipment.addActor(glovesAndChest);
        equipment.addActor(legs);
        equipment.addActor(feet);

        horizontalGroup.addActor(equipment);
    }

    @Override
    public void dispose() {

    }
}
