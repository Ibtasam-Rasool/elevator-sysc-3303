import java.lang.reflect.Type;

public class Scheduler implements Runnable{

    private SchedulerState state;
    private Floor floor;
    private TaskData task;
    private Boolean haveTask;
    private int systemClock;

    public Scheduler (){
        this.state = new SchedulerIdleState();
        task = null;
        haveTask = false;
        systemClock = 0;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        while (true){

            systemClock++;


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
                state.nextState(this);
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        state.nextState(this);
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
        if(!(state instanceof SchedulerAcquireTaskState)){
            state = new SchedulerWaitingForElevatorState();
        }else {
            state.nextState(this);
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

    /**
     * Return task given to scheduler by floor
     * @return scheduled task
     */
    public TaskData getTask() {
        return task;
    }

    /**
     * Return whether a task is availible
     * @return true is a task is availible, false otherwise
     */
    public Boolean doesHaveTask() {
        return haveTask;
    }

    /**
     * @return schedulers state
     */
    public SchedulerState getState(){
        return state;
    }

    /**
     * @return schedulers state
     */
    public void setState(SchedulerState state){
        this.state = state;
    }
}
