public class SchedulerIdleState implements SchedulerState{
    /**
     * next state of scheduler
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void nextState(Scheduler context) {
        System.out.println("Elevator is asking for a task sending to acquire task state");
        context.setState(new SchedulerAcquireTaskState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {

        System.out.println("Scheduler is IDLE Right Now");

    }
}
