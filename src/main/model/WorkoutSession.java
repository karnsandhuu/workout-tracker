package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a user's full workout session (multiple different exercises and date)
public class WorkoutSession {

    private String date;
    private List<Exercise> exercises;

    // REQUIRES: Date is not null and not empty
    // EFFECTS: Creates an empty workout session with only a date
    public WorkoutSession(String date) {
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    // REQUIRES: value of exercise is not null
    // MODIFIES: this
    // EFFECTS: Adds exercise into workout session
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    // EFFECTS: Returns exercises for this workout session
    public List<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: Returns date for this workout session
    public String getDate() {
        return date;
    }

    // EFFECTS: returns this workout session as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        JSONArray exercisesArray = new JSONArray();

        for (Exercise e : exercises) {
            exercisesArray.put(e.toJson());
        }

        json.put("exercises", exercisesArray);
        return json;
    }

}
