package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    private Exercise exercise;

    @BeforeEach
    void runBefore() {
        exercise = new Exercise("Bench Press", 10, 3, 135);
    }

    @Test
    void testConductor() {
        assertEquals("Bench Press", exercise.getName());
        assertEquals(10, exercise.getReps());
        assertEquals(3, exercise.getSets());
        assertEquals(135, exercise.getWeight());
    }
}
