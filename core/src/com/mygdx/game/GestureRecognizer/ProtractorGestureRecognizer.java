package com.mygdx.game.GestureRecognizer;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * MIGHT BE VERY INEFFICIENT: MUST IMPROVE PARSETIME OF JSON
 */
public class ProtractorGestureRecognizer {

	private ArrayList<TemplateGesture> registeredGestures;
	public ProtractorGestureRecognizer() {
		registeredGestures = new ArrayList<TemplateGesture>();
	}
    public ProtractorGestureRecognizer(FileHandle fh) {
        this();
        addGestureFromFile(fh);
    }

	public void addGesture(TemplateGesture tg) {
		registeredGestures.add(tg);
	}

	@SuppressWarnings("unchecked")
	public void addGestureFromFile(FileHandle handle) {
		if (handle.isDirectory()) {
			FileHandle[] files = handle.list("json");
			for (FileHandle f : files)
				addGestureFromFile(f);
			
		} else {
            JsonReader jreader = new JsonReader();
            ObjectMap obj = new ObjectMap();
            JsonValue jsonValue = jreader.parse(handle);
            jsonValue = jsonValue.child();
            //adds all the contents of the json file to the object map
            while (jsonValue != null) {
                obj.put(jsonValue.name(), jsonValue);
                jsonValue = jsonValue.next();
            }

            //PARSES THROUGH ALL POINTS OBJECTS
            String _name = ((JsonValue) obj.get("Name")).asString();

            ArrayList<Vector2> _points = new ArrayList<Vector2>();
            JsonValue pointObjectLL = (JsonValue) obj.get("Points");
            pointObjectLL = pointObjectLL.child();

            //loop through each point object
            while (pointObjectLL != null) {
            //add X and Y to their own maps
                _points.add(new Vector2(pointObjectLL.child().asFloat(),
                        pointObjectLL.child().next().asFloat()));
                pointObjectLL = pointObjectLL.next();
            }

            addGesture(new TemplateGesture(_name, _points));

       		}
	}

	public void removeGesture(TemplateGesture tg) {
		registeredGestures.remove(tg);
	}

	public MatchingGesture Recognize(ArrayList<Vector2> originalPath) {
		float[] vector = DollarUnistrokeRecognizer.Vectorize(originalPath);

		TemplateGesture match = null;
		float b = Float.POSITIVE_INFINITY;
		for (TemplateGesture gesture : registeredGestures) {
			float d = OptimalCosineDistance(gesture.getVector(), vector);

			if (d < b) {
				b = d;
				match = gesture;
			}
		}

		return new MatchingGesture(match, 1.0f / b);
	}

	private float OptimalCosineDistance(float[] v1, float[] v2) {
		float a = 0.0f;
		float b = 0.0f;
		float angle = 0.0f;

		int len = Math.min(v1.length, v2.length);
		for (int i = 0; i < len; i += 2) {
			a += v1[i] * v2[i] + v1[i + 1] * v2[i + 1];
			b += v1[i] * v2[i + 1] - v1[i + 1] * v2[i];
		}

		angle = (float) Math.atan(b / a);
		return (float) Math.acos(a * Math.cos(angle) + b * Math.sin(angle));
	}

}
