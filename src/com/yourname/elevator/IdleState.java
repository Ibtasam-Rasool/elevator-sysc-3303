package com.yourname.elevator;

public class IdleState implements SchedulerState{

    @Override
    public void handleEvent(Scheduler context) throws Exception {
        context.waitForEvents();
        if (!context.getTasks().isEmpty() && !context.getAvailableElevators().isEmpty()) {
            context.setState(new SchedulingState());
        }
    }
}
