package com.yourname.elevator.states;

import com.yourname.elevator.Elevator;

public interface ElevatorStates {

    /**
     * Represents event telling elevator to close doors
     * @param context represents the elevator
     */
    void closeDoors(Elevator context);

    /**
     * Represents event telling elevator to open doors
     * @param context represents the elevator
     */
    void openDoors(Elevator context);

    /**
     * Represents event elevator button pressed
     * @param context represents the elevator
     */
    void buttonPressed(Elevator context);

    /**
     * Displays current elevator state
     *
     * @param context represents the elevator
     * @return
     */
    void displayState(Elevator context);

    /**
     * Event where elevator told to move to floor
     * @param context represents elevator
     */
    void moveElevator(Elevator context);
}
