/**
 * Repersents the task data sent by the floor and received by elevator
 */
package com.yourname.elevator;
public class TaskData {

    private int initialFloor;
    private int time;
    private int destinationFloor;
    private String button;

    public TaskData(int time, int floor, String button, int destinationFloor) {
        this.initialFloor = floor;
        this.time = time;
        this.destinationFloor = destinationFloor;
        this.button = button;
    }


    public TaskData(String time, int floor, String button, int elevatorNumber) {
        this.initialFloor = floor;

        int hours = (Integer) Integer.parseInt(time.substring(0,2));
        int minutes = (Integer) Integer.parseInt(time.substring(3,5));
        int seconds = (Integer) Integer.parseInt(time.substring(6,8));
        int timeAfterStart = hours * 60 * 60 + minutes * 60 + seconds;
        this.time = timeAfterStart;

        this.destinationFloor = elevatorNumber;
        this.button = button;
    }

    public int getInitialFloor() {
        return initialFloor;
    }

    public void setInitialFloor(int initialFloor) {
        this.initialFloor = initialFloor;
    }

    public int getTimeInt() {
        return time;
    }

    public String getTimeString() {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        int curTime = time;

        while((curTime - 3600) >= 0){
            curTime-=3600;
            hours+=1;
        }

        while((curTime - 60) >= 0){
            curTime-=60;
            minutes+=1;
        }

        while((curTime - 1) >= 0){
            curTime-=1;
            seconds+=1;
        }

        String stringHours = Integer.toString(hours);
        String stringMinutes = Integer.toString(minutes);
        String stringSeconds = Integer.toString(seconds);

        if (stringHours.length() == 1) stringHours = "0" + stringHours;
        if (stringMinutes.length() == 1) stringMinutes = "0" + stringMinutes;
        if (stringSeconds.length() == 1) stringSeconds = "0" + stringSeconds;

        return  stringHours + ":" + stringMinutes + ":" + stringSeconds;
    }

    public void setTimeInt(int time) {
        this.time = time;
    }

    public void setTimeString(String time) {
        int hours = (Integer) Integer.parseInt(time.substring(0,2));
        int minutes = (Integer) Integer.parseInt(time.substring(3,5));
        int seconds = (Integer) Integer.parseInt(time.substring(6,8));
        int timeAfterStart = hours * 60 * 60 + minutes * 60 + seconds;
        this.time = timeAfterStart;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
    


    @Override
    public String toString() {
        return "TaskData{" +
                "floor=" + initialFloor +
                ", time=" + time +
                ", destinationFloor=" + destinationFloor +
                ", button='" + button + '\'' +
                '}';
    }
}