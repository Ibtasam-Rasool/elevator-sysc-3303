import static java.lang.Thread.sleep;

public class Motor {
    private volatile boolean running;
    private int floorDiff;
    private Elevator elevator;
    private final int TravelTime = 1000; // in milliseconds

    /**
     * constructor for motor
     * @author Saad Sheikh, Quinton Tracey
     */
    public Motor(Elevator elevator) {
        running = false;
        this.elevator = elevator;
    }

    /**
     *   moves towards the occupant's floor
     * @param currentFloor int, the current floor of the elevator
     * @param initialFloor int, the initial floor of the occupants
     * @throws InterruptedException
     * @author Quinton Tracey
     */
    public void moveToOccupantFloor(int currentFloor, int initialFloor)throws InterruptedException {
        //state stuff
        elevator.printElavatorStatus();
        while(currentFloor != initialFloor){
            //if destination floor is above
            if (currentFloor < initialFloor){
                sleep(TravelTime);
                currentFloor += 1;
                elevator.moveElevator("Up");
            } //destination floorn is below
            else  {
                sleep(TravelTime);
                currentFloor -= 1;
                elevator.moveElevator("Down");
            }
            elevator.printElavatorStatus();
        }

        System.out.println("ELEVATOR " + elevator.getId() + " is currently at floor " + currentFloor + " loading a group of occupants");

        elevator.setOccupancy(elevator.getOccupancy() + 1);
    }

    /**
     *   moves towards the destination floor
     * @param currentFloor int, the current floor of the elevator
     * @param destinationFloor int, the destination floor
     * @throws InterruptedException
     * @author Quinton Tracey
     */
    public void moveToDestinationFloor(int currentFloor, int destinationFloor) throws InterruptedException {
        elevator.printElavatorStatus();
        //state stuff
        while (currentFloor != destinationFloor) {
            //if destination floor is above
            if (currentFloor < destinationFloor) {
                sleep(TravelTime);
                currentFloor += 1;
                elevator.moveElevator("Up");
            } //destination floorn is below
            else {
                sleep(TravelTime);
                currentFloor -= 1;
                elevator.moveElevator("Down");
            }
            elevator.printElavatorStatus();
        }

        System.out.println("ELEVATOR " + elevator.getId() + " is currently at floor " + currentFloor + " unloading a group of occupants");

        elevator.setOccupancy(elevator.getOccupancy() - 1);
    }

}
