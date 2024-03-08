package com.yourname.elevator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler implements Runnable {
    private DatagramSocket socket;
    private SchedulerState state;
    private List<TaskData> tasks;
    private final Map<Integer, ElevatorStatus> availableElevators = new HashMap<>();
    private byte[] buf = new byte[256];
    private final int floorPort = 6665;

    public Scheduler() throws Exception {
        this.state = new SchedulerIdleState();
        this.socket = new DatagramSocket(4445);
        this.tasks = new ArrayList<TaskData>();

        byte[] buf = "Connected".getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), floorPort);
        socket.send(packet);
        System.out.println("Connected To Floor");
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
                state.handleEvent(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void waitForEvents() throws Exception {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println("Scheduler received: " + received);

        if (received.contains("available")) {
            processElevatorAvailability(received, packet);
        }
        if (received.contains("Data")) {
            addTask(received);
        }
    }

    private void processElevatorAvailability(String received, DatagramPacket packet) {
        String[] parts = received.split(" ");
        int elevatorId = Integer.parseInt(parts[1]);
        int elevatorFloor = Integer.parseInt(parts[5]);
        availableElevators.put(elevatorId, new ElevatorStatus(elevatorId, packet.getAddress(), packet.getPort(), elevatorFloor));
    }

    /** adds a new task to the tasks arraylist
     *
     * @param received, the new task to be added to the tasks arraylist
     */
    private void addTask(String received) {
        String[] taskString = received.split(",");

        int time = Integer.parseInt(taskString[1].trim());
        int initialFloor = Integer.parseInt(taskString[2].trim());
        String button = taskString[3].trim();
        int destinationFloor = Integer.parseInt(taskString[4].trim());

        TaskData newTask = new TaskData(time,initialFloor,button,destinationFloor);
        tasks.add(newTask);
    }

    private void sendTask(String task, InetAddress address, int port) throws Exception {
        byte[] buf = task.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

    protected void assignTasksIfPossible() {
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

    public void setState(SchedulerState schedulingState) {
        state = schedulingState;
    }
    public List<TaskData> getTasks(){
        return tasks;
    }
    public Map<Integer, ElevatorStatus> getAvailableElevators(){
        return availableElevators;
    }
}