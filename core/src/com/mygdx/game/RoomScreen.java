package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class RoomScreen implements Screen {

    final Syzygy game;

    //Shapes
    private User user;
    private Array<Enemy> enemies;
    private Joystick joystickMove;
    private Joystick joystickFire;

    //Utility
    private long lastSpawnTime;

    //Sprites now contained within respective classes

    public RoomScreen(final Syzygy game) {
        this.game = game;
        Gdx.input.setInputProcessor(game.getStage());
        createJoysticks();
        spawnUser(Constants.GAMESCREEN_WIDTH / 2 - Constants.USER_WIDTH / 2,
                0, Constants.USER_WIDTH, Constants.USER_HEIGHT);
        enemies = new Array<Enemy>();
        game.getStage().addActor(user);
    }

    @Override
    public void render(float delta) {
        //set background color to green
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update camera (should be done after each change in camera)
        game.getStage().getViewport().getCamera().update();

        //render elements
        game.getStage().getBatch().setProjectionMatrix(
                game.getStage().getViewport().getCamera().combined);

        game.getStage().draw();
        game.getStage().act(delta);

        //Bullet firing
        if (joystickFire.getKnobPercentX() != 0 || joystickFire.getKnobPercentY() != 0) {
            if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
                game.getStage().addActor(user.fireBullet(new Vector2(
                        joystickFire.getKnobX() - joystickFire.getWidth()/2,
                        joystickFire.getKnobY() - joystickFire.getHeight()/2),
                        5f));
            }
        }

        //spawn slimes
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) spawnSlime();

        Collisions.enemyHits(enemies);
        Collisions.removeBullets();
    }

    @Override
    public void resize(int width, int height) {
        game.getStage().getViewport().update(width, height, true);
        //Constants.BULLET_HEIGHT = height / 40;
        //Constants.BULLET_WIDTH = width / 40;
        //Constants.USER_HEIGHT = height / 80;
        //Constants.USER_WIDTH = width / 80;
        //System.out.println(Constants.BULLET_HEIGHT+" "+Constants.BULLET_WIDTH);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        //dispose of all native resources
    }

    //helper methods
    private void spawnUser(float x, float y, float width, float height) {
        user = new User(joystickMove, joystickFire, x, y, width, height);
    }

    private void spawnSlime() {
        float xPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3);
        float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3);
        float width = Constants.SLIME_ENEMY_WIDTH;
        float height = Constants.SLIME_ENEMY_HEIGHT;
        Enemy e = new Enemy(xPos, yPos, width, height);
        enemies.add(e);
        game.getStage().addActor(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    //Joystick Position is entirely relative to GS_WIDTH
    //Joystick width & height entirely relative to GS_HEIGHT
    //sambady pls halp!
    //OR is it good because regardless of screen size, joysticks will always be circular instead of stretched?idk
    public void createJoysticks() {
        TextureRegionDrawable joystickImg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpad1.png"))));
        TextureRegionDrawable joystickKnob = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpadKnob100.png"))));
        joystickMove = new Joystick(20f, new Touchpad.TouchpadStyle(joystickImg, joystickKnob));
        joystickFire = new Joystick(0f, new Touchpad.TouchpadStyle(joystickImg, joystickKnob));

        joystickMove.setWidth(Constants.GAMESCREEN_HEIGHT/2f);
        joystickMove.setHeight(Constants.GAMESCREEN_HEIGHT/2f);
        joystickFire.setWidth(Constants.GAMESCREEN_HEIGHT/2f);
        joystickFire.setHeight(Constants.GAMESCREEN_HEIGHT/2f);

        joystickMove.setPosition(Constants.GAMESCREEN_WIDTH/40, Constants.GAMESCREEN_WIDTH/40);
        joystickFire.setPosition(
                Constants.GAMESCREEN_WIDTH - Constants.GAMESCREEN_WIDTH/40 - joystickFire.getWidth(),
                Constants.GAMESCREEN_WIDTH/40);

        game.getStage().addActor(joystickMove);
        game.getStage().addActor(joystickFire);
    }
}
