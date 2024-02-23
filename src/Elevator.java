import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * interface to represent Elevator state in a state machine
 * @author Saad Sheikh
 */
interface ElevatorState {
    void selectDestination(Elevator elevator, int floor);
    void reachedDestination(Elevator elevator);
    void displayState();
}

/**
 * Concrete state class representing the state when elevator is idle
 * @author Saad Sheikh.
 */
class ElevatorIdleState implements ElevatorState{
    @Override
    public void selectDestination(Elevator elevator, int floor) {
        ArrayList<DestinationButton> buttonList = elevator.getDestinationButtonList();
        buttonList.get(floor-1).pressButton();
        elevator.setState(new MovingState());
        System.out.println("Elevator has started moving.");
    }

    @Override
    public void reachedDestination(Elevator elevator) {
        System.out.println("Elevator has not selected destinations.");
    }

    @Override
    public void displayState() {
        System.out.println("Elevator is idle.");
    }
}

/**
 * Concrete state class representing the state when elevator is idle
 * @author Saad Sheikh
 */
class MovingState implements ElevatorState{

    @Override
    public void selectDestination(Elevator elevator, int floor) {
        ArrayList<DestinationButton> buttonList = elevator.getDestinationButtonList();
        buttonList.get(floor-1).pressButton();
        System.out.println("Elevator is already moving.");
    }

    @Override
    public void reachedDestination(Elevator elevator) {
        ArrayList<DestinationButton> buttonList = elevator.getDestinationButtonList();
        boolean finishedAllRequests = true;
        for(int i = 0; i < buttonList.size(); i++){
            if(buttonList.get(i).isPressed()){
                finishedAllRequests = false;
                break;
            }
        }
        if(finishedAllRequests){
            System.out.println("Elevator has become idle.");
            elevator.setState(new ElevatorIdleState());
        }
    }

    @Override
    public void displayState() {
        System.out.println("Elevator is moving");
    }
}

public class Elevator implements Runnable{


    private final Scheduler scheduler;
    private final int id;
    private int currentFloor;
    private Random random = new Random();
    private TaskData taskData;
    private ArrayList<DestinationButton> destinationButtonList;
    private Door elevatordoor;
    private Motor motor;
    private ElevatorState currentState;
    private int occupancy;

    private final int TravelTime = 1000; // in milliseconds
    private final int LoadingTime = 2000; // in milliseconds
    private final int DeLoadingTime = 2000; // in milliseconds

    /**
     *
     * @param scheduler reference to the scheduler
     * @param id unique id for the elevator
     * @param numOfFloors number of floors in simulation
     */
    public Elevator(Scheduler scheduler, int id, int numOfFloors){
        this.scheduler = scheduler;
        this.id = id;
        currentFloor = 1;
        elevatordoor = new Door(id);
        // initialize all destination buttons for elevator
        destinationButtonList = new ArrayList<DestinationButton>();
        for(int i = 1; i <= numOfFloors; i++){
            destinationButtonList.add(new DestinationButton(i));
        }
        occupancy = 0;
        motor = new Motor(this);

    }

    /**
     * Runs this operation.
     * @author Ibtasam Rasool, Quinton Tracey
     */
    @Override
    public void run() {
        while(true){
            taskData = scheduler.acquireTask();
            acknowledgeTask(taskData);

            System.out.println("------Start Of Task------");
            //while the elevator has a task
            while(Objects.nonNull(taskData)){
                try {
                    //move to occupants if needed
                    motor.moveToOccupantFloor(currentFloor, taskData.getInitialFloor());
                    //load occupants
                    sleep(LoadingTime);
                    //move to destination
                    motor.moveToDestinationFloor(currentFloor, taskData.getDestinationFloor());
                    //unload occupants
                    sleep(DeLoadingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                taskData = null;
            }

            System.out.println("------End Of Task------");

        }

    }

    /**
     * changes the state of the elevator
     * @param state the new state to set the elevator to
     * @author Saad Sheikh
     */
    public void setState(ElevatorState state){
        this.currentState = state;
    }

    /**
     * @return the current state of the elevator
     * @author Saad Sheikh
     */
    public ElevatorState getState(){
        return currentState;
    }

    /**
     * sends back confirmation of having received a task
     * @param taskData Task received
     * @author Ibtasam Rasool
     */
    private void acknowledgeTask(TaskData taskData) {

        System.out.println("ELEVATOR " + id + " Has confirmed this task: " + taskData);
        scheduler.sendToFloor("ELEVATOR " + id + " Has confirmed this task: " + taskData);

    }

    /**
     * @return the array of destination buttons in the elevator
     */
    protected ArrayList<DestinationButton> getDestinationButtonList(){
        return destinationButtonList;
    }

    /**
     * Sets the number of occupants
     * @param occupants int, the number of occupants
     */
    public void setOccupancy(int occupants){
        this.occupancy = occupants;
    }

    /**
     * Returns the number of groups in the elevator
     * @return int, the number of occupant groups
     */
    public int getOccupancy(){
        return occupancy;
    }

    /**
     *  prints out the elevators current floor and contents
     */
    public void printElavatorStatus(){
        System.out.println("ELEVATOR " + id + " is currently at floor " + currentFloor + " with " + occupancy + " group(s)");
    }

    /**
     * returns the id of the elevator
     * @return int, id of the elevator
     */
    public int getId(){
        return id;
    }

    /**
     * Moves the elevator in the specified direction
     * @param direction String, the direction 'Up' or 'Down' that the elevator is being moved
     */
    public void moveElevator(String direction){
        if(direction.equals("Up")){
            currentFloor+=1;
        } else if(direction.equals("Down")){
            currentFloor-=1;
        }
    }

}