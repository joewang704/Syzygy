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

import java.util.NoSuchElementException;

//TODO made a bunch of TODOs in Room lol
public class RoomScreen implements Screen {

    final Syzygy game;

    //Shapes
    private User user;
    private Array<Enemy> enemies;
    private Joystick joystickMove;
    private Dungeon dungeon;
    private Room currentRoom;

    //Sprites now contained within respective classes

    public RoomScreen(final Syzygy game, int  dungeonID) {
        this.game = game;
        Gdx.input.setInputProcessor(game.getStage());
        createMoveAndFire();

        spawnUser(Constants.GAMESCREEN_WIDTH / 2 - Constants.USER_WIDTH / 2,
                0, Constants.USER_WIDTH, Constants.USER_HEIGHT);
        game.getStage().addActor(user);

        enemies = new Array<Enemy>();
        dungeon = new Dungeon(game, dungeonID, 8, user);

        //set starting room
        currentRoom = dungeon.getDungeonMap().get(new Vector2(0, 0));
        spawnEnemies();
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
        //should not use enemyNumber but length of enemies array instead
        //can also change screen back to main menu after defeating boss

        //Most recent Portal does not get removed
        currentRoom.setEnemyNumber(Collisions.enemyHits(enemies, currentRoom.getEnemyNumber()));
        if (currentRoom.getEnemyNumber() <= 0) {

            ChangeScreenPortal homePort = null;
            if (!currentRoom.PortalsAreOnStage()) {
                addPortalsToStage();
            }

            if (currentRoom.getClass().equals(Room_Boss.class)) {

                homePort = ((Room_Boss) currentRoom).getEndDungeon();
                homePort.setVisible(true);
                if (user.overlaps(homePort)) {
                    Syzygy.stage.clear();
                    Syzygy.stage.dispose();
                    game.setScreen(new Screen_Menu(game));
                }
            }

            //checking for movement to new Room
            for (int i = 0; i < currentRoom.getPortals().size; i++) {
                Portal portal = currentRoom.getPortals().get(i);
                if (user.overlaps(portal) && portal.isVisible()) {
                    currentRoom.removePortalsfromStage();
                    if (homePort != null) {
                        homePort.remove();
                        homePort.clear();
                    }
                    //repositions user after moving thru portal to pos of equivalent portal in newroom
                    //sets x or y to a certain edge of portal
                    if (PortalPos.UP == portal.getPortalPos()) {
                        user.setY(portal.getHeight() / 2);
                    } else if (PortalPos.LEFT == portal.getPortalPos()) {
                        user.setX(Constants.GAMESCREEN_WIDTH - Constants.PORTAL_WIDTH / 2 - user.getWidth());
                    } else if (PortalPos.RIGHT == portal.getPortalPos()) {
                        user.setX(portal.getWidth() / 2);
                    } else if (PortalPos.DOWN == portal.getPortalPos()) {
                        user.setY(Constants.GAMESCREEN_HEIGHT - Constants.PORTAL_HEIGHT / 2 - user.getHeight());
                    }
                    moveToNewRoom(portal);
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
    //If enemies have specific spawning properties, these should be dealt with in the constructors (ex: Big_Slime)
        for (int i = 0; i < currentRoom.getEnemyNumber(); i++) {
            //gets random enemy from the possible enemies of the room
//            Enemy e = new currentRoom.enemyArray.(MathUtils.random(currentRoom.enemyArray.size));
            int enemyToSpawn = MathUtils.random(dungeon.possibleEnemies.size - 1);
            Class enemyClass = dungeon.possibleEnemies.get(enemyToSpawn);
            Enemy e;
            if (enemyClass == Enemy_Slime.class) {
                e = new Enemy_Slime();
                System.out.print(" Slime" + i);
            } else if (enemyClass == Enemy_Golem.class) {
                e = new Enemy_Golem();
                System.out.print(" Golem" + i);
            } else if (enemyClass == Enemy_BigSlime.class) {
                e = new Enemy_BigSlime(enemies);
                System.out.print(" BigSlime" + i);
            } else if (enemyClass == Enemy_HitDetector.class) {
                e = new Enemy_HitDetector(User.bullets);
                System.out.print(" Spongebob" + i);
            } else {
                System.out.print("ERROR: ENEMY IN POSSIBLE ENEMIES NOT HANDLED WITH IFS IN SPAWNENEMIES()");
                break;//how do I not use the break? An exception causes yucky problems.
            }
            System.out.print(" " + e + i);
            enemies.add(e);
            game.getStage().addActor(e);
        }
    }

    private void spawnBoss(Enemy boss) {
        enemies.add(boss);
        Syzygy.stage.addActor(boss);
    }

    private void addPortalsToStage() {
        for(Portal portal : currentRoom.getPortals()) {
            game.getStage().addActor(portal);
        }
    }

    private void createMoveAndFire() {
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
                        event.getStageX() - user.getX() - user.getHeight()/2, event.getStageY() - user.getY() - user.getHeight()/2);
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

    //TODO should be adding bosses to bossList whenever adding a bossRoom during roomCreate so bossList.size == #of bossRooms
    private void moveToNewRoom(Portal portal) {
        currentRoom = portal.getNextRoom();
        if (currentRoom.getClass().equals(Room_Boss.class)) {
            //takes enemyNumber of bosses from the bosslist and adds them to the room
            for (int i = 0; i < currentRoom.getEnemyNumber(); i++) {
                Enemy newBoss = dungeon.bossList.random();
                spawnBoss(newBoss);
                dungeon.bossList.removeValue(newBoss, true);
            }
            Syzygy.stage.addActor(((Room_Boss) currentRoom).getEndDungeon());

        } else if (currentRoom.getClass().equals(Room_Trap.class)) {
            //trap rooms not implemented
        } else {
            spawnEnemies();
        }
    }
}
