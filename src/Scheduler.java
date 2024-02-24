interface SchedulerState {

    /**
     * Represents event where elevator has arrived
     * @param context represents scheduler
     */
    void elevatorArrived(Scheduler context);

    /**
     * Represents event where elevator is requested
     * @param context represents scheduler
     */
    void elevatorRequested(Scheduler context);

    /**
     * represents event where elevator has been found
     * @param context
     */
    void schedulerElevatorFoundState(Scheduler context);

    /**
     * displays current scheduler state
     * @param context represents the Scheduler object
     */
    void displayCurrentState(Scheduler context);

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     * @param context represents the Scheduler object
     */
    void finishedElevatorInteraction(Scheduler context);

}

class SchedulerWaitingState implements SchedulerState{

    /**
     * Represents event where elevator has arrived
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorArrived(Scheduler context) {
        context.setState(new ElevatorArrivedHandlerState());

    }

    /**
     * Represents event where elevator is requested
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorRequested(Scheduler context) {
        context.setState(new SchedulerFindElevatorState());
    }

    /**
     * represents event where elevator has been found if in waiting this essentially means that
     * command is coming from within elevator
     *
     * @param context
     */
    @Override
    public void schedulerElevatorFoundState(Scheduler context) {
        context.setState(new SchedulerSendRequestToElevatorState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        System.out.println("IN WAITING STATE");
    }

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void finishedElevatorInteraction(Scheduler context) {
        System.out.println("IN WAITING STATE NO INTERACTION FINISHED");
    }
}

class ElevatorArrivedHandlerState implements SchedulerState{

    /**
     * Represents event where elevator has arrived
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorArrived(Scheduler context) {

        System.out.println("Elevator already arrived");

    }

    /**
     * Represents event where elevator is requested
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorRequested(Scheduler context) {
        System.out.println("Elevator already Arrived");
    }

    /**
     * represents request to send to elevator
     *
     * @param context
     */
    @Override
    public void schedulerElevatorFoundState(Scheduler context) {
        context.setState(new SchedulerSendRequestToElevatorState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        System.out.println("Elevator has arrived state");
    }

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void finishedElevatorInteraction(Scheduler context) {
        System.out.println("Cant finish interaction without passing request");
    }
}

class SchedulerFindElevatorState implements SchedulerState{

    /**
     * Represents event where elevator has arrived
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorArrived(Scheduler context) {
        System.out.println("Still looking for elevator");
    }

    /**
     * Represents event where elevator is requested
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorRequested(Scheduler context) {
        System.out.println("cant request while in finding elevator state");
    }

    /**
     * represents event where elevator has been found
     *
     * @param context
     */
    @Override
    public void schedulerElevatorFoundState(Scheduler context) {
        context.setState(new SchedulerSendRequestToElevatorState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        System.out.println("Looking for elevator state");
    }

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void finishedElevatorInteraction(Scheduler context) {

    }
}

class SchedulerSendRequestToElevatorState implements SchedulerState {

    /**
     * Represents event where elevator has arrived
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorArrived(Scheduler context) {
        System.out.println("elevator already arrived");
    }

    /**
     * Represents event where elevator is requested
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorRequested(Scheduler context) {
        System.out.printf("cant request while sending request");
    }

    /**
     * represents event where elevator has been found
     *
     * @param context
     */
    @Override
    public void schedulerElevatorFoundState(Scheduler context) {
        System.out.println("elevator already found");
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        System.out.println("Sending elevator request");
    }

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void finishedElevatorInteraction(Scheduler context) {
        context.setState(new SchedulerWaitingState());
    }
}






public class Scheduler implements Runnable, SchedulerState{

    private SchedulerState state;
    private Floor floor;
    private TaskData task;
    private Boolean haveTask;
    private int systemClock;

    public Scheduler (){
        this.state = new SchedulerWaitingState();
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
     * displays current state
     */
    public void displayState(){
        state.displayCurrentState(this);
    }

    /**
     * @return schedulers state
     */
    public void setState(SchedulerState state){
        this.state = state;
    }

    /**
     * Represents event where elevator has arrived
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorArrived(Scheduler context) {
        state.elevatorArrived(this);
    }

    /**
     * Represents event where elevator is requested
     *
     * @param context represents scheduler
     */
    @Override
    public void elevatorRequested(Scheduler context) {
        state.elevatorRequested(this);
    }

    /**
     * represents event where elevator has been found
     *
     * @param context
     */
    @Override
    public void schedulerElevatorFoundState(Scheduler context) {
        state.schedulerElevatorFoundState(this);
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        state.displayCurrentState(this);
    }

    /**
     * handles event where scheduler has finsihed handling an elevator interaction
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void finishedElevatorInteraction(Scheduler context) {
        state.finishedElevatorInteraction(this);
    }
}
