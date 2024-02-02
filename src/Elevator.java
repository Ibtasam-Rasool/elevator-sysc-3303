import java.util.Random;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable{


    private final Scheduler scheduler;
    private final int id;
    private Random random = new Random();
    private TaskData taskData;

    public Elevator(Scheduler scheduler, int id){
        this.scheduler = scheduler;
        this.id = id;
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



}
