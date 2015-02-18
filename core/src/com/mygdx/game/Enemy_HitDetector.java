package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Represents a hit-detector enemy that tries to dodge bullets being fired at it
 * Created by jatin1 on 2/17/15.
 */
public class Enemy_HitDetector extends Enemy {
    public static ClassName className = ClassName.HIT_DETECTOR;
    private Sound HitDetectorTaunt;
    private Array<Bullet> bulletsShot;

    public Enemy_HitDetector(Array<Bullet> userBullets) {
        moveCtr = 0;
        moveDirection = (new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign())).nor();
        //why is it not showing up!@?#!?@#?
        enemyImage = new Texture(Gdx.files.internal("spongebob.png"));
        enemyHeight = Constants.GAMESCREEN_HEIGHT / 7.5f;
        enemyWidth = Constants.GAMESCREEN_WIDTH / 12.5f;
        setBounds(xPos, yPos, enemyWidth, enemyHeight);
        this.bulletsShot = userBullets;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        //insert normal movement thing here
        dodge(beingTargeted());
    }

    public int beingTargeted() {
        //reversing so I can pop lol
        //bulletsShot.reverse();
        // try {Bullet toDodge = bulletsShot.pop() }
        // catch java.lang.IllegalStateException and do something like attack();
        /*
        if (bullet's x direction * velocity * prediction time approaches xPos) {
            return 2; This tells the next method to change y directions if it's approaching from x.
        else if (bullet's y direction * velocity * prediction time approaches yPos) {
            return 1; Tells method to change x directions
        } else {
           return 0; Don't dodge
        }
        Because this is called in act, it checks one bullet at a time, each frame
        */
        return 0;
    }

    public void dodge(int res) {
        if (res == 1) {
            changeDirectionX();
        }
        if (res == 2) {
            changeDirectionY();
        }
        //I don't know where to put the audio files - this will be spongebob's laugh for now
        //HitDetectorTaunt = Gdx.audio.newSound(Gdx.files.internal("data/volans_call"));
        //HitDetectorTaunt.play();
    }

    public void changeDirectionX() {
        moveDirection.set(MathUtils.random() * MathUtils.randomSign(), moveDirection.y);
        moveCtr = 0;
    }

    public void changeDirectionY() {
        moveDirection.set(moveDirection.x, MathUtils.random() * MathUtils.randomSign());
        moveCtr = 0;
    }


}
