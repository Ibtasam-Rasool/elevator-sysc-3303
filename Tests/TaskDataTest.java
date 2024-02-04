import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataTest {
    ArrayList<TaskData> taskList;
    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
        taskList.add(new TaskData("14:05:15", 2, "Up", 4));
        taskList.add(new TaskData("15:06:05", 4, "Down", 1));
    }

    @AfterEach
    void tearDown() {
        taskList.add(new TaskData("14:05:15", 2, "Up", 4));
        taskList.add(new TaskData("15:06:05", 4, "Down", 1));
    }

    @Test
    void getFloor() {
        assertEquals(taskList.get(0).getFloor(), 2);
        assertEquals(taskList.get(1).getFloor(), 4);
    }

    @Test
    void setFloor() {
        assertEquals(taskList.get(0).getFloor(), 2);
        taskList.get(0).setFloor(15);
        assertEquals(taskList.get(0).getFloor(), 15);

        assertEquals(taskList.get(1).getFloor(), 4);
        taskList.get(1).setFloor(0);
        assertEquals(taskList.get(1).getFloor(), 0);
        taskList.get(1).setFloor(-2);
        assertEquals(taskList.get(1).getFloor(), -2);
        tearDown();
    }

    @Test
    void getTime() {
        assertEquals(taskList.get(0).getTime(), "14:05:15");
        assertEquals(taskList.get(1).getTime(), "15:06:05");
    }

    @Test
    void setTime() {
        assertEquals(taskList.get(0).getTime(), "14:05:15");
        taskList.get(0).setTime("11:11:11");
        assertEquals(taskList.get(0).getTime(), "11:11:11");
        tearDown();
    }

    @Test
    void getElevatorNumber() {
        assertEquals(taskList.get(0).getElevatorNumber(), 4);
        assertEquals(taskList.get(1).getElevatorNumber(), 1);
    }

    @Test
    void setElevatorNumber() {
        assertEquals(taskList.get(0).getElevatorNumber(), 4);
        taskList.get(0).setElevatorNumber(10);
        assertEquals(taskList.get(0).getElevatorNumber(), 10);
        tearDown();
    }

    @Test
    void getButton() {
        assertEquals(taskList.get(0).getButton(), "Up");
        assertEquals(taskList.get(1).getButton(), "Down");
    }

    @Test
    void setButton() {
        assertEquals(taskList.get(0).getButton(), "Up");
        taskList.get(0).setButton("Down");
        assertEquals(taskList.get(0).getButton(), "Down");
        tearDown();
    }
}