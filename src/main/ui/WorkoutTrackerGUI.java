package ui;


import javax.swing.*;
import java.awt.*;


public class WorkoutTrackerGUI extends JFrame {
    public static final int ADD_TAB_INDEX = 0;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

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
}

private JPanel createAddWorkoutTab() {
    JPanel panel = new JPanel(new BorderLayout());

    JTextField dateField = new JTextField(1);
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

    panel.add(formPanel, BorderLayout.NORTH);

    return panel;
}

private JPanel createRow(String labelText, JTextField textField) {
    JPanel row = new JPanel(new GridLayout(2, 1));
    row.add(new JLabel(labelText));
    row.add(textField);
    return row;
}

}
