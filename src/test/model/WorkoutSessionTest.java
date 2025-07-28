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

    @Test
    void testGetExerciseDetails() {
        WorkoutSession session = new WorkoutSession("07/26/25");
        assertEquals("No exercises recorded.", session.getExerciseDetails());

        Exercise e1 = new Exercise("Squats", 8, 3, 135);
        Exercise e2 = new Exercise("Bench", 5, 3, 185);
        session.addExercise(e1);
        session.addExercise(e2);

        String details = session.getExerciseDetails();

        assertTrue(details.contains("Workout on 07/26/25:"));
        assertTrue(details.contains("- Squats: 3 sets of 8 reps @ 135 lbs"));
        assertTrue(details.contains("- Bench: 3 sets of 5 reps @ 185 lbs"));
    }
}
