package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WorkoutSessionTest {

    private WorkoutSession session;
    private Exercise benchPress;

    @BeforeEach
    void runBefore() {
        session = new WorkoutSession("07/12/25");
        benchPress = new Exercise("Bench Press", 10, 3, 135);
    }

    @Test
    void testWorkoutSessionConductor() {
        assertEquals("07/12/25", session.getDate());
        assertTrue(session.getExercises().isEmpty());
    }

    @Test
    void testAddExercise() {
        session.addExercise(benchPress);
        List<Exercise> exercises = session.getExercises();
        assertEquals(1, exercises.size());
        assertEquals("Bench Press", exercises.get(0).getName());
        assertEquals(3, exercises.get(0).getSets());
        assertEquals(10, exercises.get(0).getReps());
        assertEquals(135, exercises.get(0).getWeight());

    }
}
