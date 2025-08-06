package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Exercise;
import model.WorkoutLog;
import model.WorkoutSession;
import persistence.JsonReader;
import persistence.JsonWriter;

// WorkoutTrackerGUI is the GUI for this workout tracker app.
// It allows the user to add exercises, view workout sessions, 
// seaching for dates when an exercise was performed, and save/load data.
public class WorkoutTrackerGUI extends JFrame {
    public static final int ADD_TAB_INDEX = 0;
    public static final int REPORT_TAB_INDEX = 1;
    public static final int SETTINGS_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    public static final String SAVE_FILE = "./workoutlog.json";

    private JTextArea reportText;
    private JScrollPane reportPane;

    private WorkoutSession currentSession;
    private JTabbedPane sidebar;
    private WorkoutLog workoutLog;

    public WorkoutTrackerGUI() {
        setTitle("Workout Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        sidebar = new JTabbedPane(); 
        sidebar.setTabPlacement(JTabbedPane.BOTTOM);
        loadTabs();
        add(sidebar);
        getRootPane().setBorder(BorderFactory.createEmptyBorder());
        getContentPane().setBackground(Color.LIGHT_GRAY);
        workoutLog = new WorkoutLog();

        setVisible(true);
    }

    // MODIFIES: sidebar
    // EFFECTS: loads and adds the Add Workout, Report, and Save/Load tabs
    private void loadTabs() {
        sidebar.add(createAddWorkoutTab(), ADD_TAB_INDEX);
        sidebar.setTitleAt(ADD_TAB_INDEX, "Add Workout");

        sidebar.add(createReportTab(), REPORT_TAB_INDEX);
        sidebar.setTitleAt(REPORT_TAB_INDEX, "Workout Report");

        sidebar.add(createSettingsTab(), SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Load/Save");

        sidebar.setPreferredSize(new Dimension(150, 50));
        sidebar.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // REQUIRES: user must input valid integers for reps, sets, and weight
    // MODIFIES: this, workoutLog, currentSession
    // EFFECTS: creates the Add Workout tab using helper methods
    private JPanel createAddWorkoutTab() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField repsField = new JTextField(5);
        JTextField setsField = new JTextField(5);
        JTextField weightField = new JTextField(5);

        JPanel formPanel = createFormPanel(dateField, nameField, repsField, setsField, weightField);
        JButton addButton = createAddExerciseButton(dateField, nameField, repsField, setsField, weightField);
        JPanel imagePanel = createImagePanel();

        panel.add(formPanel);
        panel.add(formatButtonRow(addButton));
        panel.add(imagePanel);

        return panel;
    }

    // EFFECTS: creates form panel for user input
    private JPanel createFormPanel(JTextField date, JTextField name,
        JTextField reps, JTextField sets, JTextField weight) {
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 2, 8));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(createRow("Date (MM/DD/YY):", date));
        formPanel.add(createRow("Exercise:", name));
        formPanel.add(createRow("Reps:", reps));
        formPanel.add(createRow("Sets:", sets));
        formPanel.add(createRow("Weight (lbs):", weight));
        return formPanel;
    }

    // EFFECTS: creates the Add Exercise button
    private JButton createAddExerciseButton(JTextField dateField, JTextField nameField,
            JTextField repsField, JTextField setsField,
            JTextField weightField) {
        JButton addButton = new JButton("Add Exercise");
        addButton.setPreferredSize(new Dimension(150, 50));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setActionCommand("AddExercise");

        addButton.addActionListener(e -> {
            if (e.getActionCommand().equals("AddExercise")) {
                String date = dateField.getText();
                String name = nameField.getText();
                int reps = Integer.parseInt(repsField.getText());
                int sets = Integer.parseInt(setsField.getText());
                int weight = Integer.parseInt(weightField.getText());

                if (currentSession == null || !currentSession.getDate().equals(date)) {
                    currentSession = new WorkoutSession(date);
                    workoutLog.addWorkoutSession(currentSession);
                }
                Exercise newExercise = new Exercise(name, reps, sets, weight);
                currentSession.addExercise(newExercise);
            }
        });

        return addButton;
    }

    // EFFECTS: creates and returns the image panel
    private JPanel createImagePanel() {
        ImageIcon dumbbellIcon = new ImageIcon("./data/dumbbell.jpg");
        Image scaledImage = dumbbellIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel);
        return imagePanel;
    }

    // MODIFIES: reportText
    // EFFECTS: creates the Workout Report tab using helper methods
    private JPanel createReportTab() {
        JPanel panel = new JPanel(new BorderLayout());

        reportText = new JTextArea(10, 40);
        reportText.setEditable(true);
        reportPane = new JScrollPane(reportText);
        reportText.setOpaque(true);

        JButton showButton = createShowExercisesButton();
        JPanel topPanel = wrapButtonsAtTop(showButton, createSearchExercisePanel());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(reportPane, BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: creates "Show Exercises" button and its functionality
    private JButton createShowExercisesButton() {
        JButton showButton = new JButton("Show Exercises");
        showButton.setPreferredSize(new Dimension(150, 50));
        showButton.setFont(new Font("Arial", Font.BOLD, 14));
        showButton.setActionCommand("ShowExercises");

        showButton.addActionListener(e -> {
            if (e.getActionCommand().equals("ShowExercises")) {
                if (workoutLog.getSessionCount() > 0) {
                    reportText.setText(generateAllWorkoutReport());
                } else {
                    reportText.setText("No workout sessions available.");
                }
            }
        });

        return showButton;
    }

    // EFFECTS: generates full report of all workouts
    private String generateAllWorkoutReport() {
        StringBuilder report = new StringBuilder();
        for (WorkoutSession session : workoutLog.getSessions()) {
            report.append("Date: ").append(session.getDate()).append("\n");
            for (Exercise exercise : session.getExercises()) {
                report.append("- ").append(exercise.getName())
                        .append(": ").append(exercise.getSets()).append(" sets of ")
                        .append(exercise.getReps()).append(" reps with ")
                        .append(exercise.getWeight()).append(" lbs\n");
            }
        }
        return report.toString();
    }

    // EFFECTS: wraps show button and search button in a panel at the top
    private JPanel wrapButtonsAtTop(JButton showButton, JPanel searchPanel) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(formatButtonRow(showButton));
        topPanel.add(searchPanel);
        return topPanel;
    }

    // EFFECTS: creates a search panel to find all dates an exercise was performed
    @SuppressWarnings("methodlength")
    private JPanel createSearchExercisePanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search Dates");
        searchButton.setPreferredSize(new Dimension(150, 30));
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));

        searchButton.addActionListener(e -> {
            String exerciseName = searchField.getText().trim();
            if (!exerciseName.isEmpty()) {
                List<String> dates = workoutLog.getSessionDatesWithExercise(exerciseName);
                if (!dates.isEmpty()) {
                    StringBuilder result = new StringBuilder("Performed on:\n");
                    for (String date : dates) {
                        result.append("- ").append(date).append("\n");
                    }
                    reportText.setText(result.toString());
                } else {
                    reportText.setText("Exercise not found.");
                }
            }
        });

        searchPanel.add(new JLabel("Exercise Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    // MODIFIES: workoutLog, currentSession, statusLabel
    // EFFECTS: creates the Save/Load/Clear Log tab using helper methods
    private JPanel createSettingsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel statusLabel = new JLabel("Status: Ready");

        JButton saveButton = createSaveButton(statusLabel);
        JButton loadButton = createLoadButton(statusLabel);
        JButton clearButton = createClearButton(statusLabel);

        JPanel buttonPanel = formatCenteredButtonRow(saveButton, loadButton, clearButton);
        JPanel centerWrapper = wrapButtonsWithVerticalGlue(buttonPanel);

        panel.add(centerWrapper, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        return panel;
    }

    // EFFECTS: creates Save button
    private JButton createSaveButton(JLabel statusLabel) {
        JButton saveButton = makeStyledButton("Save Log", "Save");

        saveButton.addActionListener(e -> {
            try {
                JsonWriter writer = new JsonWriter(SAVE_FILE);
                writer.open();
                writer.write(workoutLog);
                writer.close();
                statusLabel.setText("Status: Saved successfully.");
            } catch (Exception ex) {
                statusLabel.setText("Status: Save failed.");
            }
        });

        return saveButton;
    }

    // EFFECTS: creates Load button
    private JButton createLoadButton(JLabel statusLabel) {
        JButton loadButton = makeStyledButton("Load Log", "Load");

        loadButton.addActionListener(e -> {
            try {
                JsonReader reader = new JsonReader(SAVE_FILE);
                workoutLog = reader.read();
                currentSession = null;
                statusLabel.setText("Status: Log loaded.");
            } catch (Exception ex) {
                statusLabel.setText("Status: Load failed.");
            }
        });

        return loadButton;
    }

    // EFFECTS: creates Clear button
    private JButton createClearButton(JLabel statusLabel) {
        JButton clearButton = makeStyledButton("Clear Log", "Clear");

        clearButton.addActionListener(e -> {
            workoutLog = new WorkoutLog();
            currentSession = null;
            reportText.setText("Workout log cleared.");
            statusLabel.setText("Status: Log cleared.");
        });

        return clearButton;
    }

    // EFFECTS: returns a button with standard style
    private JButton makeStyledButton(String label, String actionCommand) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setActionCommand(actionCommand);
        return button;
    }

    // EFFECTS: formats three buttons into a centered row
    private JPanel formatCenteredButtonRow(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(Color.WHITE);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    // EFFECTS: wraps buttons with vertical glue to center vertically
    private JPanel wrapButtonsWithVerticalGlue(JPanel innerPanel) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(Color.WHITE);

        wrapper.add(Box.createVerticalGlue());
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(innerPanel);
        wrapper.add(Box.createVerticalGlue());

        return wrapper;
    }

    // EFFECTS: creates and returns a row with a label and a text box
    private JPanel createRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel(labelText));
        row.setBackground(Color.WHITE);
        row.add(textField);
        return row;
    }

    // EFFECTS: returns a panel that puts a button in a row
    private JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel(new FlowLayout());
        p.setBackground(Color.WHITE);
        p.add(b);
        return p;
    }
}
