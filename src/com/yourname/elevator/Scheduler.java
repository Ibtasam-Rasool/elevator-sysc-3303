package com.yourname.elevator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler implements Runnable {
    enum State {
        IDLE,
        SCHEDULING
    }
    private DatagramSocket socket;
    private State state;
    private List<TaskData> tasks;
    private final Map<Integer, ElevatorStatus> availableElevators = new HashMap<>();
    private byte[] buf = new byte[256];

    public Scheduler(List<TaskData> tasks) throws Exception {
        this.state = State.IDLE;
        this.socket = new DatagramSocket(4445);
        this.tasks = tasks;
    }

    @Override
    public void run() {
        System.out.println("Scheduler started.");
        /*
        for (TaskData task : tasks) {
            System.out.println(task);
        }
        /*
         */
        while (true) {
            try {
                switch (state){
                    case IDLE:
                        waitForEvents();
                        break;
                    case SCHEDULING:
                        assignTasksIfPossible();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForEvents() throws Exception {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println("Scheduler received: " + received);

        if (received.contains("available")) {
            processElevatorAvailability(received, packet);
        }

        if (!tasks.isEmpty() && !availableElevators.isEmpty()) {
            state = State.SCHEDULING;
        }
    }

    private void processElevatorAvailability(String received, DatagramPacket packet) {
        String[] parts = received.split(" ");
        int elevatorId = Integer.parseInt(parts[1]);
        int elevatorFloor = Integer.parseInt(parts[5]);
        availableElevators.put(elevatorId, new ElevatorStatus(elevatorId, packet.getAddress(), packet.getPort(), elevatorFloor));
    }

    private void sendTask(String task, InetAddress address, int port) throws Exception {
        byte[] buf = task.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

    private void assignTasksIfPossible() {
        while (!tasks.isEmpty() && !availableElevators.isEmpty()) {
            TaskData task = tasks.remove(0);
            ElevatorStatus nearestElevator = findNearestAvailableElevator(task.getInitialFloor());
            if (nearestElevator != null) {
                availableElevators.remove(nearestElevator.getId());
                try {
                    sendTask(task.toString(), nearestElevator.getAddress(), nearestElevator.getPort());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.state = State.IDLE;
    }

    private ElevatorStatus findNearestAvailableElevator(int floor) {
        ElevatorStatus nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (ElevatorStatus elevator : availableElevators.values()) {
            int distance = Math.abs(elevator.getFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearestElevator = elevator;
            }
        }
        return nearestElevator;
    }

    private static class ElevatorStatus {
        private final int id;
        private final InetAddress address;
        private final int port;
        private final int floor;

        public ElevatorStatus(int id, InetAddress address, int port, int floor) {
            this.id = id;
            this.address = address;
            this.port = port;
            this.floor = floor;
        }

        public int getId() { return id; }
        public InetAddress getAddress() { return address; }
        public int getPort() { return port; }
        public int getFloor() { return floor; }
    }
}