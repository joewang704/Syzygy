package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
 */
public class Boss_Volans extends Enemy {
    public static ClassName className = ClassName.VOLANS;
    public static Array<Bullet> volansBullets = new Array<Bullet>();
    private Sound volansCall;
    private Vector2 directionOfUser;

    public Boss_Volans(Vector2 userPos) {
        moveCtr = 0;
        //vector needs to be normalized even if its x and y are less than 1
        moveDirection = (new Vector2(MathUtils.random()*  MathUtils.randomSign(),
                MathUtils.random() * MathUtils.randomSign())).nor();
        enemyImage = new Texture(Gdx.files.internal("volans.png"));
        enemyHeight = Constants.GAMESCREEN_WIDTH / 6f;
        enemyHeight = Constants.GAMESCREEN_HEIGHT / 3.6f;
        volansCall = Gdx.audio.newSound(Gdx.files.internal("data/volans_call"));
        volansCall.play();
        directionOfUser = userPos.nor();
        addAction(Actions.sequence(Actions.delay(4.257f)));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getActions().size == 0) {
            if (MathUtils.random(1) == 0) {
                addAction(Actions.moveTo(MathUtils.random(Constants.GAMESCREEN_WIDTH - enemyWidth),
                        MathUtils.random(Constants.GAMESCREEN_HEIGHT - enemyHeight),
                        MathUtils.random(.5f, 1.5f)));
            } else {
                for (int i = 0; i < MathUtils.random(3, 5); i++) {
                    //add most recent volansBullet to stage
                    attack();
                    Syzygy.stage.addActor(volansBullets.get(volansBullets.size - 1));
                }
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
        Bullet bullet = new Bullet(
                directionOfUser.scl(210).setAngle(directionOfUser.angle() + 40),
                10f, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        volansBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }
}