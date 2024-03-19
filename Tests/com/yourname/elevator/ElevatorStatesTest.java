package com.yourname.elevator;

import com.yourname.elevator.states.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElevatorStatesTest {

    private Elevator elevator;

    @BeforeEach
    void setUp() throws Exception {
        elevator = new Elevator(1, 1000);
    }

    @AfterEach
    void tearDown() {
        elevator.close();
    }

    @Test
    void testIdleToLoadingTransition() {
        elevator.setState(new ElevatorIdle());
        assertTrue(elevator.getState() instanceof ElevatorIdle);
        elevator.moveElevator();
        assertTrue(elevator.getState() instanceof ElevatorLoadingPassengers);
    }

    @Test
    void testLoadingToMovingTransition() {
        elevator.setState(new ElevatorLoadingPassengers());
        assertTrue(elevator.getState() instanceof ElevatorLoadingPassengers);
        //elevator.closeDoors();
        elevator.openDoors();
        assertTrue(elevator.getState() instanceof ElevatorMovingToDestination);
    }

    @Test
    void testMovingToUnloadingTransition() {
        elevator.setState(new ElevatorMovingToDestination());
        assertTrue(elevator.getState() instanceof ElevatorMovingToDestination);
        elevator.moveElevator();
        assertTrue(elevator.getState() instanceof ElevatorUnloadingPassengers);
    }

    @Test
    void testUnloadingToIdleTransition() {
        elevator.setState(new ElevatorUnloadingPassengers());
        assertTrue(elevator.getState() instanceof ElevatorUnloadingPassengers);
        elevator.openDoors();
        assertTrue(elevator.getState() instanceof ElevatorIdle);
    }
}
