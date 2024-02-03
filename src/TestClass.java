import org.junit.Assert;

import java.util.ArrayList;

class TestClass {

    @org.junit.jupiter.api.Test
    void CSVReaderTest() {
        CSVReader CSVR = new CSVReader();
        ArrayList<TaskData> taskList = CSVR.parser("src\\ElevatorCallSheetTESTFILE - Sheet1.csv");

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