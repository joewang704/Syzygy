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
import com.mygdx.game.Screen_Menu;
import com.mygdx.game.Syzygy;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by Lucas on 3/11/2015.
 */
public class GestureWriter extends InputListener {

    private ArrayList<Vector2> originalPath;
    private ProtractorGestureRecognizer recognizer;
    private MatchingGesture matchingGesture;

    public GestureWriter() {
        originalPath = new ArrayList<Vector2>();
        recognizer = new ProtractorGestureRecognizer(Gdx.files.internal("gestures/"));
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

            Json json = new Json();
            FileHandle file = Gdx.files.local("gestures/" + Screen_Menu.gestureName.getText() + ".json");
            JsonWriter writer = new JsonWriter(file.writer(false));
            writer.setOutputType(JsonWriter.OutputType.json);
            try {
                //writes the gestures
                writer.object()
                        .set("Name", Screen_Menu.gestureName.getText())
                        .array("Points");

                for (Vector2 vec: originalPath) {
                    writer.object()
                            .set("X", vec.x)
                            .set("Y", vec.y)
                            .pop();
                }
                writer.pop().array("Vector");
                for (double d: DollarUnistrokeRecognizer.Vectorize(originalPath)) {
                    writer.value(d);
                }
                writer.pop().close();
                //fixes text to pretty print
                file.writeString(json.prettyPrint(file.readString()), false);

            } catch (IOException e) {
                System.out.print(e);
            }

            Screen_Menu.gestureName.setText("Enter Gest. Name");
//            TextButton newTextButt = new TextButton(matchingGesture.getGesture().getName()
//                    + ": " + Float.toString(matchingGesture.getScore()), Screen_MacroUI.uiSkin);
//            newTextButt.right();
//            Syzygy.stage.addActor(newTextButt);
        }

        originalPath.clear();
    }

}
