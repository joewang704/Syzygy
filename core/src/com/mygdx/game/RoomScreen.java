package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.event.InputEvent;

//TODO made a bunch of TODOs in Room lol
public class RoomScreen implements Screen {

    final Syzygy game;

    //Shapes
    private User user;
    private Array<Enemy> enemies;
    private Joystick joystickMove;
    private Dungeon dungeon;
    private Room currentRoom;


    //Utility
    private long lastSpawnTime;

    //Sprites now contained within respective classes

    public RoomScreen(final Syzygy game) {
        this.game = game;
        Gdx.input.setInputProcessor(game.getStage());
        createMoveAndFire();

        spawnUser(Constants.GAMESCREEN_WIDTH / 2 - Constants.USER_WIDTH / 2,
                0, Constants.USER_WIDTH, Constants.USER_HEIGHT);
        enemies = new Array<Enemy>();
        dungeon = new Dungeon(game, 1, 8);
        System.out.println(dungeon.getDungeonMap().entrySet());

        //set starting room
        currentRoom = dungeon.getDungeonMap().get(new Vector2(0, 0));
        spawnEnemies();

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

        //constantly update total number of enemies until it reaches 0
        //change to new room
        currentRoom.setEnemyNumber(Collisions.enemyHits(enemies, currentRoom.getEnemyNumber()));
        if (currentRoom.getEnemyNumber() <= 0) {
            addPortalsToStage();
            //check for collisions between each portal and the user
            for (Portal portal: currentRoom.getPortals()) {
                if (user.overlaps(portal) && portal.isVisible() && Collisions.moveToward(user, portal.getPortalPos())) {
                    currentRoom.removePortalsfromStage();
                    currentRoom = portal.getNextRoom();
                    //repositions user after moving thru portal to pos of equivalent portal in newroom
                    //sets x or y to a certain edge of portal
                    if (PortalPos.UP == portal.getPortalPos()) {
                        user.setY(portal.getHeight()/2);
                    } else if (PortalPos.LEFT == portal.getPortalPos()) {
                        user.setX(Constants.GAMESCREEN_WIDTH - Constants.PORTAL_HEIGHT / 2);
                    } else if (PortalPos.RIGHT == portal.getPortalPos()) {
                        user.setX(portal.getWidth()/2);
                    } else if (PortalPos.DOWN == portal.getPortalPos()) {
                        user.setY(Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT/2);
                    }
                    System.out.println("||" + currentRoom.getEnemyNumber() + "||");
                    spawnEnemies();
                }
            }
        }
        Collisions.removeBullets();

        //bullet firing
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 touchCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            touchPos = game.getStage().screenToStageCoordinates(touchPos);
            touchCoords = game.getStage().screenToStageCoordinates(touchCoords);

            //check if touchPos is outside of the moveStick
            if (touchPos.sub(joystickMove.getX() + joystickMove.getWidth()/2,
                    joystickMove.getY() + joystickMove.getHeight()/2).dst(touchCoords) > joystickMove.getWidth()/2) {
               if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {

                   game.getStage().addActor(user.fireBullet(
                            new Vector2(touchCoords), 10f));
               }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.getStage().getViewport().update(width, height, true);
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
        user = new User(joystickMove, x, y, width, height);
    }

    private void spawnEnemies() {
        for(int i = 0; i < currentRoom.getEnemyNumber(); i++) {
            int enemyToSpawn = MathUtils.random(2); //Line changed Jay Devanathan 2_15_2014
            if (enemyToSpawn == 0) {
                spawnSlime();
                System.out.print(" |Slime" + i);
            } else if (enemyToSpawn == 1) {
                spawnGolem();
                System.out.print(" |Golem" + i);
            } else {
                spawnBigSlime();
            }
        }
    }

    private void addPortalsToStage() {
        for(Portal portal : currentRoom.getPortals()) {
            game.getStage().addActor(portal);
        }
    }
    /**
     * Method added 2_15_2015 Jay Devanathan
     */
    private void spawnBigSlime() {
        float xPos = MathUtils.random(Constants.GAMESCREEN_WIDTH / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3);
        float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3);
        float width = Constants.ENEMY_SLIME_WIDTH;
        float height = Constants.ENEMY_SLIME_HEIGHT;
        Enemy e = new Enemy_BigSlime(xPos, yPos, game.getStage(), enemies);//, width, height);//Changed Line
        e.setName("Slime" + enemies.size);
        enemies.add(e);
        game.getStage().addActor(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }
    private void spawnSlime() {
        float xPos = MathUtils.random(Constants.GAMESCREEN_WIDTH / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3);
        float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3);
        float width = Constants.ENEMY_SLIME_WIDTH;
        float height = Constants.ENEMY_SLIME_HEIGHT;
        Enemy e = new Enemy_Slime(xPos, yPos, width, height);
        e.setName("Slime" + enemies.size);
        enemies.add(e);
        game.getStage().addActor(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }
    private void spawnGolem() {
        float xPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_WIDTH / 3);
        float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
                2 * Constants.GAMESCREEN_HEIGHT / 3);
        float width = Constants.ENEMY_GOLEM_WIDTH;
        float height = Constants.ENEMY_GOLEM_HEIGHT;
        Enemy e = new Enemy_Golem(xPos, yPos, width, height);
        e.setName("Golem" + enemies.size);
        enemies.add(e);
        game.getStage().addActor(e);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    //Joystick Position is entirely relative to GS_WIDTH
    //Joystick width & height entirely relative to GS_HEIGHT
    //sambady pls halp!
    //OR is it good because regardless of screen size, joysticks will always be circular instead of stretched?idk
    public void createMoveAndFire() {
        TextureRegionDrawable joystickImg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpad1.png"))));
        TextureRegionDrawable joystickKnob = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("controllerpadKnob100.png"))));
        joystickMove = new Joystick(20f, new Touchpad.TouchpadStyle(joystickImg, joystickKnob));

        joystickMove.setWidth(Constants.GAMESCREEN_HEIGHT/2f);
        joystickMove.setHeight(Constants.GAMESCREEN_HEIGHT/2f);
        joystickMove.setPosition(Constants.GAMESCREEN_WIDTH/40, Constants.GAMESCREEN_WIDTH/40);
        game.getStage().addActor(joystickMove);

        //add firing
    }
}
