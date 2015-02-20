package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private TextButton backButt;

    public Screen_Equipped(Syzygy game) {
        super(game);
        equipment = new VerticalGroup();
        glovesAndChest = new HorizontalGroup();

        //draw back button at top left corner
        backButt = new TextButton("<", uiSkin);
        backButt.setSize(Constants.GAMESCREEN_WIDTH/20, Constants.GAMESCREEN_HEIGHT/20);
        backButt.setPosition(Constants.GAMESCREEN_WIDTH/70,
                Constants.GAMESCREEN_HEIGHT - backButt.getHeight()
                        - Constants.GAMESCREEN_HEIGHT/60);
        Syzygy.stage.addActor(backButt);

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

        Syzygy.stage.addActor(equipment);
        equipment.setFillParent(true);
    }

    /**
     * scales button to GS_HEIGHT / curHeight / 5
     * Joe: the problem was that curHeight isnt set yet, setMinHeight sets the height
     * and before that the height is undefined and shapes around the image
     * @param button to scale
     */
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
    public void dispose() {
        backButt.clear();
        backButt.remove();
        equipment.clear();
        equipment.remove();
    }
}