package com.yourname.elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GUI to simulate elevator movement between floors.
 *
 * @Author: Daniel Godfrey
 */
class ElevatorSimulationWindow extends JPanel {
    private final int carWidth = 40;
    private final int carHeight = 40;
    private final int[] carPosition = {60, 50}; // Initial position of elevator car
    private int moveY = -1; // Y coordinate for vertical movement of elevator
    private final int velocity = 10; // Pixels moved per timer tick
    private int[] stoppingPoints; // Positions of stopping points (floors)
    private Timer timer;

    public ElevatorSimulationWindow(int numberOfStoppingPoints) {
        this.setPreferredSize(new Dimension(200, 800)); // Adjusted for vertical orientation
        this.setBackground(Color.WHITE);
        calculateStoppingPoints(numberOfStoppingPoints);
        carPosition[1] = stoppingPoints[0] - carHeight/2;

        // Mouse can control elevator movement, but this is mostly for testing purposes. Scheduler should call moveCar()
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int point : stoppingPoints) {
                    if (Math.abs(e.getY() - point) < 50) { // Find the closest stopping point within 50 pixels and set it as the destination
                        moveY = point; // Set destination point for vertical movement
                        startCarMovement();
                        break;
                    }
                }
            }
        });

        timer = new Timer(30, e -> moveCar(moveY)); // Timer triggers every 30 ms
    }

    private void startCarMovement() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Move the elevator car to a specified floor.
     *
     * @param destinationY the destination of the elevator car
     */
    private void moveCar(int destinationY) {
        if (destinationY != -1) {
            int carMidPoint = carPosition[1] + carHeight / 2;
            if (Math.abs(carMidPoint - destinationY) <= velocity) {
                carPosition[1] = destinationY - carHeight / 2;
                timer.stop();
            } else if (carMidPoint < destinationY) {
                carPosition[1] += velocity; // Move down
            } else if (carMidPoint > destinationY) {
                carPosition[1] -= velocity; // Move up
            }
            repaint();
        }
    }

    /**
     * Calculate the positions of each floor in the shaft when floors are placed at an equal distance from each other.
     *
     * @param numberOfStoppingPoints the number of floors
     */
    private void calculateStoppingPoints(int numberOfStoppingPoints) {
        stoppingPoints = new int[numberOfStoppingPoints];
        int distance = this.getPreferredSize().height / (numberOfStoppingPoints + 1);
        for (int i = 0; i < numberOfStoppingPoints; i++) {
            stoppingPoints[i] = this.getPreferredSize().height - (i + 1) * distance;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int elevatorStartY = stoppingPoints[stoppingPoints.length - 1] - 5; // Start of the elevator shaft
        int elevatorEndY = stoppingPoints[0] + 5; // End of the elevator shaft
        g.drawLine(100, elevatorStartY, 100, elevatorEndY);

        for (int i = 0; i < stoppingPoints.length; i++) {
            int point = stoppingPoints[i];
            g.fillOval(95, point - 5, 10, 10); // Small circles represent "floors"
            g.drawString(Integer.toString(i + 1), 110, point + 5); // Start at floor "1"
        }

        // Draw car to the left of the elevator
        g.fillRect(carPosition[0], carPosition[1], carWidth, carHeight);
    }
}


