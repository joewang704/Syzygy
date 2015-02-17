package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by Lucas on 2/6/2015.
 */
public class Joystick extends Touchpad {

    public Joystick(float deadZoneRadius, Touchpad.TouchpadStyle style) {
        super(deadZoneRadius, style);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        toFront();
    }

    //TODO not using parentAlpha means no transparency will be applied
    //TODO correctly implement cheap fix for knob placement
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}

