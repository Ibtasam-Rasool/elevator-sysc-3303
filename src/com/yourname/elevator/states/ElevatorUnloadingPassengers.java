package com.yourname.elevator.states;

import com.yourname.elevator.Elevator;

public class ElevatorUnloadingPassengers implements ElevatorStates{
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
        System.out.println("Elevator " + context.getId() + " is opening doors and unloading passengers at floor " + context.getCurrentFloor());
        //3 second load time
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setState(new ElevatorIdle());
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
     */
    @Override
    public void displayState(Elevator context) {
        System.out.println("UNLOAD PASS");
    }

    /**
     * Event where elevator told to move to floor
     *
     * @param context represents elevator
     */
    @Override
    public void moveElevator(Elevator context) {

    }
}
