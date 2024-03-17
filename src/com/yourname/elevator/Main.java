package com.yourname.elevator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JFrame{
    private ElevatorSimulationWindow elevatorWindow;

    public Main(int stoppingPoints) {
        this.elevatorWindow = new ElevatorSimulationWindow(stoppingPoints);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(elevatorWindow);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        String input = JOptionPane.showInputDialog("Enter the number of floors:");
        int stoppingPoints = Integer.parseInt(input);
        SwingUtilities.invokeLater(() -> new Main(stoppingPoints));

        try {
            Scheduler scheduler = new Scheduler();
            Thread schedulerThread = new Thread(scheduler);
            schedulerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
