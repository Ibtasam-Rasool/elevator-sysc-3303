import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    Scheduler scheduler;
    TaskData sampleTask;
    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
        sampleTask = new TaskData("14:05:15", 2, "Up", 4);
    }

    @AfterEach
    void tearDown() {
        while (scheduler.doesHaveTask()){
            scheduler.acquireTask();
        }
    }

    @Test
    void acquireTask() {
        assertNull(scheduler.getTask());
        assertFalse(scheduler.doesHaveTask());

        scheduler.giveTask(sampleTask);
        assertTrue(scheduler.doesHaveTask());

        assertEquals(scheduler.acquireTask(), sampleTask);
        assertFalse(scheduler.doesHaveTask());
        tearDown();
    }

    @Test
    void giveTask() {
        assertNull(scheduler.getTask());
        assertFalse(scheduler.doesHaveTask());

        scheduler.giveTask(sampleTask);
        assertEquals(scheduler.getTask(), sampleTask);
        assertTrue(scheduler.doesHaveTask());
        tearDown();
    }

}