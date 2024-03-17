package com.yourname.elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Button panel for floors to request an elevator
 *
 * @Author: Daniel Godfrey
 */
public class FloorButtonPanel extends JFrame {
    private JComboBox<Integer> floorSelector;
    private JButton upButton, downButton;
    private final int numFloors;
    private final Map<Integer, Boolean[]> floorButtonStatus; // Array of 2 booleans for each floor, representing Up[0] and Down[1] buttons status

    public FloorButtonPanel(int numFloors) {
        this.numFloors = numFloors;
        this.floorButtonStatus = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Elevator Control Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300); // Adjust window size
        setLayout(new GridBagLayout()); // Use GridBagLayout for better control over component positioning

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around components

        // Floor selector
        floorSelector = new JComboBox<>();
        for (int i = numFloors; i >= 1; i--) {
            floorSelector.addItem(i);
            floorButtonStatus.put(i, new Boolean[]{false, false}); // Initialize all buttons as unlit
        }
        floorSelector.setPreferredSize(new Dimension(120, 50));
        ((JLabel)floorSelector.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        floorSelector.setFont(new Font("Arial", Font.PLAIN, 20));
        add(floorSelector, gbc);

        // Button setup
        upButton = createButton("Ʌ");
        downButton = createButton("V");

        add(upButton, gbc);
        add(downButton, gbc);

        upButton.addActionListener(e -> buttonPressed(true));
        downButton.addActionListener(e -> buttonPressed(false));
        floorSelector.addActionListener(this::floorChanged);

        updateButtonStatus();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 60));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        return button;
    }

    private void floorChanged(ActionEvent e) {
        updateButtonStatus();
    }

    private void buttonPressed(boolean isUp) {
        int floor = (int) floorSelector.getSelectedItem();
        Boolean[] status = floorButtonStatus.get(floor);
        status[isUp ? 0 : 1] = true;
        simulateService(floor, isUp);
        updateButtonStatus();
    }

    /**
     * While button is waiting to be serviced, show a red outline
     */
    private void updateButtonStatus() {
        int floor = (int) floorSelector.getSelectedItem();
        Boolean[] status = floorButtonStatus.get(floor);

        upButton.setEnabled(floor < numFloors && !status[0]);
        downButton.setEnabled(floor > 1 && !status[1]);

        int outlineThickness = 3; // For red outline
        upButton.setBorder(status[0] ? BorderFactory.createLineBorder(Color.RED, outlineThickness) : BorderFactory.createLineBorder(Color.GRAY, outlineThickness));
        downButton.setBorder(status[1] ? BorderFactory.createLineBorder(Color.RED, outlineThickness) : BorderFactory.createLineBorder(Color.GRAY, outlineThickness));
    }


    // This simulates the elevator being called to a floor. Right now it uses a 2-second wait, but it should be called by the scheduler instead when a request gets serviced by an elevator
    private void simulateService(int floor, boolean isUp) {
        Timer timer = new Timer(2000, e -> {
            Boolean[] status = floorButtonStatus.get(floor);
            status[isUp ? 0 : 1] = false;
            updateButtonStatus();
        });
        timer.setRepeats(false);
        timer.start();
    }

}
