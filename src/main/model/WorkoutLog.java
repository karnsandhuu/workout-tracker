package model;

import java.util.ArrayList;
import java.util.List;

// This represents a workout log containing all of a person's past workout sessions
public class WorkoutLog {

    private List<WorkoutSession> sessions;

    // EFFECTS: Constructs an empty workout log
    public WorkoutLog() {
        this.sessions = new ArrayList<>();

    }

    // REQUIRES: Session cannot be null
    // MODIFIES: this
    // EFFECTS: Adds workout session to workout log
    public void addWorkoutSession(WorkoutSession session) {
        sessions.add(session);

    }

    // EFFECTS: returns highest weight lifted for given exercise or 0 if exercise is
    // not in any session
    public int getPersonalRecord(String exerciseName) {
        int maxWeight = 0;

        for (WorkoutSession session : sessions) {
            for (Exercise exercise : session.getExercises()) {
                if (exercise.getName().equals(exerciseName)) {
                    if (exercise.getWeight() > maxWeight) {
                        maxWeight = exercise.getWeight();
                    }
                }
            }
        }
        return maxWeight;
    }

    // EFFECTS: returns number of sessions in workout log
    public int getSessionCount() {
        return sessions.size();
    }

    // EFFECTS: returns a list of all workout sessions
    public List<WorkoutSession> getSessions() {
        return sessions;

    }

}