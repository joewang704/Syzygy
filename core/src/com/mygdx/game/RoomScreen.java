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
    private Touchpad joystickMove;
    private Joystick joystickFire;
    private TextureRegionDrawable joystickImg;
    private TextureRegionDrawable joystickKnob;

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
        game.getStage().getBatch().begin();
//        //game.getStage().getBatch().draw(userImg, user.x, user.y);
//        game.getStage().getBatch().draw(userImg, user.getX(), user.getY(), user.getWidth(), user.getHeight());
//
//        //draw all bullets and enemies from their respective arrays
//        for (Rectangle bullet: User.userBullets) {
//            game.getStage().getBatch().draw(bulletImg, bullet.x, bullet.y, bullet.width, bullet.height);
//        }
//
//        for (Enemy enemy: enemies) {
//            game.getStage().getBatch().draw(slimeImg, enemy.x, enemy.y, enemy.width, enemy.height);
//        }

        game.getStage().getBatch().end();
        game.getStage().draw();
        game.getStage().act(delta);
        if (joystickFire.getKnobPercentX() != 0 || joystickFire.getKnobPercentY() != 0) {
            if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
                game.getStage().addActor(user.fireBullet(new Vector2(joystickFire.getKnobX() - 90f, joystickFire.getKnobY() - 90f), 5f));
            }
        }
        //WASD moves user
        //spawn slimes
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) spawnSlime();

//        if(User.userBullets.get(User.userBullets.size - 1) == );

        //moves bullets, removes bullets off screen
        /*Iterator<Bullet> uIter = User.userBullets.iterator();
        while (uIter.hasNext()) {
            Bullet bullet = uIter.next();
            bullet.y += 200 * bullet.velocity.y * Gdx.graphics.getDeltaTime();
            bullet.x += 200 * bullet.velocity.x * Gdx.graphics.getDeltaTime();
            if (bullet.y + Constants.BULLET_HEIGHT < 0 || bullet.y > Constants.GAMESCREEN_HEIGHT) {
                uIter.remove();
            }
        }*/
        Collisions.enemyHits(enemies);
        Collisions.removeBullets(); //removes bullets that have flown off the screen
        //Collisions.enemyHits(enemies); //removes enemies that have been hit by bullets
        //iterate through bullets and check if they collide with an enemy.
        /*for (Bullet bullet: User.userBullets) {
            Iterator<Enemy> eIter = enemies.iterator();
            while (eIter.hasNext()) {
                if (bullet.overlaps(eIter.next())) {
                    eIter.remove();
                    User.userBullets.removeValue(bullet, true);
                }
            }
        }*/
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

    public void createJoysticks() {
        joystickImg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpad1.png"))));
        joystickKnob = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpadKnob40.png"))));
        joystickMove = new Joystick(13f, new Touchpad.TouchpadStyle(joystickImg, joystickKnob));
        joystickFire = new Joystick(13f, new Touchpad.TouchpadStyle(joystickImg, joystickKnob));

        joystickMove.setPosition(Constants.GAMESCREEN_WIDTH/40, Constants.GAMESCREEN_WIDTH/40);
        joystickFire.setPosition(
                Constants.GAMESCREEN_WIDTH - Constants.GAMESCREEN_WIDTH/40 - joystickFire.getWidth(),
                Constants.GAMESCREEN_WIDTH/40);

        game.getStage().addActor(joystickMove);
        game.getStage().addActor(joystickFire);
    }
}
