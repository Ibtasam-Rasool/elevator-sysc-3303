package com.yourname.elevator;

public interface SchedulerState{
    void handleEvent(Scheduler context) throws Exception;
}
