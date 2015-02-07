package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by Lucas on 2/6/2015.
 */
public class Joystick extends Touchpad {

    public Joystick(float deadZoneRadius, Touchpad.TouchpadStyle style) {
        super(deadZoneRadius, style);
    }
}

