package com.felipearruda.synchronizingthreads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Operações atomicas, garantem a integridade dos dados e wvita condições de corrida entre as threads.
 */
public class Atomic {

    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        Synchronized1.MyRunnable myRunnable = new Synchronized1.MyRunnable();

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
    }

    public static class MyRunnable implements Runnable {

        @Override
        public synchronized void run() {
            System.out.println(Thread.currentThread().getName() + ": " + i.incrementAndGet());
        }
    }
}
