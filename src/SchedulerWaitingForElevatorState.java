public class SchedulerWaitingForElevatorState implements SchedulerState{
    /**
     * next state of scheduler
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void nextState(Scheduler context) {
        System.out.printf("Sending Task To Elevator (waiting on elevator to send task) going to idle state");
        context.setState(new SchedulerIdleState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {
        System.out.println("Task Received from floor before any elevator requests");
    }
}
