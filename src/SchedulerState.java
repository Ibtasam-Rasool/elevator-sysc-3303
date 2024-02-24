public interface SchedulerState {

    /**
     * next state of scheduler
     * @param context represents the Scheduler object
     */
    void nextState(Scheduler context);

    /**
     * displays current scheduler state
     * @param context represents the Scheduler object
     */
    void displayCurrentState(Scheduler context);

}
