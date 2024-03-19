package com.yourname.elevator.states;

import com.yourname.elevator.Elevator;

public class ElevatorMovingToDestination implements ElevatorStates{
    /**
     * Represents event telling elevator to close doors
     *
     * @param context represents the elevator
     */
    @Override
    public void closeDoors(Elevator context) {
    }

    /**
     * Represents event telling elevator to open doors
     *
     * @param context represents the elevator
     */
    @Override
    public void openDoors(Elevator context) {
        System.out.println("Elevator doors cannot open or close while in Moving state");
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
    public ElevatorStates displayState(Elevator context) {
        System.out.println("MOVING TO DEST");
        return this;
    }

    /**
     * Event where elevator told to move to floor
     *
     * @param context represents elevator
     */
    @Override
    public void moveElevator(Elevator context) {
        if (context.getCurrentFloor() !=  context.getDestinationFloor()) {
            System.out.println("Elevator " + context.getElevatorId() + " is moving to the destination floor " + context.getDestinationFloor());
            //1 second per floor diff
            int floorDifference = Math.abs(context.getDestinationFloor() - context.getCurrentFloor());
            try {
                Thread.sleep(1000 * floorDifference);  // Simulate moving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context.setCurrentFloor(context.getDestinationFloor()); // Update current floor
        }
        context.setState(new ElevatorUnloadingPassengers());
    }
}
