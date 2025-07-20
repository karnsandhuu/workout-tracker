package persistence;

import model.Exercise;
import model.WorkoutSession;
import model.WorkoutLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads WorkoutLog from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout log from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WorkoutLog from JSON object and returns it
    private WorkoutLog parseWorkoutLog(JSONObject jsonObject) {
        WorkoutLog log = new WorkoutLog();
        addWorkoutSessions(log, jsonObject);
        return log;
    }

    // MODIFIES: log
    // EFFECTS: parses workout sessions from JSON object and adds them to the log
    private void addWorkoutSessions(WorkoutLog log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessions");
        for (Object sessionObj : jsonArray) {
            JSONObject sessionJson = (JSONObject) sessionObj;
            WorkoutSession session = parseWorkoutSession(sessionJson);
            log.addWorkoutSession(session);
        }
    }

    // EFFECTS: parses a workout session from JSON object
    private WorkoutSession parseWorkoutSession(JSONObject json) {
        String date = json.getString("date");
        WorkoutSession session = new WorkoutSession(date);

        JSONArray exercisesArray = json.getJSONArray("exercises");
        for (Object obj : exercisesArray) {
            JSONObject exerciseJson = (JSONObject) obj;
            Exercise e = new Exercise(
                    exerciseJson.getString("name"),
                    exerciseJson.getInt("reps"),
                    exerciseJson.getInt("sets"),
                    exerciseJson.getInt("weight"));
            session.addExercise(e);
        }

        return session;
    }
}
