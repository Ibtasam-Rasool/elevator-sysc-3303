package com.yourname.elevator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Button panel for inside elevator car to select destination floor
 *
 * @Author: Daniel Godfrey
 */
public class CarButtonPanel extends JFrame {
    private final int numFloors;
    private final int numCars;
    private JComboBox<String> elevatorSelector;
    private JPanel floorsPanel;
    private List<List<JButton>> elevatorButtons;

    public CarButtonPanel(int numFloors, int numCars) {
        this.numFloors = numFloors;
        this.numCars = numCars;
        this.elevatorButtons = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Car Button Panel");
        setSize(300, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dropdown to select the elevator
        elevatorSelector = new JComboBox<>();
        for (int i = 1; i <= numCars; i++) {
            elevatorSelector.addItem("Elevator " + i);
        }
        add(elevatorSelector, BorderLayout.NORTH);

        // Panel for floor buttons
        floorsPanel = new JPanel(new GridLayout(0, 3));
        add(floorsPanel, BorderLayout.CENTER);

        for (int i = 0; i < numCars; i++) {
            ArrayList<JButton> buttonsForOneElevator = new ArrayList<>();
            for (int floor = 1; floor <= numFloors; floor++) {
                JButton button = new JButton(Integer.toString(floor));
                int finalI = i;
                button.addActionListener(e -> handleFloorButtonPress(button, finalI));
                buttonsForOneElevator.add(button);
            }
            elevatorButtons.add(buttonsForOneElevator);
        }

        // Load the buttons for the first elevator
        loadButtonsForElevator(0);

        // Listener for elevator selection
        elevatorSelector.addActionListener(e -> {
            int selectedCar = elevatorSelector.getSelectedIndex();
            loadButtonsForElevator(selectedCar);
        });

        setVisible(true);
    }

    private void loadButtonsForElevator(int carIndex) {
        floorsPanel.removeAll();

        List<JButton> buttons = elevatorButtons.get(carIndex);
        int numButtons = buttons.size();

        int columns = 3;
        int rows = (int) Math.ceil((double) numButtons / columns);

        floorsPanel.setLayout(new GridLayout(rows, columns));

        // Placeholder to fill in the grid from bottom left
        JButton[] placeholders = new JButton[rows * columns];

        // Calculate starting index for the first button at the bottom left
        int startIndex = (rows - 1) * columns;

        for (int i = 0; i < numButtons; i++) {
            int row = startIndex / columns - (i / columns);
            int col = i % columns;
            int index = row * columns + col;
            placeholders[index] = buttons.get(i);
        }

        for (JButton placeholder : placeholders) {
            if (placeholder != null) {
                floorsPanel.add(placeholder);
            } else {
                floorsPanel.add(new JPanel());
            }
        }

        floorsPanel.revalidate();
        floorsPanel.repaint();
    }

    // Similar to FloorButtonPanel. Right now it uses a 2-second wait, but it should be called by the scheduler when the elevator arrives at a specified destination floor
    private void handleFloorButtonPress(JButton button, int elevatorIndex) {
        button.setEnabled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.RED));
        Timer timer = new Timer(5000, e -> {
            button.setEnabled(true);
            button.setBorder(UIManager.getBorder("Button.border"));
        });
        timer.setRepeats(false);
        timer.start();
    }
}
