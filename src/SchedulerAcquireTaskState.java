public class SchedulerAcquireTaskState implements SchedulerState{
    /**
     * next state of scheduler
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void nextState(Scheduler context) {
        System.out.println("changing State To idle after sending task to elevator");
        context.setState(new SchedulerIdleState());
    }

    /**
     * displays current scheduler state
     *
     * @param context represents the Scheduler object
     */
    @Override
    public void displayCurrentState(Scheduler context) {

        System.out.println("acquiring a task state");

    }
}
