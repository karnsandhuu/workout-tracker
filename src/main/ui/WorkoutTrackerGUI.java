package ui;

import javax.swing.*;

import model.Exercise;
import model.WorkoutLog;
import model.WorkoutSession;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.IOException;

public class WorkoutTrackerGUI extends JFrame {
    public static final int ADD_TAB_INDEX = 0;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    public static final int REPORT_TAB_INDEX = 1;
    public static final int SETTINGS_TAB_INDEX = 2;

    public static final String SAVE_FILE = "./workoutlog.json";

    private JTextArea reportText;
    private JScrollPane reportPane; 

    private WorkoutSession currentSession;
    private JTabbedPane sidebar;
    private WorkoutLog workoutLog;

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
        workoutLog = new WorkoutLog();

        setVisible(true);
    }

    private void loadTabs() {
        JPanel addWorkoutTab = createAddWorkoutTab();
        sidebar.add(addWorkoutTab, ADD_TAB_INDEX);
        sidebar.setTitleAt(ADD_TAB_INDEX, "Add Workout");
        JPanel reportTab = createReportTab();

        sidebar.add(reportTab, REPORT_TAB_INDEX); 
        sidebar.setTitleAt(REPORT_TAB_INDEX, "Workout Report"); 

        JPanel settingsTab = createSettingsTab();
        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
         sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Load/Save");
        }

    private JPanel createAddWorkoutTab() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 

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
                workoutLog.addWorkoutSession(currentSession);

                System.out.println("Added: " + name + " - " + sets + " sets of " + reps + " reps with " + weight + " lbs");
            }
        });

        ImageIcon dumbbellIcon = new ImageIcon("./data/dumbbell.jpg");
        Image scaledImage = dumbbellIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);

        panel.add(formPanel);                    
        panel.add(formatButtonRow(addButton));   
        panel.add(imagePanel);  


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
                if (workoutLog.getSessionCount() > 0) {
                    StringBuilder report = new StringBuilder();
                    for (WorkoutSession session : workoutLog.getSessions()) {
                        report.append("Date: ").append(session.getDate()).append("\n");
                        for (Exercise exercise : session.getExercises()) {
                            report.append("- ").append(exercise.getName())
                                  .append(": ").append(exercise.getSets()).append(" sets of ")
                                  .append(exercise.getReps()).append(" reps with")
                                  .append(exercise.getWeight()).append(" lbs\n");
                        }
                    }
                    reportText.setText(report.toString());
                } else {
                    reportText.setText("No workout sessions available.");
                }
            }
        });
    
        panel.add(formatButtonRow(showButton), BorderLayout.NORTH);
        panel.add(reportPane, BorderLayout.CENTER);
    
        return panel;
    }

    private JPanel createSettingsTab() {
    JPanel panel = new JPanel(new BorderLayout());

    JButton saveButton = new JButton("Save Log");
    saveButton.setActionCommand("Save");
    JButton loadButton = new JButton("Load Log");
    loadButton.setActionCommand("Load");

    JLabel statusLabel = new JLabel("Status: Ready");

    saveButton.addActionListener(e -> {
        if (e.getActionCommand().equals("Save")) {
            try {
                JsonWriter writer = new JsonWriter(SAVE_FILE);
                writer.open();
                writer.write(workoutLog);
                writer.close();
                statusLabel.setText("Status: Saved successfully.");
            } catch (Exception ex) {
                statusLabel.setText("Status: Save failed.");
            }
        }
    });

    loadButton.addActionListener(e -> {
        if (e.getActionCommand().equals("Load")) {
            try {
                JsonReader reader = new JsonReader(SAVE_FILE);
                workoutLog = reader.read();
                currentSession = null; 
                statusLabel.setText("Status: Log loaded.");
            } catch (Exception ex) {
                statusLabel.setText("Status: Load failed.");
            }
        }
    });

    JPanel buttons = new JPanel(new FlowLayout());
    buttons.add(saveButton);
    buttons.add(loadButton);

    panel.add(buttons, BorderLayout.NORTH);
    panel.add(statusLabel, BorderLayout.SOUTH);
    return panel;
}

}
