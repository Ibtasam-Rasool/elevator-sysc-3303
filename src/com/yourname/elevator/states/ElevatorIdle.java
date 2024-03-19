package com.yourname.elevator.states;

import com.yourname.elevator.Elevator;

public class ElevatorIdle implements ElevatorStates{
    /**
     * Represents event telling elevator to close doors
     *
     * @param context represents the elevator
     */
    @Override
    public void closeDoors(Elevator context) {
        System.out.println("Elevator doors cannot open or close while in Idle state");
    }

    /**
     * Represents event telling elevator to open doors
     *
     * @param context represents the elevator
     */
    @Override
    public void openDoors(Elevator context) {
        System.out.println("Elevator doors cannot open or close while in Idle state");
    }

    /**
     * Represents event elevator button pressed
     *
     * @param context represents the elevator
     */
    @Override
    public void buttonPressed(Elevator context) {

    }

    /**
     * Displays current elevator state
     *
     * @param context represents the elevator
     * @return
     */
    @Override
    public void displayState(Elevator context) {
        System.out.println("IDLE");
    }

    /**
     * Event where elevator told to move to floor
     *
     * @param context represents elevator
     */
    @Override
    public void moveElevator(Elevator context) {
        int currentFloor = context.getCurrentFloor();
        int initialFloor = context.getInitialFloor();
        if (currentFloor != initialFloor) {
            System.out.println("Elevator " + context.getElevatorId() + " is moving to the initial floor " + initialFloor);
            //1 second per floor difference
            int floorDifference = Math.abs(initialFloor - currentFloor);
            try {
                Thread.sleep(1000 * floorDifference);  // Simulate moving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context.setCurrentFloor(initialFloor); // Update current floor
        }
        context.setState(new ElevatorLoadingPassengers());
    }
}
