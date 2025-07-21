package ui;

import model.Exercise;
import model.WorkoutLog;
import model.WorkoutSession;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.List;
import java.util.Scanner;

public class WorkoutTrackerApp {

    private WorkoutLog log;
    private Scanner input;
    private static final String JSON_STORE = "./data/workoutlog.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the app
    // Source: Teller Application
    public WorkoutTrackerApp() {
        log = new WorkoutLog();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWorkoutTracker();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runWorkoutTracker() {
        boolean stillRunning = true;

        while (stillRunning) {
            displayMenu();
            String command = input.nextLine();

            // Source: Teller Application
            if (command.equals("n")) {
                addWorkoutSession();
            } else if (command.equals("v")) {
                viewWorkoutSessions();
            } else if (command.equals("p")) {
                viewPersonalRecord();
            } else if (command.equals("s")) {
                searchExerciseAcrossSessions();
            } else if (command.equals("q")) {
                stillRunning = false;
                System.out.println("Exiting Tracker");
            } else {
                System.out.println("Invalid. Try again.");
            }
        }

    }

    // EFFECTS: displays a menu of options to the user
    // Source: Teller Application
    private void displayMenu() {
        System.out.println("\nWorkout Tracker Menu:");
        System.out.println("\tn -> Create new workout session");
        System.out.println("\tv -> View workout sessions");
        System.out.println("\tp -> View personal record");
        System.out.println("\ts -> Search exercise by name");
        System.out.println("\tq -> Quit");
        System.out.print("\nEnter choice:");
    }

    // EFFECTS: asks user for workout date and then asks user for exercise(s) info
    // then creates a workout session to add to workout log
    @SuppressWarnings("methodlength")
    private void addWorkoutSession() {
        System.out.print("\nEnter workout date: (mm/dd/yy):");
        String date = input.nextLine();
        WorkoutSession session = new WorkoutSession(date);

        boolean addingExercises = true;
        while (addingExercises) {
            System.out.print("\nWrite exercise name:");
            String name = input.nextLine();
            System.out.print("\nEnter amount of sets:");
            int sets = Integer.parseInt(input.nextLine());
            System.out.print("\nEnter amount of reps:");
            int reps = Integer.parseInt(input.nextLine());
            System.out.print("\nEnter weight used (in lbs):");
            int weight = Integer.parseInt(input.nextLine());

            Exercise exercise = new Exercise(name, reps, sets, weight);
            session.addExercise(exercise);

            System.out.println("Would you like to add another exercise? (y/n)");
            String addAnother = input.nextLine();
            addingExercises = addAnother.equals("y");
        }

        log.addWorkoutSession(session);
        System.out.println("Workout session added to log!");
    }

    // EFFECTS: showcases all workout sessions that have been added to workout log
    private void viewWorkoutSessions() {
        for (WorkoutSession session : log.getSessions()) {
            System.out.println("\nWorkout Date: " + session.getDate());
            for (Exercise exercise : session.getExercises()) {
                System.out.println(" - " + exercise.getName() + ": "
                        + exercise.getSets() + " sets of "
                        + exercise.getReps() + " reps at "
                        + exercise.getWeight() + " lbs");
            }
        }
    }

    // EFFECTS: displays personal record for given exercise or displays message if
    // not found
    private void viewPersonalRecord() {
        System.out.print("\nEnter exercise name to check PR: ");
        String name = input.nextLine();
        int pr = log.getPersonalRecord(name);

        if (pr == 0) {
            System.out.println("\nNo personal record found for " + name);
        } else {
            System.out.println("\nPersonal record for " + name + ": " + pr + " lbs");
        }
    }

    // EFFECTS: asks user for an exercise name, searches all workout sessions for
    // that exercise,
    // and prints a list of dates that the exercise was done
    // if the exercise is not found in any session, print a message
    private void searchExerciseAcrossSessions() {
        System.out.print("\nEnter exercise name to search: ");
        String name = input.nextLine();
        List<String> dates = log.getSessionDatesWithExercise(name);

        if (dates.isEmpty()) {
            System.out.println("No sessions found with exercise: " + name);
        } else {
            System.out.println("Exercise \"" + name + "\" was done on:");
            for (String date : dates) {
                System.out.println(" - " + date);
            }
        }
    }

    // EFFECTS: saves the current workout log to file
    private void saveWorkoutLog() {
    }

    // MODIFIES: this
    // EFFECTS: loads workout log from file
    private void loadWorkoutLog() {

    }
}