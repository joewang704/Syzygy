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

    //Camera
    OrthographicCamera camera;

    //Shapes
    public User user;
    Array<Enemy> enemies;

    //Utility
    private long lastSpawnTime;

    //Sprites
    SpriteBatch batch;
    Texture img, bImg, slimeImg;


    public GameScreen(final Name gam) {
        this.game = gam;
        img = new Texture(Gdx.files.internal("wizard.png"));
        bImg = new Texture(Gdx.files.internal("soccer.png"));
        slimeImg = new Texture(Gdx.files.internal("cuteSlime64.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);
        createUser(Constants.GAMESCREEN_WIDTH / 2 - Constants.USER_WIDTH / 2,
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
        game.batch.end();

        //on screen touch, store bullet in uBullet array with proper direction
        if (Gdx.input.isTouched() && TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
            //grab touched position
            Vector2 touchPos = new Vector2();
            touchPos.set(Gdx.input.getX() - Constants.BULLET_WIDTH / 2,
                Constants.GAMESCREEN_HEIGHT - Gdx.input.getY() - Constants.BULLET_HEIGHT / 2);
            //grab user position
            Vector2 userPos = new Vector2();
            userPos.set(user.x + Constants.USER_WIDTH / 2, user.y + Constants.USER_HEIGHT / 2);
            //creates bullet using two positions
            user.fireBullet(userPos, touchPos);
        }

        //WASD moves user
        if (Gdx.input.isKeyPressed(Input.Keys.A)) user.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) user.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) user.y -= 180 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) user.y += 180 * Gdx.graphics.getDeltaTime();

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
            if (bullet.y + 64 < 0 || bullet.y > 480) {
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
