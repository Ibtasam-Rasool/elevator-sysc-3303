package com.yourname.elevator;

import java.util.Scanner;

public class ElevatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of elevators:");

        int numberOfElevators;
        while (true) {
            if (scanner.hasNextInt()) {
                numberOfElevators = scanner.nextInt();
                if (numberOfElevators > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number:");
                }
            } else {
                System.out.println("Invalid input. Please enter a number:");
                scanner.next(); // Consume the invalid input
            }
        }

        for (int i = 1; i <= numberOfElevators; i++) {
            int elevatorId = i;
            int port = 5000 + elevatorId; // Assign a unique port for each elevator

            try {
                Elevator elevator = new Elevator(elevatorId, port);
                Thread elevatorThread = new Thread(elevator);
                elevatorThread.start();
                System.out.println("Elevator " + elevatorId + " started on port " + port);
            } catch (Exception e) {
                System.err.println("Error starting elevator " + elevatorId);
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
