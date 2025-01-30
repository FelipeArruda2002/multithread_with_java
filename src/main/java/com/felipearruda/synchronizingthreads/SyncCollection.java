package com.felipearruda.synchronizingthreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyncCollection {

    static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        list = Collections.synchronizedList(list);
        MyRunnable myRunnable = new MyRunnable();

        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);
        Thread t3 = new Thread(myRunnable);
        Thread t4 = new Thread(myRunnable);
        Thread t5 = new Thread(myRunnable);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        Thread.sleep(500);
        System.out.println(list);
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            list.add("Added to the list");
            System.out.println(Thread.currentThread().getName() + "-> Added to the list");
        }
    }
}
