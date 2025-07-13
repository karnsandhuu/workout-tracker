package model;
import java.util.ArrayList;
import java.util.List;

// Represents a user's full workout session (multiple different exercises and date)
public class WorkoutSession {

    private String date;
    private List<Exercise> exercises;

    //REQUIRES: Date is not null and not empty
    //EFFECTS: Creates an empty workout session with only a date
    public WorkoutSession(String date) {
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    //REQUIRES: value of e is not null
    //MODIFIES: this
    //EFFECTS: Adds exercise into workout session
    public void addExercise(Exercise e) {
        exercises.add(e);
    }

    //EFFECTS: Returns exercises for this workout session
    public List<Exercise> getExercises() {
        return exercises;
    }
    //EFFECTS: Returns date for this workout session
    public String getDate() {
        return date;

    }

}
