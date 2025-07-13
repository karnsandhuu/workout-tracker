package model;
import java.util.List;

// Represents a user's full workout session (multiple different exercises and date)
public class WorkoutSession {

    private String date;
    private List<Exercise> exercises;

    //REQUIRES: Date is not null as well as empty
    //EFFECTS: Creates an empty workout session with only a date
    public WorkoutSession(String date) {
        //
    }

    //REQUIRES: value e is not null
    //MODIFIES: this
    //EFFECTS: Adds exercise into workout session
    public void addExercise(Exercise e) {

    }

    //EFFECTS: Returns exercise for this workout session
    public List<Exercise> getExercise() {
        return null;
    }
    //EFFECTS: Returns date for this workout session
    public String getDate() {
        return null;

    }

}
