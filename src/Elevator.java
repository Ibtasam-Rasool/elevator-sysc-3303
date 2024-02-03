import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable{


    private final Scheduler scheduler;
    private final int id;
    private int currentFloor;
    private Random random = new Random();
    private TaskData taskData;
    private ArrayList<DestinationButton> destinationButtonList;
    private Motor motor;

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
        // initialize all destination buttons for elevator
        destinationButtonList = new ArrayList<DestinationButton>();
        for(int i = 1; i <= numOfFloors; i++){
            destinationButtonList.add(new DestinationButton(i));
        }
        motor = new Motor();

    }

    /**
     * Runs this operation.
     * @author Ibtasam Rasool
     */
    @Override
    public void run() {
        while(true){

            try {
                sleep(random.nextInt(1000, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            taskData = scheduler.acquireTask();
            acknowledgeTask(taskData);

        }

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
     * moves to specified floor
     * @param floorNum the number of the destination floor
     * @author Saad Sheikh
     */
    protected void moveToFloor(int floorNum){
        int floorDiff = Math.abs(currentFloor - floorNum);
        motor.moveToFloor(floorDiff);
    }

}
