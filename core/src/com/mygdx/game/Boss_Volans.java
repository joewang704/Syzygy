package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Lucas on 2/13/2015.
 * 1st Boss
 * TODO should have multiple attack methods for different attacks? should Array<Bullet> be static?
 * TODO dispose of volansCall
 */
public class Boss_Volans extends Enemy {
    public static Array<Bullet> bullets = new Array<Bullet>();
    private Sound volansCall;
    private Vector2 directionOfUser;
    private User user;

    public Boss_Volans(User user) {
        moveCtr = 0;
        //vector needs to be normalized even if its x and y are less than 1
        moveDirection = (new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign())).nor();
        enemyImage = new Texture(Gdx.files.internal("volans.png"));
        setWidth(Constants.GAMESCREEN_WIDTH / 6f);
        setHeight(Constants.GAMESCREEN_HEIGHT / 3.6f);
        volansCall = Gdx.audio.newSound(Gdx.files.internal("data/volans_call.wav"));
        this.user = user;
        isVulnerable = false;

        addAction(Actions.sequence(
            new Action() {
                @Override
                public boolean act(float delta) {
                    volansCall.play();
                    return true;
                }},
            Actions.delay(3f),
            new Action(){
                @Override
                public boolean act(float delta) {
                    volansCall.stop();
                    volansCall.dispose();
                    isVulnerable = true;
                    return true;
                }
            }));


//        setBounds(Constants.GAMESCREEN_WIDTH/2, Constants.GAMESCREEN_HEIGHT/2,
//                Constants.GAMESCREEN_WIDTH/3, Constants.GAMESCREEN_HEIGHT/5);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getActions().size == 0) {
            if (MathUtils.random(1) == 0) {
                addAction(Actions.moveTo(MathUtils.random(Constants.GAMESCREEN_WIDTH - getWidth()),
                        MathUtils.random(Constants.GAMESCREEN_HEIGHT - getHeight()),
                        MathUtils.random(1f, 2.5f)));
            } else {
                int numOfBullets = MathUtils.random(2, 4);
                for (int i = 0; i < numOfBullets; i++) {
                    //add most recent volansBullet to stage
                    attack();
                    Syzygy.stage.addActor(bullets.get(bullets.size - 1));
                }
                addAction(Actions.moveTo(MathUtils.random(Constants.GAMESCREEN_WIDTH - getWidth()),
                        MathUtils.random(Constants.GAMESCREEN_HEIGHT - getHeight()),
                        MathUtils.random(2f, 3.5f)));
            }
        } else {
            for (Action a : getActions()) {
                a.act(delta);
            }
        }
        checkBoundsCollision();
    }

    public void attack() {
        //random direction of bullet relative to user pos, with a range of 40 degrees
        directionOfUser = (new Vector2(user.getX() - getX(), user.getY() - getY())).nor();
        Bullet bullet = new Bullet(
                directionOfUser.scl(50).setAngle(directionOfUser.angle() + MathUtils.random(-30, 30)),
                10f, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        bullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }

    @Override
    public void playCall() {
        volansCall.play();
    }
}