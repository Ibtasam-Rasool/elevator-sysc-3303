public class Scheduler implements Runnable{


    private Floor floor;
    private TaskData task;
    private Boolean haveTask;

    public Scheduler (){
        task = null;
        haveTask = false;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        while (true){

        }

    }

    /**
     * Used by elevator to acquire a task
     * @return the current task being handled
     * @author Ibtasam Rasool
     */
    synchronized public TaskData acquireTask() {
        while (!haveTask){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        haveTask = false;
        notifyAll();
        return task;

    }

    /**
     * Takes in a task from the floor
     * @param task task received from floor
     * @author Ibtasam Rasool
     */
    synchronized public void giveTask(TaskData task){
        while (haveTask){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.task = task;
        haveTask = true;
        notifyAll();

    }

    /**
     * sends message to floor
     * @param message to be sent to floor
     * @author Ibtasam Rasool
     */
    synchronized public void sendToFloor(String message) {
        floor.messageChannel(message);
    }

    /**
     * Sets a floor for scheduler to use but will change this when implementing multiple floors
     * @param floor floor to be set and receive  information from
     *
     */
    public void setFloor(Floor floor){
        this.floor = floor;
    }

}
