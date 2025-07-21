package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WorkoutLogTest {

    private WorkoutLog log;
    private WorkoutSession session;
    private Exercise benchPress;

    @BeforeEach
    void runBefore() {
        log = new WorkoutLog();
        session = new WorkoutSession("07/12/2025");
        benchPress = new Exercise("Bench Press", 10, 3, 135);
        session.addExercise(benchPress);
    }

    @Test
    void testWorkoutLogConductor() {
        assertEquals(0, log.getSessionCount());
        assertTrue(log.getSessions().isEmpty());
    }

    @Test
    void testAddWorkoutSession() {
        log.addWorkoutSession(session);
        List<WorkoutSession> sessions = log.getSessions();
        assertEquals("07/12/2025", sessions.get(0).getDate());
        assertEquals(1, sessions.size());
    }

    @Test
    void testGetPersonalRecord() {
        log.addWorkoutSession(session);

        int squatPr = log.getPersonalRecord("Squat");
        assertEquals(0, squatPr);

        Exercise secondBench = new Exercise("Bench Press", 8, 4, 185);
        Exercise thirdBench = new Exercise("Bench Press", 6, 3, 115);

        WorkoutSession session2 = new WorkoutSession("07/13/2025");
        session2.addExercise(secondBench);
        WorkoutSession session3 = new WorkoutSession("07/14/2025");
        session3.addExercise(thirdBench);

        log.addWorkoutSession(session);
        log.addWorkoutSession(session2);
        log.addWorkoutSession(session3);

        int benchPR = log.getPersonalRecord("Bench Press");
        assertEquals(185, benchPR);
    }

    @Test
    void testGetWorkoutDatesByExerciseName() {
        WorkoutSession session2 = new WorkoutSession("07/11/2025");

        Exercise squat = new Exercise("Squat", 5, 3, 200);

        session2.addExercise(squat);

        log.addWorkoutSession(session);
        log.addWorkoutSession(session2);

        List<String> result1 = log.getSessionDatesWithExercise("Bench Press");
        List<String> result2 = log.getSessionDatesWithExercise("Squat");
        List<String> result3 = log.getSessionDatesWithExercise("Deadlift");

        assertEquals(1, result1.size());
        assertEquals("07/12/2025", result1.get(0));

        assertEquals(1, result2.size());
        assertEquals("07/11/2025", result2.get(0));

        assertTrue(result3.isEmpty());
    }
}
