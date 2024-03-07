package com.yourname.elevator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Floor implements Runnable{

    private DatagramSocket socket;
    private Random random = new Random();
    private List<TaskData> taskDataList;
    private final int schedulerPort = 4445;
    private final InetAddress schedulerAddress = InetAddress.getByName("127.0.0.1");

    private byte[] buf = new byte[256];
    private boolean connected;

    /**
     * initializes the floor class
     *
     * @param taskDataList lists of tasks
     */
    public Floor(ArrayList taskDataList) throws Exception {
        this.taskDataList = taskDataList;
        this.socket = new DatagramSocket(6665);
        this.connected = false;
    }

    /**
     * Runs this operation.
     * @author Quinton Tracey
     */
    @Override
    public void run() {
        while(!connected){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Waiting to connect to scheduler...");
        }

        while(true){
            if(!taskDataList.isEmpty()){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String messageToSend = taskDataList.removeFirst().toDataString();
                byte[] buf = messageToSend.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, schedulerAddress, schedulerPort);

                try {
                    System.out.println("Sending: " + messageToSend);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                try {
                    //wait for new user inputted task
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * this function ensures that the scheduler is running before attempting to send packets of taskdata
     *
     * @throws Exception, cannot send to socket
     */
    public void connectToScheduler() throws Exception {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println(received + " to scheduler");
        this.connected = true;

    }

    /**
     * prints out message from scheduler
     * @param message message received
     * @author Ibtasam Rasool
     */
    public void messageChannel(String message) {
        System.out.println("THE FLOOR HAS RECEIVED THE MESSAGE: " + message);
    }

    /**
     * Return currently scheduled tasks
     *
     * @return taskDataList - list of tasts scheduled
     * @author: Daniel Godfrey
     */
    public List<TaskData> getTaskDataList() { return taskDataList; }

    public void addtask(TaskData newTask){
        taskDataList.add(newTask);
        System.out.println("new task was added");
    }
}
