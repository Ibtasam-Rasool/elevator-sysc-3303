import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Floor implements Runnable{


    private Random random = new Random();
    private final Scheduler scheduler;
    private List<TaskData> taskDataList;

    /**
     * initializes the floor class
     * @param scheduler scheduler to sent tasks to
     * @param taskDataList lists of tasks
     */
    public Floor(Scheduler scheduler, ArrayList taskDataList){
        this.scheduler = scheduler;
        this.taskDataList = taskDataList;
    }

    /**
     * Runs this operation.
     * @author Ibtasam Rasool
     */
    @Override
    public void run() {
        while (!taskDataList.isEmpty()){
            try {
                sleep(random.nextInt(1000, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            scheduler.giveTask(taskDataList.remove(0));
        }

    }

    /**
     * prints out message from scheduler
     * @param message message received
     * @author Ibtasam Rasool
     */
    public void messageChannel(String message) {
        System.out.println("THE FLOOR HAS RECEIVED THE MESSAGE: " + message);
    }

    /**
     * Return currently scheduled tasks
     *
     * @return taskDataList - list of tasts scheduled
     * @author: Daniel Godfrey
     */
    public List<TaskData> getTaskDataList() { return taskDataList; }
}
