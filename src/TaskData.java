import java.util.Date;

/**
 * Repersents the task data sent by the floor and received by elevator
 */
public class TaskData {

    private int floor;
    private String time;
    private int elevatorNumber;
    private String button;

    public TaskData(String time, int floor, String button, int elevatorNumber) {
        this.floor = floor;
        this.time = time;
        this.elevatorNumber = elevatorNumber;
        this.button = button;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(int elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
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
                "floor=" + floor +
                ", time=" + time +
                ", elevatorNumber=" + elevatorNumber +
                ", button='" + button + '\'' +
                '}';
    }
}
