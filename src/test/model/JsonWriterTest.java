package model;

import model.Exercise;
import model.WorkoutLog;
import model.WorkoutSession;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutLog log = new WorkoutLog();
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
            WorkoutLog log = new WorkoutLog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutLog.json");
            log = reader.read();
            assertEquals(0, log.getSessionCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutLog() {
        try {
            WorkoutLog log = new WorkoutLog();
            WorkoutSession session1 = new WorkoutSession("2025-07-20");
            session1.addExercise(new Exercise("Bench Press", 5, 3, 135));
            session1.addExercise(new Exercise("Squat", 6, 4, 185));

            WorkoutSession session2 = new WorkoutSession("2025-07-18");
            session2.addExercise(new Exercise("Deadlift", 5, 3, 225));

            log.addWorkoutSession(session1);
            log.addWorkoutSession(session2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutLog.json");
            log = reader.read();
            List<WorkoutSession> sessions = log.getSessions();
            assertEquals(2, sessions.size());

            WorkoutSession s1 = sessions.get(0);
            assertEquals("2025-07-20", s1.getDate());
            assertEquals(2, s1.getExercises().size());
            assertEquals("Bench Press", s1.getExercises().get(0).getName());
            assertEquals(135, s1.getExercises().get(0).getWeight());

            WorkoutSession s2 = sessions.get(1);
            assertEquals("2025-07-18", s2.getDate());
            assertEquals(1, s2.getExercises().size());
            assertEquals("Deadlift", s2.getExercises().get(0).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}