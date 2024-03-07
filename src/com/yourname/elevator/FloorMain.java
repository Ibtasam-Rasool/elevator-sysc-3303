package com.yourname.elevator;

import java.util.ArrayList;
import java.util.Scanner;

public class FloorMain {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        ArrayList<TaskData> taskList = csvReader.parser("src\\ElevatorCallSheet - Sheet1.csv");

        for (TaskData task : taskList) {
            System.out.println(task);
        }

        Scanner scanner = null;
        try {
            Floor floor = new Floor(taskList);
            Thread floorThread = new Thread(floor);
            floorThread.start();
            floor.connectToScheduler();


            scanner = new Scanner(System.in);


            while (true) {
                System.out.println("Enter new passenger request in the format, hh:mm:ss,f,up/down,cb");
                System.out.println("Where where hh:mm:ss is the time, f is the floor of the passenger, up/down is the direction they are headed, and cb is the destination floor");

                //read user input
                String request = scanner.nextLine();
                String[] row = request.split(",");

                System.out.println(row.length);
                if (row.length == 4) {
                    int time = parseTime(row[0]);
                    int initialFloor = Integer.parseInt(row[1].trim());
                    String button = row[2].trim();
                    int destinationFloor = Integer.parseInt(row[3].trim());

                    floor.addtask(new TaskData(time, initialFloor, button, destinationFloor));

                } else {
                    System.out.println("Error you entered an invalid format");
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    private static int parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }
}

