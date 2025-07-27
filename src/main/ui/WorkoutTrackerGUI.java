package ui;

import javax.swing.*;

import model.Exercise;
import model.WorkoutSession;

import java.awt.*;

public class WorkoutTrackerGUI extends JFrame {
    public static final int ADD_TAB_INDEX = 0;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    public static final int REPORT_TAB_INDEX = 1;

    private JTextArea reportText;
    private JScrollPane reportPane; 

    private WorkoutSession currentSession;
    private JTabbedPane sidebar;

    public static void main(String[] args) {
        new WorkoutTrackerGUI();
    }

    public WorkoutTrackerGUI() {
        setTitle("Workout Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    private void loadTabs() {
        JPanel addWorkoutTab = createAddWorkoutTab();
        sidebar.add(addWorkoutTab, ADD_TAB_INDEX);
        sidebar.setTitleAt(ADD_TAB_INDEX, "Add Workout");
        JPanel reportTab = createReportTab();

        sidebar.add(reportTab, REPORT_TAB_INDEX); 
        sidebar.setTitleAt(REPORT_TAB_INDEX, "Workout Report"); 
    }

    private JPanel createAddWorkoutTab() {

        JPanel panel = new JPanel(new BorderLayout());

        JTextField dateField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField repsField = new JTextField(5);
        JTextField setsField = new JTextField(5);
        JTextField weightField = new JTextField(5);

        JPanel formPanel = new JPanel(new GridLayout(5, 1, 2, 8));
        formPanel.add(createRow("Date (MM/DD/YY):", dateField));
        formPanel.add(createRow("Exercise:", nameField));
        formPanel.add(createRow("Reps:", repsField));
        formPanel.add(createRow("Sets:", setsField));
        formPanel.add(createRow("Weight (lbs):", weightField));

        JButton addButton = new JButton("Add Exercise");
        addButton.setActionCommand("AddExercise");

        addButton.addActionListener(e -> {
            String buttonPressed = e.getActionCommand();

            if (buttonPressed.equals("AddExercise")) {
                String date = dateField.getText();
                String name = nameField.getText();
                int reps = Integer.parseInt((repsField.getText()));
                int sets = Integer.parseInt((setsField.getText()));
                int weight = Integer.parseInt((weightField.getText()));

                if (currentSession == null) {
                    currentSession = new WorkoutSession(date);
                }

                Exercise newExercise = new Exercise(name, reps, sets, weight);
                currentSession.addExercise(newExercise);

                System.out.println("Added: " + name + " - " + sets + " sets of " + reps + " reps with " + weight + " lbs");
            }
        });

        panel.add(formatButtonRow(addButton));

        panel.add(formPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel(labelText));
        row.add(textField);
        return row;
    }

    private JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel(new FlowLayout());
        p.add(b);
        return p;

    }


    private JPanel createReportTab() {
        JPanel panel = new JPanel(new BorderLayout());
    
        JButton showButton = new JButton("Show Exercises");
        showButton.setActionCommand("ShowExercises");
    
        reportText = new JTextArea(10, 40);
        reportText.setEditable(true);
        reportPane = new JScrollPane(reportText);
    
        showButton.addActionListener(e -> {
            if (e.getActionCommand().equals("ShowExercises")) {
                if (currentSession != null) {
                    reportText.setText(currentSession.getExerciseDetails());
                } else {
                    reportText.setText("No workout session found.");
                }
            }
        });
    
        panel.add(formatButtonRow(showButton), BorderLayout.NORTH);
        panel.add(reportPane, BorderLayout.CENTER);
    
        return panel;
    }

}
