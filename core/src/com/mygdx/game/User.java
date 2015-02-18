package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class User extends Actor {

    public static Array<Bullet> userBullets = new Array();
    private Texture userImg;
    private long lastShotTime;
    private int atkSpeed;
    private float moveSpeed;
    private Touchpad movePad;

    public User(Touchpad move, float x, float y, float width, float height) {
        this(move);
        setBounds(x, y, width, height);
    }

    public User(Touchpad move) {
        movePad = move;
        atkSpeed = 300000000;
        moveSpeed = 10;
        userImg = new Texture(Gdx.files.internal("wizard.png"));
    }
    /*
    * Returns direction user is moving in.
    * 1 if UP, 2 if LEFT, 3 if RIGHT, 4 if DOWN
    * 0 if not moving or directly diagonal
     */
    public int getDir() {
        double angle = Math.atan2(movePad.getKnobPercentY(), movePad.getKnobPercentX());
        if (angle < Math.PI / 4 || angle > 7 * Math.PI / 4) {
            return PortalPos.RIGHT.ordinal(); //Right direction
        } else if (angle > Math.PI / 4 && angle < 3 * Math.PI / 4) {
            return PortalPos.UP.ordinal(); //Upward direction
        } else if (angle > 3* Math.PI / 4 && angle < 5 * Math.PI / 4) {
            return PortalPos.LEFT.ordinal();
        } else if (angle > 5* Math.PI / 4 && angle < 2 * Math.PI) {
            return PortalPos.DOWN.ordinal();
        } else {
            return 0;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //move();
        //Why does getKnobPercent() work but not getKnob()?
        move(new Vector2(movePad.getKnobPercentX(), movePad.getKnobPercentY()));
        checkScreenBoundsCollision();
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(userImg, getX(), getY(), getWidth(), getHeight());
    }

    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }

    public void move(Vector2 direction) {
        setX(getX() + direction.x*moveSpeed);
        setY(getY() + direction.y*moveSpeed);
    }

    public void checkScreenBoundsCollision() {
        if (getX() < 0) setX(0);
        if (getY() < 0) setY(0);
        if (getX() > Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH) {
            setX(Constants.GAMESCREEN_WIDTH - Constants.USER_WIDTH);
        }
        if (getY() > Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT){
            setY(Constants.GAMESCREEN_HEIGHT - Constants.USER_HEIGHT);
        }
    }

    //70 and 3 are somewhat arbitrary, should be changed
    public Bullet fireBullet(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70).scl(3), speed, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH/2,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT/2,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        userBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
        return bullet;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    public int getAtkSpeed() {
        return atkSpeed;
    }
}
