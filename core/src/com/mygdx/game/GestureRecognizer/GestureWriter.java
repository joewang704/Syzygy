package com.mygdx.game.GestureRecognizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Screen_MacroUI;
import com.mygdx.game.Syzygy;

import java.util.ArrayList;

/**
 * Created by Lucas on 3/11/2015.
 */
public class GestureWriter extends InputListener {

    private ArrayList<Vector2> originalPath;
    private ProtractorGestureRecognizer recognizer;
    private MatchingGesture matchingGesture;
    private boolean currentlyAddingGesture;

    public GestureWriter() {
        originalPath = new ArrayList<Vector2>();
        recognizer = new ProtractorGestureRecognizer(Gdx.files.internal("gestures/"));
        setCurrentlyAddingGesture(true);
    }

    @Override public boolean touchDown(InputEvent inputEvent, float x, float y, int pointer, int button) {
        super.touchDown(inputEvent, x, y, pointer, button);
        originalPath.add(new Vector2(x, y));
        return true;
    }

    @Override public void touchDragged(InputEvent inputEvent, float x, float y, int pointer) {
        super.touchDragged(inputEvent, x, y, pointer);
        originalPath.add(new Vector2(x, y));
    }

    @Override public void touchUp(InputEvent inputEvent, float x, float y, int pointer, int button) {
        super.touchUp(inputEvent, x, y, pointer, button);
        if (originalPath.size() >= 10) {
            originalPath.add(new Vector2(x, y));
            matchingGesture = recognizer.Recognize(originalPath);

//            if (currentlyAddingGesture) {
//                Json json = new Json();
//                //create the gesture file
//                FileHandle file = Gdx.files.local("Gesture" + originalPath.hashCode() + ".json");
//                json.setWriter(file.writer(false));
//
//                try {
//                    file.writjson.toJson()
//
//                    json.writeObjectStart();
//                    json.writeValue("Gesture" + originalPath.hashCode(), "Name");
//                    json.writeFields(originalPath);
//                    json.writeField(DollarUnistrokeRecognizer.Vectorize(originalPath), "Vector");
//                    json.writeObjectEnd();
//                    /*jsonWriter.object("Gesture" + originalPath.hashCode()).array()
//                            .object("Name").value("Gesture" + originalPath.hashCode())
//                            .object("Points").value(json.toJson(originalPath, ArrayList.class, Vector2.class))
//                            .object("Vectors").value(DollarUnistrokeRecognizer.Vectorize(originalPath))
//                            .close();
//                    */
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                //json.toJson(originalPath, ArrayList.class, Vector2.class);
//            }
            Syzygy.stage.addActor(new TextButton(matchingGesture.getGesture().getName()
                    + ": " + Float.toString(matchingGesture.getScore()), Screen_MacroUI.uiSkin));
        }

        originalPath.clear();
    }

    public void setCurrentlyAddingGesture(boolean b) {currentlyAddingGesture = b;}
    public boolean currentlyAddingGesture() {return currentlyAddingGesture;}


}
