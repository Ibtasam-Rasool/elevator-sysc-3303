import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Elevator Test class
 * @author Saad Sheikh
 */
class ElevatorTest {
    Elevator elevator;
    Scheduler scheduler;
    ArrayList<TaskData> taskDataList;

    @BeforeEach
    void setup() {
        Random random = new Random();
        scheduler = new Scheduler();
        taskDataList = new ArrayList<TaskData>();
        int numOfFloors = 4;
        int numOfElevators = 2;
        int numOfTasks = 5;

        for (int i = 0; i < numOfTasks; i++) {
            TaskData sampleTask = new TaskData(LocalTime.now().toString(), random.nextInt(1, numOfFloors + 1), "Up", random.nextInt(1, numOfElevators + 1));
            taskDataList.add(sampleTask);
        }
        elevator = new Elevator(scheduler, 1, numOfFloors);
    }

    @AfterEach
    void tearDown() {
        if (!(elevator.getState() instanceof MovingState)) {
            elevator.stopRunning();
        }
    }

    /**
     * Test for elevator being idle by default
     * @author Saad Sheikh
     */
    @Test
    void idleTest() {
        assertTrue(elevator.getState() instanceof ElevatorIdleState);
    }

    /**
     * Test for elevaotor receiving task and carrying it out
     * (end-to-end behaviour test):
     *      IdleState -> MovingState -> IdleState
     * @author Saad Sheikh
     */
    @Test
    void movingStateTest() {
        // Handing a task to the elevator and initializing floor to send message to
        scheduler.giveTask(taskDataList.get(0));
        scheduler.setFloor(new Floor(scheduler, taskDataList));

        // making sure elevator was idle before receiving task
        assertTrue(elevator.getState() instanceof ElevatorIdleState, "Elevator is not in idle before acquiring tasks");

        // starting the elevator on another thread
        Thread elevatorThread = new Thread(() -> elevator.run());
        elevatorThread.start();

        // checking status of elevator every 1/10th of a second to see if it changes
        ElevatorState elevatorState = null;
        boolean stateChanged = false;
        long checkPeriod = 12000; // to make sure the elevator has enough time finish tasks
        long startCheckingTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startCheckingTime < checkPeriod){
            if(!(elevator.getState() instanceof ElevatorIdleState)){
                elevatorState = elevator.getState();
            }

            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // checking that elevator did change state, and went back to idle after completing task
        assertTrue(elevatorState instanceof MovingState, "Elevator did not change state to MovingState when it was running");
        assertTrue(elevator.getState() instanceof ElevatorIdleState, "Elevator did not revert back to IdleState when it had no more tasks");
    }
}