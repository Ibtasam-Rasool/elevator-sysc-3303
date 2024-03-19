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
        assertTrue(elevator.displayState() instanceof ElevatorIdle);
        elevator.moveElevator();
        assertTrue(elevator.displayState() instanceof ElevatorLoadingPassengers);
    }

    @Test
    void testLoadingToMovingTransition() {
        elevator.setState(new ElevatorLoadingPassengers());
        assertTrue(elevator.displayState() instanceof ElevatorLoadingPassengers);
        //elevator.closeDoors();
        elevator.openDoors();
        assertTrue(elevator.displayState() instanceof ElevatorMovingToDestination);
    }

    @Test
    void testMovingToUnloadingTransition() {
        elevator.setState(new ElevatorMovingToDestination());
        assertTrue(elevator.displayState() instanceof ElevatorMovingToDestination);
        elevator.moveElevator();
        assertTrue(elevator.displayState() instanceof ElevatorUnloadingPassengers);
    }

    @Test
    void testUnloadingToIdleTransition() {
        elevator.setState(new ElevatorUnloadingPassengers());
        assertTrue(elevator.displayState() instanceof ElevatorUnloadingPassengers);
        elevator.openDoors();
        assertTrue(elevator.displayState() instanceof ElevatorIdle);
    }
}
