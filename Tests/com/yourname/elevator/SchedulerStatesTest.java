package com.yourname.elevator;

import com.yourname.elevator.states.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchedulerStatesTest {

    private Scheduler scheduler;

    @BeforeEach
    void setUp() throws Exception {
        scheduler = new Scheduler();
    }

    @AfterEach
    void tearDown() {
        scheduler.close();
    }

    @Test
    void testSetIdleState() {
        scheduler.setState(new SchedulerIdleState());
        assertTrue(scheduler.getState() instanceof SchedulerIdleState);
    }

    @Test
    void testSetSchedulingState() {
        scheduler.setState(new SchedulerSchedulingState());
        assertTrue(scheduler.getState() instanceof SchedulerSchedulingState);
    }
}
