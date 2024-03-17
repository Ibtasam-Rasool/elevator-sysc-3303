package com.yourname.elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ElevatorSimulationWindow extends JPanel {
    private final int carWidth = 40; // Width of the car (square)
    private final int carHeight = 40; // Height of the car
    private final int[] carPosition = {60, 50}; // Initial position of the car, adjusted to be on the left
    private int moveY = -1; // Destination Y coordinate for vertical movement
    private final int velocity = 10; // Pixels moved per timer tick
    private int[] stoppingPoints; // Positions of stopping points for vertical orientation
    private Timer timer;

    public ElevatorSimulationWindow(int numberOfStoppingPoints) {
        this.setPreferredSize(new Dimension(200, 800)); // Adjusted for vertical orientation
        this.setBackground(Color.WHITE);
        calculateStoppingPoints(numberOfStoppingPoints);
        carPosition[1] = stoppingPoints[0] - carHeight/2;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Find the closest stopping point and set it as the destination
                for (int point : stoppingPoints) {
                    if (Math.abs(e.getY() - point) < 50) { // Adjusted for vertical clicks
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

    private void moveCar(int destinationY) {
        if (destinationY != -1) {
            int carMidPoint = carPosition[1] + carHeight / 2;
            if (Math.abs(carMidPoint - destinationY) <= velocity) {
                carPosition[1] = destinationY - carHeight / 2; // Align car's midpoint with stopping point vertically
                timer.stop(); // Stop the car when it reaches the destination
            } else if (carMidPoint < destinationY) {
                carPosition[1] += velocity; // Move down
            } else if (carMidPoint > destinationY) {
                carPosition[1] -= velocity; // Move up
            }
            repaint();
        }
    }

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

        // Adjusted to draw the elevator from the bottom to the top stopping point
        int elevatorStartY = stoppingPoints[stoppingPoints.length - 1] - 5; // Start of the elevator
        int elevatorEndY = stoppingPoints[0] + 5; // End of the elevator
        g.drawLine(100, elevatorStartY, 100, elevatorEndY);

        // Draw stopping points and labels vertically, starting labels from "1"
        for (int i = 0; i < stoppingPoints.length; i++) {
            int point = stoppingPoints[i];
            g.fillOval(95, point - 5, 10, 10); // Small circles for stopping points, adjusted for left side
            // Adjusted label numbering to start from "1"
            g.drawString(Integer.toString(i + 1), 110, point + 5);
        }

        // Draw the car on the left side of the elevator
        g.fillRect(carPosition[0], carPosition[1], carWidth, carHeight);
    }
}


