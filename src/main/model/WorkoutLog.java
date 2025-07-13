package model;

import java.util.List;

// This represents a workout log containing all of a person's past workout sessions
public class WorkoutLog {

    private List<WorkoutSession> sessions;

    //EFFECTS: Constructs an empty workout log
    public WorkoutLog() {
        //

    }

    //REQUIRES: Session cannot be null
    //MODIFIES: this
    //EFFECTS: Adds workout session to workout log
    public void addWorkoutSession(WorkoutSession session) {
        //

    }

    //EFFECTS: returns highest weight lifted for given exercise 
    public int getPersonalRecord(String exerciseName) {
        return 0;

    }

    //EFFECTS: returns number of sessions in workout log
    public int getSessionCount() {
        return 0;
    }

    //EFFECTS: returns a list of all workout sessions
    public List<WorkoutSession> getSessions() {
        return null;

    }

}