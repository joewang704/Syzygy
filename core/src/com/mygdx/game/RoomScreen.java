package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

//TODO made a bunch of TODOs in Room lol
public class RoomScreen implements Screen {

    final Syzygy game;

    //Shapes
    private User user;
    private Array<Enemy> enemies;
    private Joystick joystickMove;
    private Dungeon dungeon;
    private Room currentRoom;
    private Vector2 screenHWInStageCoords;


    //Utility
    private long lastSpawnTime;

    //Sprites now contained within respective classes

    public RoomScreen(final Syzygy game) {
        this.game = game;
        screenHWInStageCoords = game.getStage().screenToStageCoordinates(new Vector2(Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT));
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
                if (user.overlaps(portal) && portal.isVisible()) {
                    currentRoom.removePortalsfromStage();
                    currentRoom = portal.getNextRoom();
                    //repositions user after moving thru portal to pos of equivalent portal in newroom
                    //sets x or y to a certain edge of portal
                    if (PortalPos.UP == portal.getPortalPos()) {
                        user.setY(portal.getHeight()/2);
                    } else if (PortalPos.LEFT == portal.getPortalPos()) {
                        user.setX(Constants.GAMESCREEN_WIDTH - Constants.PORTAL_WIDTH/2 - user.getWidth());
                    } else if (PortalPos.RIGHT == portal.getPortalPos()) {
                        user.setX(portal.getWidth()/2);
                    } else if (PortalPos.DOWN == portal.getPortalPos()) {
                        user.setY(Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT/2 - user.getHeight());
                    }
                    System.out.println("||" + currentRoom.getPortals() + "||");
                    spawnEnemies();
                }
            }
        }
        Collisions.removeBullets();
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
    //edited to include the "spawn enemyname" method details in one method
    //change the value of e to spawn a new enemy, no need for separate method call.
    //If enemies have specific spawning properties, these should be dealt with in the constructors (ex: Big_Slime)
        for(int i = 0; i < currentRoom.getEnemyNumber(); i++) {
            int enemyToSpawn = MathUtils.random(3);
            Enemy e;
            if (enemyToSpawn == 3) {
                e = new Enemy_Slime();
                System.out.print(" Slime" + i);
            } else if (enemyToSpawn == 2) {
                e = new Enemy_Golem();
                System.out.print(" Golem" + i);
            } else if (enemyToSpawn == 1) {
                e = new Enemy_BigSlime(game.getStage(), enemies);
                System.out.print(" BigSlime" + i);
            } else {
                e = new Enemy_HitDetector(user.userBullets);
                System.out.print(" Spongebob" + i);
            }
            enemies.add(e);
            game.getStage().addActor(e);
            lastSpawnTime = TimeUtils.nanoTime();
        }
    }
    private void addPortalsToStage() {
        for(Portal portal : currentRoom.getPortals()) {
            game.getStage().addActor(portal);
        }
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
        joystickMove.setHeight(Constants.GAMESCREEN_HEIGHT / 2f);
        joystickMove.setPosition(Constants.GAMESCREEN_WIDTH / 40, Constants.GAMESCREEN_WIDTH / 40);
        game.getStage().addActor(joystickMove);

        game.getStage().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 touchPos = new Vector2(event.getStageX(), event.getStageY());
                Vector2 fireDirection = new Vector2(
                        event.getStageX() - user.getX(), event.getStageY() - user.getY());

                //check if touchPos is outside of the moveStick
                if (touchPos.sub(joystickMove.getX() + joystickMove.getWidth() / 2,
                        joystickMove.getY() + joystickMove.getHeight() / 2).len() > joystickMove.getWidth() / 2) {

                    if (TimeUtils.nanoTime() - user.getLastShotTime() > user.getAtkSpeed()) {
                        game.getStage().addActor(user.fireBullet(
                                new Vector2(fireDirection), 10f));
                    }
                }
                return false;
            }
        });

        //add firing
    }
}
