package com.yourname.elevator;

public class SchedulingState implements SchedulerState{

    @Override
    public void handleEvent(Scheduler context) throws Exception {
        context.assignTasksIfPossible();
        context.setState(new IdleState());
    }
}
