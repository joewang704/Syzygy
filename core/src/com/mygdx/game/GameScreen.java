package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.security.pkcs11.wrapper.CK_PBE_PARAMS;

public class GameScreen implements Screen {
    //why does Name extend Game? We should change it its not very logical
    //does Name need to be passed to GameScreen at all? looks like we don't really use it.
    //should Name be final?
    final Syzygy game;

    //technical stuff: Input Processor
    private InputMultiplexer inputMultiplexer;
    public static Map<Integer, TouchInfo> touchMap;

    //Shapes
    public User user;
    public Array<Enemy> enemies;

    //Utility
    private long lastSpawnTime;

    //Sprites
    Texture img, bImg, slimeImg;
    ControlPad ctrlPadMove, ctrlPadShoot;


    public GameScreen(final Syzygy game) {
        this.game = game;
        img = new Texture(Gdx.files.internal("wizard.png"));
        bImg = new Texture(Gdx.files.internal("soccer.png"));
        slimeImg = new Texture(Gdx.files.internal("cuteSlime64.png"));

        ctrlPadMove = new ControlPad("controllerpad1.png",
                90 + Constants.CP_PADDING, 90 + Constants.CP_PADDING, 90);
        ctrlPadShoot = new ControlPad("controllerpad1.png",
                Constants.GAMESCREEN_WIDTH - 90 - Constants.CP_PADDING, 90 + Constants.CP_PADDING, 90);

        inputMultiplexer = new InputMultiplexer(ctrlPadMove, ctrlPadShoot);
        Gdx.input.setInputProcessor(inputMultiplexer);

        touchMap = new HashMap<Integer, TouchInfo>();
        for (int i = 0; i < 3; i++) {
            touchMap.put(i, new TouchInfo());
        }

        spawnUser(Constants.GAMESCREEN_WIDTH / 2 - Constants.USER_WIDTH / 2,
                0, Constants.USER_WIDTH, Constants.USER_HEIGHT);
        enemies = new Array<Enemy>();
    }

    @Override
    public void render(float delta) {
        //set background color to green
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update camera (should be done after each change in camera)
        game.stage.getViewport().getCamera().update();

        //render elements
        game.stage.getBatch().setProjectionMatrix(game.stage.getViewport().getCamera().combined);
        game.stage.getBatch().begin();
        //game.stage.getBatch().draw(img, user.x, user.y);
        game.stage.getBatch().draw(img, user.x, user.y, user.width, user.height);

        //draw all bullets and enemies from their respective arrays
        for (Rectangle bullet: User.userBullets) {
            game.stage.getBatch().draw(bImg, bullet.x, bullet.y, bullet.width, bullet.height);
        }

        for (Enemy enemy: enemies) {
            game.stage.getBatch().draw(slimeImg, enemy.x, enemy.y, enemy.width, enemy.height);
        }

        //draw ControlPads with padding equal to 1/8 of GS Height & Width
        //---->When using GS Height & Width to draw padding it stretches textures.
        game.stage.getBatch().draw(ctrlPadMove.getTexture(),
                Constants.CP_PADDING, Constants.CP_PADDING);
        game.stage.getBatch().draw(ctrlPadShoot.getTexture(),
                Constants.GAMESCREEN_WIDTH - 180 - Constants.CP_PADDING, Constants.CP_PADDING);
        game.stage.getBatch().end();

        //game.stage.draw();

        //WASD moves user
        user.move();
        //ControlPad moves user, new Vector passed to fireBullet to avoid
        //continuously pointing to direction vector
        //SPEED PARAM IS ARBITRARY
        if (GameScreen.touchMap.get(ctrlPadMove.getInputPointer()).touched) {
            user.move(ctrlPadMove.getDirectionVector());
        }
       //block of code to fire bullets is stretching the controlPads...
        if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
            //checks to see if ctrlPadShoot is depressed
            if (GameScreen.touchMap.get(ctrlPadShoot.getInputPointer()).touched) {
                user.fireBullet(new Vector2(ctrlPadShoot.getDirectionVector()), 2);
            }
        }

        //spawn slimes
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) spawnSlime();

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
        Collisions.removeBullets(); //removes bullets that have flown off the screen
        Collisions.enemyHits(enemies); //removes enemies that have been hit by bullets
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

        //move all enemies
        for (Enemy enemy: enemies) {
            enemy.move();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.stage.getViewport().update(width, height, true);
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
        img.dispose();
        bImg.dispose();
    }

    //helper methods
    private void spawnUser(float x, float y, float width, float height) {
        user = new User();
        user.x = x;
        user.y = y;
        user.width = width;
        user.height = height;
    }

    private void spawnSlime() {
        Enemy e = new Enemy();
        e.x = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3);
        e.y = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3);
        e.width = Constants.SLIME_ENEMY_WIDTH;
        e.height = Constants.SLIME_ENEMY_HEIGHT;
        enemies.add(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }
}
