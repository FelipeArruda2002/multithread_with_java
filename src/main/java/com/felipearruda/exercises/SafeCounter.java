package com.felipearruda.exercises;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounter {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable myRunnable = () -> {
           counter.addAndGet(1000);
        };

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

        /**
         * A chamada a join() bloqueia a thread principal (a do main)
         * até que cada uma das threads (t1, t2, t3, t4 e t5) tenha completado sua execução.
         */
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        System.out.println("Counter = " + counter.get());
    }

}
