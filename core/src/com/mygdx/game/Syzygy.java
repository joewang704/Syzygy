package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GestureRecognizer.MatchingGesture;
import com.mygdx.game.GestureRecognizer.ProtractorGestureRecognizer;

import java.util.ArrayList;

/**
 * Created by wojang on 1/26/15.
 */
public class Syzygy extends Game {

    private BitmapFont font;
    public static Stage stage;
    private OrthographicCamera camera;

    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAMESCREEN_WIDTH, Constants.GAMESCREEN_HEIGHT);

        stage = new Stage(new StretchViewport(Constants.GAMESCREEN_WIDTH,
                Constants.GAMESCREEN_HEIGHT, camera), new SpriteBatch()) {

            private ArrayList<Vector2> originalPath = new ArrayList<Vector2>();
            private ProtractorGestureRecognizer recognizer =
                    new ProtractorGestureRecognizer(Gdx.files.internal("gestures/"));
            private MatchingGesture matchingGesture;

            @Override public boolean touchDown(int x, int y, int pointer, int button) {
                super.touchDown(x, y, pointer, button);
                originalPath.add(new Vector2(x, y));

                return false;
            }

            @Override public boolean touchDragged(int x, int y, int pointer) {
                super.touchDragged(x, y, pointer);
                originalPath.add(new Vector2(x, y));

                return false;
            }

            @Override public boolean touchUp(int x, int y, int pointer, int button) {
                super.touchUp(x, y, pointer, button);
                if (originalPath.size() >= 10) {
                    originalPath.add(new Vector2(x, y));
                    matchingGesture = recognizer.Recognize(originalPath);

                    Gdx.app.log("Gesture Name/Score", matchingGesture.getGesture().getName()
                            + Float.toString(matchingGesture.getScore()));
                }

                originalPath.clear();

                return false;
            }
        };

        Gdx.input.setInputProcessor(stage);
        //Use default arial font
        font = new BitmapFont();
        this.setScreen(new StartScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    public Stage getStage() { return stage; }
    public BitmapFont getFont() { return font; }
    public OrthographicCamera getCamera() { return camera; }
}
