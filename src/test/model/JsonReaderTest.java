package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutLog.json");
        try {
            WorkoutLog log = reader.read();
            assertEquals(0, log.getSessionCount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutLog() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutLog.json");
        try {
            WorkoutLog log = reader.read();
            List<WorkoutSession> sessions = log.getSessions();
            assertEquals(2, sessions.size());

            WorkoutSession s1 = sessions.get(0);
            assertEquals("2025-07-20", s1.getDate());
            List<Exercise> exercises1 = s1.getExercises();
            assertEquals(2, exercises1.size());

            Exercise e1 = exercises1.get(0);
            assertEquals("Bench Press", e1.getName());
            assertEquals(3, e1.getSets());
            assertEquals(5, e1.getReps());
            assertEquals(135, e1.getWeight());

            Exercise e2 = exercises1.get(1);
            assertEquals("Squat", e2.getName());
            assertEquals(4, e2.getSets());
            assertEquals(6, e2.getReps());
            assertEquals(185, e2.getWeight());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
