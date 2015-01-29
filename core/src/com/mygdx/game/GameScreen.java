package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    //Constants
    final static int WIDTH = 800;
    final static int HEIGHT = 480;

    //Variables
    int atkSpeed = 100000000;

    //Camera
    OrthographicCamera camera;

    //Shapes
    public User user;
    Array<Enemy> enemies;
    Array<Bullet> uBullets;

    //Utility
    private long lastShotTime, lastSpawnTime;

    //Sprites
    SpriteBatch batch;
    Texture img, bImg, slimeImg;


    public GameScreen(final Name gam) {
        this.game = gam;
        img = new Texture(Gdx.files.internal("wizard.png"));
        bImg = new Texture(Gdx.files.internal("soccer.png"));
        slimeImg = new Texture(Gdx.files.internal("slime.gif"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        createUser(WIDTH / 2 - 64 / 2, 0, 64, 64);

        uBullets = new Array<Bullet>();
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

        //draw all bullets in array
        for (Rectangle bullet: uBullets) {
            game.batch.draw(bImg, bullet.x, bullet.y);
        }

        for (Enemy enemy: enemies) {
            game.batch.draw(slimeImg, enemy.x, enemy.y);
        }
        game.batch.end();

        //on screen touch, store bullet in uBullet array with proper direction
        if (Gdx.input.isTouched() && TimeUtils.nanoTime() - lastShotTime > atkSpeed) {
            //grab touched position
            Vector2 touchPos = new Vector2();
            touchPos.set(Gdx.input.getX() - 32 / 2, HEIGHT - Gdx.input.getY() - 32 / 2);
            //grab user position
            Vector2 userPos = new Vector2();
            userPos.set(user.x + 64 / 2, user.y + 64 / 2);
            //creates bullet using two positions
            spawnUserBullet(userPos, touchPos);
        }

        //WASD moves user
        if (Gdx.input.isKeyPressed(Input.Keys.A)) user.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) user.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) user.y -= 180 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) user.y += 180 * Gdx.graphics.getDeltaTime();

        //keep from user moving off the screen
        if (user.x < 0) user.x = 0;
        if (user.x > 800 - 64) user.x = 800 - 64;

        //spawn bullets
        //if (TimeUtils.nanoTime() - lastShotTime > 1000000000) spawnBullet();

        //spawn slimes
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) spawnSlime();

        //moves bullets, does collisions
        /*Iterator<Rectangle> iter = bullets.iterator();
        while (iter.hasNext()) {
            Rectangle bullet = iter.next();
            bullet.y -= 200 * Gdx.graphics.getDeltaTime();
            if (bullet.y + 64 < 0) {
                iter.remove();
            }
            if (bullet.overlaps(user)) {
                iter.remove();
            }
        }*/

        //moves bullets, does collisions
        Iterator<Bullet> uIter = uBullets.iterator();
        while (uIter.hasNext()) {
            Bullet b = uIter.next();
            b.y += 200 * b.v.y * Gdx.graphics.getDeltaTime();
            b.x += 200 * b.v.x * Gdx.graphics.getDeltaTime();
            if (b.y + 64 < 0 || b.y > 480) {
                uIter.remove();
            }
        }

        Iterator<Enemy> eIter = enemies.iterator();
        for (Bullet b : uBullets) {
            while (eIter.hasNext()) {
                if (b.overlaps(eIter.next())) {
                    eIter.remove();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
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
    private void createUser(float x, float y, float width, float height) {
        user = new User();
        user.x = x;
        user.y = y;
        user.width = width;
        user.height = height;
    }

    private void spawnUserBullet(Vector2 beginVector, Vector2 endVector) {
        Vector2 v = new Vector2();
        v.set(endVector).sub(beginVector).nor();
        Bullet bullet = new Bullet(v, false);
        bullet.x = user.x + 64 / 2;
        bullet.y = user.y + 64 / 2;
        bullet.width = 64;
        bullet.height = 64;
        uBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }

    private void spawnSlime() {
        Enemy e = new Enemy();
        e.x = MathUtils.random(WIDTH / 3, 2 * WIDTH / 3);
        e.y = MathUtils.random(HEIGHT / 3, 2 * HEIGHT / 3);
        e.width = 64;
        e.height = 64;
        enemies.add(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }
}
