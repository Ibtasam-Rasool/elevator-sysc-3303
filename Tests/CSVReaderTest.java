import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        ArrayList<TaskData> taskList = CSVR.parser("src\\ElevatorCallSheetTESTFILE - Sheet1.csv");

        ArrayList<TaskData> correctTaskList = new ArrayList<>();
        correctTaskList.add(new TaskData("14:05:15", 2, "Up", 4));
        correctTaskList.add(new TaskData("15:06:05", 4, "Down", 1));
        correctTaskList.add(new TaskData("19:00:00", 1, "Up", 2));
        correctTaskList.add(new TaskData("22:25:55", 3, "Down", 2));

        for (int i = 0; i < correctTaskList.toArray().length; i++){
            Assert.assertEquals(correctTaskList.get(i).getTimeString(),taskList.get(i).getTimeString());
            Assert.assertEquals(correctTaskList.get(i).getInitialFloor(),taskList.get(i).getInitialFloor());
            Assert.assertEquals(correctTaskList.get(i).getButton(),taskList.get(i).getButton());
            Assert.assertEquals(correctTaskList.get(i).getDestinationFloor(),taskList.get(i).getDestinationFloor());

        }
    }
}