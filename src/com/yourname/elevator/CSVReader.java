package com.yourname.elevator;

import java.io.*;
import java.util.ArrayList;

public class CSVReader {

    public ArrayList<TaskData> parser(String fileLocation) {
        BufferedReader reader = null;
        String line = "";
        ArrayList<TaskData> taskList = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileLocation));
            reader.readLine();  // Skip column names
            reader.readLine();  // Skip example, if any

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");

                // Assuming format: Time, Floor, Floor Button, Car Button
                int time = parseTime(row[0]);
                int initialFloor = Integer.parseInt(row[1].trim());
                String button = row[2].trim();
                int destinationFloor = Integer.parseInt(row[3].trim());

                taskList.add(new TaskData(time, initialFloor, button, destinationFloor));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return taskList;
    }

    private int parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }
}
