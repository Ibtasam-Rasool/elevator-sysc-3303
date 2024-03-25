package com.yourname.elevator;

import com.yourname.elevator.states.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Elevator implements Runnable{

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
    private int floorButton;

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
                String message = new String(packet.getData(), 0, packet.getLength()).trim();

                System.out.println("Elevator " + id + " received message: " + message);

                if(message.startsWith("TaskData")){
                    parseTaskData(message);
                }
                else if(message.startsWith("ChangeState")){
                    handleAction(message);
                }

                //displayState();



                notifyAvailability();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyScheduler(String message) throws Exception {
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, schedulerAddress, schedulerPort);
        socket.send(packet);
        System.out.println("Elevator " + id + " sent update to Scheduler: " + message);
    }

    public void notifyLoading() throws Exception {
        notifyScheduler("Loading " + id);
    }

    public void notifyUnloading() throws Exception {
        notifyScheduler("Unloading " + id);
    }

    public void readyToCloseDoors() throws Exception {
        notifyScheduler("ReadyToCloseDoors " + id);
    }

    private void handleAction(String message) throws Exception {
        String[] parts = message.split(" ");
        String newState = parts[1];

        switch (newState) {
            case "OPEN_DOOR":
                openDoors();
                break;
            case "CLOSE_DOOR":
                closeDoors();
                break;
            case "LOADING":
                notifyLoading();
                setState(new ElevatorLoadingPassengers());
                break;
            case "MOVING":
                moveElevator();
                break;
            case "UNLOADING":
                notifyUnloading();
                setState(new ElevatorUnloadingPassengers());
                break;
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
    public void buttonPressed(int floor) throws Exception {
        floorButton = floor;
        System.out.println("Internal Elevator Floor Command: Elevator " + id + " will move to " + floor);
        sendUpdate("ElevatorFloorButton " + id + " " +floorButton);
        state.buttonPressed(this);
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

    public int getElevatorId() {
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
