package ui;

import model.Exercise;
import model.WorkoutLog;
import model.WorkoutSession;

import java.util.Scanner;

public class WorkoutTrackerApp {
    
    private WorkoutLog log;
    private Scanner input;

    //EFFECTS: Runs the app
    // Source: Teller Application
    public WorkoutTrackerApp() {
        log = new WorkoutLog();
        input = new Scanner(System.in);
        runWorkoutTracker();
    }

    //MODIFIES: this
    //EFFECTS: Processes user input
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
            } else if (command.equals("q")) {
                stillRunning = false;
                System.out.println("Exiting Tracker");
            } else {
                System.out.println("Invalid. Try again.");
            }
        }

    }

    //EFFECTS: displays a menu of options to the user
    // Source: Teller Application
    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("\tn -> Create new workout session");
        System.out.println("\tv -> View workout sessions");
        System.out.println("\tp -> View personal record");
        System.out.println("\tq -> Quit");
        System.out.print("\nEnter choice:");
    }

    //EFFECTS: asks user for workout date and then asks user for exercise(s) info
    //         then creates a workout session to add to workout log
    private void addWorkoutSession() {
        System.out.print("\n Enter workout date: (mm/dd/yy):");
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

    //EFFECTS: showcases all workout sessions that have been added to workout log
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

    //EFFECTS: displays personal record for given exercise
    private void viewPersonalRecord() {
        System.out.print("Enter exercise name to check PR: ");
        String name = input.nextLine();
        int pr = log.getPersonalRecord(name);

        if (pr == 0) {
            System.out.println("No personal record found for " + name);
        } else {
            System.out.println("Personal record for " + name + ": " + pr + " lbs");
        }
    }
}