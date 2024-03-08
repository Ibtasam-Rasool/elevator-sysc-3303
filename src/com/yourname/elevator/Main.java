package com.yourname.elevator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            Scheduler scheduler = new Scheduler();
            Thread schedulerThread = new Thread(scheduler);
            schedulerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
