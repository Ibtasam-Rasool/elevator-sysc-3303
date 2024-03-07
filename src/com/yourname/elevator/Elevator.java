package com.yourname.elevator;

import com.yourname.elevator.states.ElevatorIdle;
import com.yourname.elevator.states.ElevatorStates;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Elevator implements Runnable{
    enum State {
        IDLE,
        MOVING_TO_INITIAL,
        LOADING_PASSENGERS,
        MOVING_TO_DESTINATION,
        UNLOADING_PASSENGERS
    }

    private ElevatorStates state;
    private final int id;
    private int currentFloor;
    private DatagramSocket socket;
    private InetAddress schedulerAddress;
    private int schedulerPort = 4445;
    private byte[] buf = new byte[256];
    private int initialFloor;
    private int destinationFloor;
    private String button;

    public Elevator(int id, int port) throws Exception {
        this.id = id;
        this.currentFloor = 1;
        this.state = new ElevatorIdle();
        this.socket = new DatagramSocket(port);
        this.schedulerAddress = InetAddress.getByName("127.0.0.1");
    }

    private void sendUpdate(String message) throws Exception {
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, schedulerAddress, schedulerPort);
        socket.send(packet);
    }

    private void notifyAvailability() throws Exception {
        sendUpdate("Elevator " + id + " available at floor " + currentFloor);
    }

    @Override
    public void run() {
        try {
            notifyAvailability(); // send inital msg to scheduler
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String taskDataStr = new String(packet.getData(), 0, packet.getLength()).trim();
                parseTaskData(taskDataStr);
                System.out.println("Elevator " + id + " received task: " + taskDataStr);

                //displayState();

                //JUST FOR TESTING STATES SCHEDULER WILL DO ALL OF THIS
                moveElevator();
                displayState();
                openDoors();
                displayState();
                moveElevator();
                displayState();
                openDoors();
                displayState();


                notifyAvailability();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseTaskData(String taskDataStr) {
        taskDataStr = taskDataStr.replace("TaskData{", "").replace("}", "").replace("'", "");
        String[] dataParts = taskDataStr.split(", ");
        for (String part : dataParts) {
            String[] keyValue = part.split("=");
            switch (keyValue[0]) {
                case "floor":
                    initialFloor = Integer.parseInt(keyValue[1]);
                    break;
                case "destinationFloor":
                    destinationFloor = Integer.parseInt(keyValue[1]);
                    break;
                case "button":
                    button = keyValue[1];
                    break;
            }
        }
    }

    /**
     * Represents event telling elevator to close doors
     */
    public void closeDoors() {
        state.closeDoors(this);
    }

    /**
     * Represents event telling elevator to open doors
     */
    public void openDoors() {
        state.openDoors(this);
    }

    /**
     * Represents event elevator button pressed
     */
    public void buttonPressed() {

    }

    /**
     * Displays current elevator state
     */
    public void displayState() {
       state.displayState(this);
    }

    /**
     * Event where elevator told to move to floor
     */
    public void moveElevator() {
        state.moveElevator(this);
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getInitialFloor() {
        return initialFloor;
    }

    public void setState(ElevatorStates state) {
        this.state = state;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }


}
