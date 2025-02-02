package com.felipearruda.exercises;

public class ControlledThreadMain {

    static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            stopThread();
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        Thread t4 = new Thread(runnable);
        Thread t5 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        while (running) {
            System.out.println("Run......");
            Thread.sleep(100);
        }
    }

    public static void stopThread() {
        running = false;
    }

}