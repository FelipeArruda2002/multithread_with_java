package com.felipearruda.synchronizingthreads;

public class Synchronized2 {

    static int i = 0;

    public static void main(String[] args) {
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
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            int j;
            synchronized (Synchronized2.class) {
                i++;
                j = i * 2;
            }

            System.out.println(Thread.currentThread().getName() + ": " + j);
        }
    }

}
