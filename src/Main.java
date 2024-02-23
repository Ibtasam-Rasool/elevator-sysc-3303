import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CSVReader CSVR = new CSVReader();
        ArrayList<TaskData> taskList = CSVR.parser("src\\ElevatorCallSheet - Sheet1.csv");
        Scheduler scheduler = new Scheduler();
        Floor floor = new Floor(scheduler, taskList);
        Elevator elevator1 = new Elevator(scheduler, 1, 5);
        //Elevator elevator2 = new Elevator(scheduler, 2, 5);
        //Elevator elevator3 = new Elevator(scheduler, 3, 5);

        scheduler.setFloor(floor);


        Thread t0 = new Thread(scheduler);
        Thread t1 = new Thread(floor);
        Thread t2 = new Thread(elevator1);
        //Thread t3 = new Thread(elevator2);
        //Thread t4 = new Thread(elevator3);


        t0.start();
        t1.start();
        t2.start();
        //t3.start();
        //t4.start();

    }
}