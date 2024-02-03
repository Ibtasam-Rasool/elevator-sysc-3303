import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        TaskData task1 = new TaskData(2, new Date(), 3, "UP");
        TaskData task2 = new TaskData(3, new Date(), 4, "DOWN");
        TaskData task3 = new TaskData(4, new Date(), 1, "UP");
        TaskData task4 = new TaskData(5, new Date(), 5, "DOWN");

        ArrayList<TaskData> taskList = new ArrayList();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);

        Scheduler scheduler = new Scheduler();
        Floor floor = new Floor(scheduler, taskList);
        Elevator elevator = new Elevator(scheduler, 1, 5);
        Elevator elevator2 = new Elevator(scheduler, 2, 5);
        Elevator elevator3 = new Elevator(scheduler, 3, 5);

        scheduler.setFloor(floor);


        Thread t0 = new Thread(scheduler);
        Thread t1 = new Thread(floor);
        Thread t2 = new Thread(elevator);
        Thread t3 = new Thread(elevator2);
        Thread t4 = new Thread(elevator3);


        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}