import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {
    CSVReader CSVR;
    @BeforeEach
    void setUp() {
        CSVR = new CSVReader();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parser() {
        ArrayList<TaskData> taskList = CSVR.parser("src\\ElevatorCallSheet - Sheet1.csv");

        ArrayList<TaskData> correctTaskList = new ArrayList<>();
        correctTaskList.add(new TaskData("14:05:15", 2, "Up", 4));
        correctTaskList.add(new TaskData("15:06:05", 4, "Down", 1));
        correctTaskList.add(new TaskData("19:00:00", 1, "Up", 2));
        correctTaskList.add(new TaskData("22:25:55", 3, "Down", 2));

        for (int i = 0; i < correctTaskList.toArray().length; i++){
            Assert.assertEquals(correctTaskList.get(i).getTime(),taskList.get(i).getTime());
            Assert.assertEquals(correctTaskList.get(i).getFloor(),taskList.get(i).getFloor());
            Assert.assertEquals(correctTaskList.get(i).getButton(),taskList.get(i).getButton());
            Assert.assertEquals(correctTaskList.get(i).getElevatorNumber(),taskList.get(i).getElevatorNumber());

        }
    }
}