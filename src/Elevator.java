import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Elevator implements Runnable {
    enum State {
        MOVING_UP,
        MOVING_DOWN,
        IDLE,
        RETURNING_TO_START
    }

    private final Scheduler scheduler;
    private final int id;
    private int currentFloor;
    private Random random = new Random();
    private TaskData taskData;
    private ArrayList<DestinationButton> destinationButtonList;
    private Door elevatordoor;
    private Motor motor;
    private int occupancy;
    private int startFloor;
    private State currentState;
    private LinkedList<Integer> taskQueue = new LinkedList<>();
    private boolean stopThread = false;

    private final int TravelTime = 1000; // in milliseconds
    private final int LoadingTime = 2000; // in milliseconds
    private final int DeLoadingTime = 2000; // in milliseconds

    public Elevator(int startFloor, Scheduler scheduler, int id, int numOfFloors) {
        this.startFloor = startFloor;
        this.currentFloor = startFloor;
        this.currentState = State.IDLE;
        this.scheduler = scheduler;
        this.id = id;
        this.elevatordoor = new Door(id);
        // initialize all destination buttons for elevator
        destinationButtonList = new ArrayList<DestinationButton>();
        for(int i = 1; i <= numOfFloors; i++){
            destinationButtonList.add(new DestinationButton(i));
        }
        this.occupancy = 0;
        this.motor = new Motor(this);
        this.stopThread = false;
    }

    public synchronized void addTask(int callFloor, int destinationFloor) {
        if (!taskQueue.contains(callFloor)) {
            taskQueue.add(callFloor);
            Collections.sort(taskQueue);
        }

        if (!taskQueue.contains(destinationFloor)) {
            taskQueue.add(destinationFloor);
            Collections.sort(taskQueue);
        }

        if (currentState == State.IDLE) {
            currentState = callFloor > currentFloor ? State.MOVING_UP : State.MOVING_DOWN;
        }

        notifyAll();
    }


    public synchronized void move() {
        while (taskQueue.isEmpty() && currentState != State.RETURNING_TO_START) {
            try {
                wait(); // Wait until there is a task
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (currentState == State.MOVING_UP) {
            moveUp();
        } else if (currentState == State.MOVING_DOWN) {
            moveDown();
        } else if (currentState == State.RETURNING_TO_START) {
            returnToStart();
        }
    }

    private void moveUp() {
        if (!taskQueue.isEmpty() && currentFloor < taskQueue.peek()) {
            currentFloor++;
            checkFloor();
        } else {
            currentState = State.IDLE;
        }
    }

    private void moveDown() {
        if (!taskQueue.isEmpty() && currentFloor > taskQueue.peek()) {
            currentFloor--;
            checkFloor();
        } else {
            currentState = State.IDLE;
        }
    }

    private synchronized void checkFloor() {
        if (!taskQueue.isEmpty() && currentFloor == taskQueue.peek()) {
            System.out.println("Stopping at floor " + currentFloor);
            taskQueue.poll();
            if (taskQueue.isEmpty()) {
                currentState = State.RETURNING_TO_START;
            }
        }
    }

    private void returnToStart() {
        if (currentFloor > startFloor) {
            currentFloor--;
        } else if (currentFloor < startFloor) {
            currentFloor++;
        } else {
            currentState = State.IDLE;
        }
    }

    public synchronized void stopElevator() {
        stopThread = true;
        notifyAll(); // Notify in case the thread is waiting
    }

    @Override
    public void run() {
        while (!stopThread) {
            move();
            try {
                Thread.sleep(1000); // Simulate elevator movement delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized State getState() {
        return currentState;
    }
}
