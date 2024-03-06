package com.yourname.elevator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        ArrayList<TaskData> taskList = csvReader.parser("src\\ElevatorCallSheet - Sheet1.csv");

        for (TaskData task : taskList) {
            System.out.println(task);
        }

        try {
            Scheduler scheduler = new Scheduler(taskList);
            Thread schedulerThread = new Thread(scheduler);
            schedulerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
