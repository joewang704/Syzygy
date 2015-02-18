package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

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
        /*equipment = new VerticalGroup();
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

        horizontalGroup.addActor(equipment);*/
    }

    /**
     * scales button to GS_HEIGHT / curHeight / 5
     * @param button to scale
     */
    private void scaleButton (ImageButton button) {
        button.getStyle().imageUp.setMinWidth(Constants.GAMESCREEN_HEIGHT / button.getHeight() / 5);
        button.getStyle().imageUp.setMinHeight(Constants.GAMESCREEN_HEIGHT / button.getHeight() / 5);
    }

    @Override
    public void dispose() {

    }
}