package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Name game;

    //technical stuff: Camera, Input Processor
    OrthographicCamera camera;
    InputMultiplexer inputMultiplexer;

    //Shapes
    public User user;
    Array<Enemy> enemies;

    //Utility
    private long lastSpawnTime;

    //Sprites
    SpriteBatch batch;
    Texture img, bImg, slimeImg;
    ControlPad ctrlPadMove, ctrlPadShoot;

    float xScalingFactor;
    float yScalingFactor;


    public GameScreen(final Name gam) {
        this.game = gam;
        img = new Texture(Gdx.files.internal("wizard.png"));
        bImg = new Texture(Gdx.files.internal("soccer.png"));
        slimeImg = new Texture(Gdx.files.internal("cuteSlime64.png"));
        ctrlPadMove = new ControlPad("controllerpad.png.jpg", 0, 0, 128);
        ctrlPadShoot = new ControlPad("controllerpad.png.jpg", Constants.GAMESCREEN_WIDTH - 128, 0, 128);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);
        inputMultiplexer = new InputMultiplexer(ctrlPadMove, ctrlPadShoot);
        Gdx.input.setInputProcessor(inputMultiplexer);

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
        camera.update();

        //render elements
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(img, user.x, user.y);

        //draw all bullets and enemies from their respective arrays
        for (Rectangle bullet: User.userBullets) {
            game.batch.draw(bImg, bullet.x, bullet.y);
        }

        for (Enemy enemy: enemies) {
            game.batch.draw(slimeImg, enemy.x, enemy.y);
        }

        game.batch.draw(ctrlPadMove.getTexture(), 0, 0);
        game.batch.draw(ctrlPadShoot.getTexture(), Constants.GAMESCREEN_WIDTH - 128, 0);

        game.batch.end();

       /* //on screen touch, store bullet in uBullet array with proper direction
        if (Gdx.input.isTouched() && TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
            //grab touched position
            Vector2 touchPos = new Vector2();
            touchPos.set(Gdx.input.getX() - Constants.BULLET_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT - Gdx.input.getY() - Constants.BULLET_HEIGHT / 2);
            //grab user position
            Vector2 userPos = new Vector2();
            userPos.set(user.x, user.y);
            System.out.println(""+touchPos.x+" "+touchPos.y);
            // + Constants.USER_WIDTH / 2
            //creates bullet using two positions
            user.fireBullet(userPos, touchPos);
        }
        */

        //WASD moves user
        user.move();
        //ControlPad moves user, new Vector passed to fireBullet to avoid
        //continuously pointing to direction vector and thus being able to change
        //bullet direction in midair
        //SPEED IS ARBITRARY
        user.move(ctrlPadMove.getDirectionVector());
        if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
            if (ctrlPadShoot.getTouchDownOnPad()) {
                user.fireBullet(new Vector2(ctrlPadShoot.getDirectionVector()), 2);
            }
        }

        //keep user from moving off the screen
        if (user.x < 0) user.x = 0;
        if (user.x > Constants.GAMESCREEN_WIDTH - 64) user.x = 800 - 64;
        if (user.y < 0) user.y = 0;
        if (user.y > Constants.GAMESCREEN_HEIGHT - 64) user.y = Constants.GAMESCREEN_HEIGHT - 64;
        //spawn bullets
        //if (TimeUtils.nanoTime() - lastShotTime > 1000000000) spawnBullet();

        //spawn slimes
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) spawnSlime();

        //moves bullets, removes bullets off screen
        Iterator<Bullet> uIter = User.userBullets.iterator();
        while (uIter.hasNext()) {
            Bullet bullet = uIter.next();
            bullet.y += 200 * bullet.velocity.y * Gdx.graphics.getDeltaTime();
            bullet.x += 200 * bullet.velocity.x * Gdx.graphics.getDeltaTime();
            if (bullet.y + Constants.BULLET_HEIGHT < 0 || bullet.y > Constants.GAMESCREEN_HEIGHT) {
                uIter.remove();
            }
        }

        //iterate through bullets and check if they collide with an enemy.
        for (Bullet bullet: User.userBullets) {
            Iterator<Enemy> eIter = enemies.iterator();
            while (eIter.hasNext()) {
                if (bullet.overlaps(eIter.next())) {
                    eIter.remove();
                    User.userBullets.removeValue(bullet, true);
                }
            }
        }

        //move all enemies
        for (Enemy enemy: enemies) {
            enemy.move();
        }
    }

    @Override
    public void resize(int width, int height) {
        xScalingFactor = width / Constants.GAMESCREEN_WIDTH;
        yScalingFactor = height / Constants.GAMESCREEN_HEIGHT;
        user.x *= xScalingFactor;
        user.y *= xScalingFactor;
        user.width *= xScalingFactor;
        user.height *= xScalingFactor;
        for(Bullet bullet:User.userBullets) {
            bullet.width *= xScalingFactor;
            bullet.height *= yScalingFactor;
        }
        for(Enemy enemy:enemies) {
            enemy.width *= xScalingFactor;
            enemy.height *= yScalingFactor;
        }

        Constants.GAMESCREEN_WIDTH = width;
        Constants.GAMESCREEN_HEIGHT = height;

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
        batch.dispose();
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
        e.width = 64;
        e.height = 64;
        enemies.add(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }
}
