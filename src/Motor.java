public class Motor implements Runnable {
    private volatile boolean running;
    private int floorDiff;

    /**
     * constructor for motor
     * @author Saad Sheikh
     */
    public Motor() {
        running = false;
    }

    /**
     * function to simulate motor running between floors
     * @param floorDiff number of floors between source and destination
     * @author Saad Sheikh
     */
    public void moveToFloor(int floorDiff) {
        if (!running) {
            this.floorDiff = floorDiff;
            running = true;
            new Thread(this).start();
        } else {
            System.out.println("Motor is already running");
        }
    }

    /**
     * run function for motor thread
     * @author Saad Sheikh
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < floorDiff; i++) {
                if (!running) {
                    break;
                }
                // change the time from 1 second to something else later
                Thread.sleep(1000); // a counter for time motor will be running for (aka travel time between each floor)
            }
        } catch (InterruptedException e) {
            stopMotor();
        }
    }

    /**
     * function to stop motor running
     * @author Saad Sheikh
     */
    public void stopMotor() {
        running = false;
        System.out.println("Motor stopped.");
    }
}
