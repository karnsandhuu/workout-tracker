package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Event;
import model.EventLog;

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
        EventLog.getInstance().logEvent(
                new Event("Workout session added: " + session.getDate()));
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

    // EFFECTS: returns a list of dates where the given exercise was performed
    public List<String> getSessionDatesWithExercise(String exerciseName) {
        List<String> sameDates = new ArrayList<>();
        for (WorkoutSession session : sessions) {
            for (Exercise exercise : session.getExercises()) {
                if (exercise.getName().equalsIgnoreCase(exerciseName)) {
                    sameDates.add(session.getDate());
                }
            }
        }
        return sameDates;
    }

    // MODIFIES: this
    // EFFECTS: removes all workout sessions from the log and logs the event
    public void clear() {
        sessions.clear();
        EventLog.getInstance().logEvent(new Event("Workout log cleared"));
    }

    // EFFECTS: returns this workout log as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray sessionsArray = new JSONArray();

        for (WorkoutSession session : sessions) {
            sessionsArray.put(session.toJson());
        }

        json.put("sessions", sessionsArray);
        return json;
    }

}