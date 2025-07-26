package ui;


import javax.swing.*;


public class WorkoutTrackerGUI extends JFrame {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    public static void main(String[] args) {
        new WorkoutTrackerGUI();
    }

    public WorkoutTrackerGUI() {
        setTitle("Workout Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }
}
