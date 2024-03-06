package com.yourname.elevator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Elevator implements Runnable {
    enum State {
        IDLE,
        MOVING_TO_INITIAL,
        LOADING_PASSENGERS,
        MOVING_TO_DESTINATION,
        UNLOADING_PASSENGERS
    }

    private final int id;
    private int currentFloor;
    private State state;
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
        this.state = State.IDLE;
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

                moveToInitialFloor();
                loadPassengers();
                moveToDestinationFloor();
                unloadPassengers();

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

    private void moveToInitialFloor() {
        if (currentFloor != initialFloor) {
            System.out.println("Elevator " + id + " is moving to the initial floor " + initialFloor);
            //1 second per floor difference
            int floorDifference = Math.abs(initialFloor - currentFloor);
            try {
                Thread.sleep(1000 * floorDifference);  // Simulate moving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentFloor = initialFloor; // Update current floor
        }
        this.state = State.LOADING_PASSENGERS;
    }

    private void loadPassengers() {
        System.out.println("Elevator " + id + " is loading passengers at floor " + currentFloor);
        // 3 second load time
        try {
            Thread.sleep(3000);  //
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.state = State.MOVING_TO_DESTINATION;
    }

    private void moveToDestinationFloor() {
        if (currentFloor != destinationFloor) {
            System.out.println("Elevator " + id + " is moving to the destination floor " + destinationFloor);
            //1 second per floor diff
            int floorDifference = Math.abs(destinationFloor - currentFloor);
            try {
                Thread.sleep(1000 * floorDifference);  // Simulate moving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentFloor = destinationFloor; // Update current floor
        }
        this.state = State.UNLOADING_PASSENGERS;
    }

    private void unloadPassengers() {
        System.out.println("Elevator " + id + " is unloading passengers at floor " + currentFloor);
        //3 second load time
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.state = State.IDLE;
    }
}
