package com.yourname.elevator;

import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorMain {
    private static int numberOfElevators;
    private static int floorButtonPressed;
    private static int calledElevator;
    private static ArrayList<Elevator> elevatorList;
    public static void main(String[] args) {
        elevatorList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of elevators:");
        String elevatorFloorCommand;


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
                elevatorList.add(elevator);
                Thread elevatorThread = new Thread(elevator);
                elevatorThread.start();
                System.out.println("Elevator " + elevatorId + " started on port " + port);
            } catch (Exception e) {
                System.err.println("Error starting elevator " + elevatorId);
                e.printStackTrace();
            }
        }

        System.out.println("You can send floor command to any elevator by sending command elevator,floor");

        while (true){
            elevatorFloorCommand = scanner.next();
            if(isElevatorFloorCommandValid(elevatorFloorCommand)){

                for (Elevator e: elevatorList) {
                    if (e.getElevatorId() == calledElevator){
                        try {
                            e.buttonPressed(floorButtonPressed);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }else {
                System.out.println("Invalid floor command entered");
            }

        }


    }

    private static boolean isElevatorFloorCommandValid(String command){
        String[] parsedCommand = command.split(",");
        if ((Integer.valueOf(parsedCommand[0]) <= numberOfElevators)){
            calledElevator = Integer.valueOf(parsedCommand[0]);
        }else{
            return false;
        }
        if((Integer.valueOf(parsedCommand[1]) instanceof Integer)){
            floorButtonPressed = (Integer.valueOf(parsedCommand[1]));
        }else {
            return false;
        }

        return true;
    }

}
