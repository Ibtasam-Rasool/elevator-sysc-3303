package com.yourname.elevator;

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
        assertEquals(taskList.get(0).getInitialFloor(), 2);
        assertEquals(taskList.get(1).getInitialFloor(), 4);
    }

    @Test
    void setFloor() {
        assertEquals(taskList.get(0).getInitialFloor(), 2);
        taskList.get(0).setInitialFloor(15);
        assertEquals(taskList.get(0).getInitialFloor(), 15);

        assertEquals(taskList.get(1).getInitialFloor(), 4);
        taskList.get(1).setInitialFloor(0);
        assertEquals(taskList.get(1).getInitialFloor(), 0);
        taskList.get(1).setInitialFloor(-2);
        assertEquals(taskList.get(1).getInitialFloor(), -2);
        tearDown();
    }

    @Test
    void getTime() {
        assertEquals(taskList.get(0).getTimeString(), "14:05:15");
        assertEquals(taskList.get(1).getTimeString(), "15:06:05");
    }

    @Test
    void setTime() {
        assertEquals(taskList.get(0).getTimeString(), "14:05:15");
        taskList.get(0).setTimeString("11:11:11");
        assertEquals(taskList.get(0).getTimeString(), "11:11:11");
        tearDown();
    }

    @Test
    void getElevatorNumber() {
        assertEquals(taskList.get(0).getDestinationFloor(), 4);
        assertEquals(taskList.get(1).getDestinationFloor(), 1);
    }

    @Test
    void setElevatorNumber() {
        assertEquals(taskList.get(0).getDestinationFloor(), 4);
        taskList.get(0).setDestinationFloor(10);
        assertEquals(taskList.get(0).getDestinationFloor(), 10);
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