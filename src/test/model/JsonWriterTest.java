package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    private WorkoutLog log;
    private WorkoutSession session1;
    private WorkoutSession session2;

    @BeforeEach
    void runBefore() {
        log = new WorkoutLog();

        session1 = new WorkoutSession("2025-07-20");
        session1.addExercise(new Exercise("Bench Press", 5, 3, 135));
        session1.addExercise(new Exercise("Squat", 6, 4, 185));

        session2 = new WorkoutSession("2025-07-18");
        session2.addExercise(new Exercise("Deadlift", 5, 3, 225));

        log.addWorkoutSession(session1);
        log.addWorkoutSession(session2);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutLog.json");
            log = reader.read();
            assertEquals(2, log.getSessionCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutLog.json");
            WorkoutLog loadedLog = reader.read();
            List<WorkoutSession> sessions = loadedLog.getSessions();

            assertEquals(2, sessions.size());
            assertEquals("2025-07-20", sessions.get(0).getDate());
            assertEquals("Bench Press", sessions.get(0).getExercises().get(0).getName());
            assertEquals("2025-07-18", sessions.get(1).getDate());
            assertEquals("Deadlift", sessions.get(1).getExercises().get(0).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}